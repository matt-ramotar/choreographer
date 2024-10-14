package com.uber.choreographer.core.impl

import com.uber.choreographer.core.api.AppState
import com.uber.choreographer.core.api.Bookkeeping
import com.uber.choreographer.core.api.ChoreographerConfig
import com.uber.choreographer.core.api.ComponentAllowList
import com.uber.choreographer.core.api.VisibilityRequest
import com.uber.choreographer.dsl.api.Action
import com.uber.choreographer.dsl.api.ComponentSet
import com.uber.choreographer.dsl.api.RuleSet

class RealRuleEnforcer<S : AppState>(
    private val ruleSet: RuleSet<S>,
    private val choreographerConfig: ChoreographerConfig
) : RuleEnforcer<S> {
    override suspend fun isCompliant(request: VisibilityRequest, appState: S, bookkeeping: Bookkeeping): RuleEnforcerResponse {


        // First, check the config to see whether this component type is allowed for this slot and page.

        var componentTypeIsAllowed = true

        when (val componentAllowList = choreographerConfig.getComponentAllowList(request.componentType)) {
            is ComponentAllowList.AllowAny -> {
                when {
                    componentAllowList.blockedPages.contains(request.pageType) -> {
                        componentTypeIsAllowed = false
                    }

                    componentAllowList.blockedSlots.contains(request.slotType) -> {
                        componentTypeIsAllowed = false
                    }

                }
            }

            is ComponentAllowList.AllowOnly -> {
                when {
                    !componentAllowList.allowedPages.contains(request.pageType) -> {
                        componentTypeIsAllowed = false
                    }

                    !componentAllowList.allowedSlots.contains(request.slotType) -> {
                        componentTypeIsAllowed = false
                    }
                }
            }
        }


        if (!componentTypeIsAllowed) {
            return RuleEnforcerResponse.NO
        }

        // Second, check whether the conditions in the applicable rules are satisfied.

        val applicableRules = ruleSet.filter { rule ->
            when (val componentSet = rule.componentSet) {
                is ComponentSet.Any -> {
                    !componentSet.excluded.contains(request.componentType)
                }

                is ComponentSet.Only -> {
                    componentSet.included.contains(request.componentType)
                }
            }
        }

        val actions = applicableRules.map { rule ->
            rule.actionCreator.invoke(rule.conditionsEvaluator.invoke(request, appState, bookkeeping))
        }

        return when {
            actions.any { it == Action.Deny } -> RuleEnforcerResponse.NO
            actions.any { it == Action.Defer } -> RuleEnforcerResponse.PENDING
            else -> RuleEnforcerResponse.YES
        }
    }
}