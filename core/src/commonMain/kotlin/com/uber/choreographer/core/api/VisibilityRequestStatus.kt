package com.uber.choreographer.core.api

sealed interface VisibilityRequestStatus {

    val instanceId: ComponentInstanceId

    data class PendingReview(
        override val instanceId: ComponentInstanceId
    ) : VisibilityRequestStatus

    data class UnderReview(
        override val instanceId: ComponentInstanceId
    ) : VisibilityRequestStatus

    data class Granted(
        override val instanceId: ComponentInstanceId,
        val reason: GrantedRequestReason,
        val onVisibilityChange: suspend (ComponentVisibility) -> Unit // This is a callback so that components can only update visibility after being granted visibility
    ) : VisibilityRequestStatus

    data class Deferred(
        override val instanceId: ComponentInstanceId,
        val cancelRequest: () -> Unit
    ) : VisibilityRequestStatus

    data class Denied(
        override val instanceId: ComponentInstanceId,
        val reason: DeniedRequestReason
    ) : VisibilityRequestStatus
}