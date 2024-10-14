package com.uber.choreographer.core.impl

import com.uber.choreographer.core.api.Bookkeeper
import com.uber.choreographer.core.api.Choreographer
import com.uber.choreographer.core.api.ComponentInstanceId
import com.uber.choreographer.core.api.ComponentVisibility
import com.uber.choreographer.core.api.DeniedRequestReason
import com.uber.choreographer.core.api.GrantedRequestReason
import com.uber.choreographer.core.api.MutableAppState
import com.uber.choreographer.core.api.SlotType
import com.uber.choreographer.core.api.VisibilityDecision
import com.uber.choreographer.core.api.VisibilityRequest
import com.uber.choreographer.core.api.VisibilityRequestStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class RealChoreographer<S : MutableAppState>(
    private val mutableAppState: S,
    private val ruleEnforcer: RuleEnforcer<S>,
    private val bookkeeper: Bookkeeper,
    coroutineDispatcher: CoroutineDispatcher
) : Choreographer {

    // TODO: This needs to be thread-safe
    private val responseStreams = mutableMapOf<VisibilityRequest, MutableSharedFlow<VisibilityRequestStatus>>()

    private val componentInstanceIds = mutableMapOf<VisibilityRequest, ComponentInstanceId>()

    // TODO: This needs to be thread-safe
    // Sharding waitlist queues by slot to prevent blocking UI slots when non-competing UI slots have waitlist
    // TODO: The queues need to be ordered
    private val prioritizedWaitlists = linkedMapOf<SlotType?, ArrayDeque<VisibilityRequest>>()

    // TODO: This needs to be thread-safe
    private val requestQueue = ArrayDeque<VisibilityRequest>()

    private val coroutineScope = CoroutineScope(coroutineDispatcher)


    override fun requestVisibility(request: VisibilityRequest): Flow<VisibilityRequestStatus> {
        val existingResponseStream = responseStreams[request]

        if (existingResponseStream != null) {
            return existingResponseStream
        }

        val componentInstanceId = Uuid.random().toHexString()

        val responseStream = MutableSharedFlow<VisibilityRequestStatus>()

        requestQueue.addLast(request)
        responseStreams[request] = responseStream
        componentInstanceIds[request] = componentInstanceId

        return responseStream.asSharedFlow().also {
            coroutineScope.launch {
                responseStream.emit(VisibilityRequestStatus.PendingReview(componentInstanceId))
                processQueue()
            }
        }
    }

    private suspend fun processQueue() {
        while (requestQueue.isNotEmpty()) {
            processFirstEligibleRequest()
        }
    }

    private suspend fun processFirstEligibleRequest() {

        // TODO: Need to lock the request queue before starting this search
        // Need to skip requests blocked by another request for the same slot that has already been waitlisted
        var index = 0
        var canProcess = false
        while (!canProcess && index <= requestQueue.lastIndex) {
            val request = requestQueue[index]
            if (prioritizedWaitlists[request.slotType]?.contains(request) == false) {
                canProcess = true
            } else {
                index++
            }
        }

        if (!canProcess) {
            return
        }

        val request = requestQueue.removeAt(index)
        processRequest(request)
    }

    private suspend fun processRequest(request: VisibilityRequest) {
        val componentInstanceId = componentInstanceIds[request] ?: return
        val responseStream = responseStreams[request] ?: return
        responseStream.emit(VisibilityRequestStatus.UnderReview(componentInstanceId))
        val ruleEnforcerResponse = ruleEnforcer.isCompliant(request, mutableAppState, bookkeeper.bookkeeping())

        if (request.deferrable) {
            when (ruleEnforcerResponse) {
                RuleEnforcerResponse.YES -> grantRequest(
                    componentInstanceId,
                    request,
                    GrantedRequestReason.RuleSatisfied
                ) // TODO: Add reasonable GrantedRequestReasons
                RuleEnforcerResponse.NO -> denyRequest(
                    componentInstanceId,
                    request,
                    DeniedRequestReason.RuleNotSatisfied
                ) // TODO: Add reasonable DeniedRequestReasons
                RuleEnforcerResponse.PENDING -> {
                    // Deferrable, so can add to waitlist

                    val waitlist = prioritizedWaitlists[request.slotType]
                    if (waitlist == null) {
                        prioritizedWaitlists[request.slotType] = ArrayDeque()
                    }
                    // TODO: Need to sort the requests efficiently
                    val prioritizedWaitlist = prioritizedWaitlists[request.slotType]!!
                    prioritizedWaitlist.addLast(request)
                    val reprioritizedWaitlist = prioritizedWaitlist.sortedBy { it.priority }
                    prioritizedWaitlists[request.slotType] = ArrayDeque(reprioritizedWaitlist)

                    responseStream.emit(VisibilityRequestStatus.Deferred(componentInstanceId) {
                        // TODO: Support canceling a waitlisted request
                    })
                }
            }

        } else {
            when (ruleEnforcerResponse) {
                RuleEnforcerResponse.YES -> grantRequest(
                    componentInstanceId,
                    request,
                    GrantedRequestReason.RuleSatisfied
                ) // TODO: Add reasonable GrantedRequestReasons

                // Not deferrable, cannot add to waitlist
                RuleEnforcerResponse.PENDING,
                RuleEnforcerResponse.NO -> denyRequest(
                    componentInstanceId,
                    request,
                    DeniedRequestReason.RuleNotSatisfied
                ) // TODO: Add reasonable DeniedRequestReasons
            }
        }
    }

    private suspend fun grantRequest(componentInstanceId: ComponentInstanceId, request: VisibilityRequest, reason: GrantedRequestReason) {

        // Let the consumer know it is granted
        responseStreams[request]!!.emit(
            VisibilityRequestStatus.Granted(
                componentInstanceId, reason
            ) { componentVisibility ->
                // This is to enable animation sequencing

                val prevComponentState = mutableAppState.getComponentState(componentInstanceId)

                if (prevComponentState != null) {

                    mutableAppState.updateComponentState(prevComponentState.copy(visibility = componentVisibility))

                    when (componentVisibility) {
                        ComponentVisibility.NotVisible -> {
                            // This is where we move requests off the waitlist
                            val firstOnWaitlist = prioritizedWaitlists[request.slotType]?.removeFirstOrNull()

                            if (firstOnWaitlist != null) {
                                coroutineScope.launch {
                                    processRequest(firstOnWaitlist)
                                }
                            }
                        }

                        ComponentVisibility.Visible.AnimatingIn -> {
                            // TODO: Check dependencies
                        }

                        ComponentVisibility.Visible.AnimatingOut -> {
                            // TODO: Check dependencies
                        }

                        ComponentVisibility.Visible.Idle -> {
                            // TODO: Check dependencies
                        }
                    }
                }
            }
        )

        // Handle bookkeeping
        bookkeeper.decision(
            VisibilityDecision(
                componentInstanceId,
                request.componentType,
                request.slotType,
                request.pageType,
                granted = true,
                timestamp = now()
            )
        )

        // Clean up
        responseStreams.remove(request)
        prioritizedWaitlists[request.slotType]?.remove(request)
    }

    private suspend fun denyRequest(componentInstanceId: ComponentInstanceId, request: VisibilityRequest, reason: DeniedRequestReason) {
        // Let the consumer know it is denied
        responseStreams[request]!!.emit(
            VisibilityRequestStatus.Denied(
                componentInstanceId,
                reason
            )
        )

        // Handle bookkeeping
        bookkeeper.decision(
            VisibilityDecision(
                componentInstanceId,
                request.componentType,
                request.slotType,
                request.pageType,
                granted = false,
                timestamp = now()
            )
        )

        // Clean up
        responseStreams.remove(request)
        prioritizedWaitlists[request.slotType]?.remove(request)
    }

    private fun now() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
}