package com.uber.choreographer.pokedex.lib.choreographer

import com.uber.choreographer.dsl.EvaluationResult
import com.uber.choreographer.dsl.RuleSet
import com.uber.choreographer.dsl.RuleSetBuilder
import com.uber.choreographer.dsl.rules

object PokedexRules {
    fun ruleSet(): RuleSet<PokedexAppState> = rules {
        bottomBannerCanShowOncePerSessionIfNotOverlapping()
    }


    private fun RuleSetBuilder<PokedexAppState>.bottomBannerCanShowOncePerSessionIfNotOverlapping() = rule {
        named("bottomBannerCanShowOncePerSessionIfNotOverlapping")

        forOnly(PokedexComponentType.Banner.type)

        whenever { conditions ->
            // Need to check that bottom nav is open
            val visibleComponentsInSlot = conditions.appState.getVisibleComponentsInSlot(PokedexSlotType.BottomNav.type, null)

            // Need to check that this component type hasn't already shown this session
            val equivalentRequestsSameSession = conditions.bookkeeping.visibilityDecisions.filter { visibilityDecision ->
                (visibilityDecision.component == conditions.request.componentType && (conditions.request.slotType == null || (visibilityDecision.slot == conditions.request.slotType)) && (conditions.request.pageType == null || (visibilityDecision.page == conditions.request.pageType)))
            }

            when {
                visibleComponentsInSlot.isNotEmpty() && equivalentRequestsSameSession.isEmpty() -> EvaluationResult.SatisfiedSomeConditions
                visibleComponentsInSlot.isEmpty() && equivalentRequestsSameSession.isEmpty() -> EvaluationResult.SatisfiedAllConditions
                else -> EvaluationResult.DidNotSatisfyAnyConditions
            }
        }

        then { result ->
            when (result) {
                EvaluationResult.SatisfiedSomeConditions -> {
                    // Do something
                    com.uber.choreographer.dsl.Action.Defer
                }

                EvaluationResult.DidNotSatisfyAnyConditions -> {
                    // Do something
                    com.uber.choreographer.dsl.Action.Deny
                }

                EvaluationResult.SatisfiedAllConditions -> {
                    // Do something
                    com.uber.choreographer.dsl.Action.Grant
                }
            }
        }

        priority(10)
    }
}