package com.uber.choreographer.pokedex.lib.choreographer

import com.uber.choreographer.core.api.Choreographer
import com.uber.choreographer.core.api.ChoreographerBuilder
import com.uber.choreographer.dsl.api.Action
import com.uber.choreographer.dsl.api.RuleSet
import com.uber.choreographer.dsl.api.rules

class PokedexChoreographerFactory {
    fun create(): Choreographer {
        val builder = ChoreographerBuilder.invoke<PokedexAppState>()

        val ruleSet = ruleSet()

        builder.ruleSet(ruleSet)

        return builder.build()
    }

    private fun ruleSet(): RuleSet<PokedexAppState> = rules {
        rule {
            named("ForOnlyBanner_WheneverNoOverlappingComponentsAndNotAlreadyShownThisSession_ThenGrant")

            forOnly(PokedexComponentType.Banner.type)

            whenever { conditions ->
                // Need to check that bottom nav is open
                val visibleComponentsInSlot = conditions.appState.getVisibleComponentsInSlot(PokedexSlotType.BottomNav.type, null)

                // Need to check that this component type hasn't already shown this session
                val equivalentRequestsSameSession = conditions.bookkeeping.visibilityDecisions.filter { visibilityDecision ->
                    (visibilityDecision.component == conditions.request.componentType && (conditions.request.slotType == null || (visibilityDecision.slot == conditions.request.slotType)) && (conditions.request.pageType == null || (visibilityDecision.page == conditions.request.pageType)))
                }

                visibleComponentsInSlot.isEmpty() && equivalentRequestsSameSession.isEmpty()
            }

            then { satisfiedConditions ->
                if (satisfiedConditions) {
                    Action.Grant
                } else {
                    Action.Deny
                }
            }

            priority(10)
        }
    }
}
