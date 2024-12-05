package com.uber.choreographer.pokedex.lib.choreographer

import com.uber.choreographer.dsl.Action
import com.uber.choreographer.dsl.ComponentSet
import com.uber.choreographer.dsl.ConditionsEvaluationResult
import com.uber.choreographer.dsl.rule
import com.uber.choreographer.pokedex.lib.choreographer.PokedexDependencies.bottomBannerDependencies
import com.uber.choreographer.pokedex.lib.choreographer.PokedexExtensions.getCurrentPage
import com.uber.choreographer.pokedex.lib.choreographer.PokedexExtensions.sessionImpressionCount

object PokedexRules {
    val bottomBannerVisibilityRule = rule {
        name("Bottom Banner Visibility Rule")
        appliesTo(ComponentSet.Only(setOf(PokedexComponentType.Banner.type)))
        dependsOn(bottomBannerDependencies)
        whenConditions { request, appState, bookkeeping ->
            // Is request for bottom banner
            val isBottomBanner =
                request.componentType == PokedexComponentType.Banner.type && request.slotType == PokedexSlotType.BottomNav.type

            // Has not shown this session
            val sessionImpressionCount = bookkeeping.sessionImpressionCount(PokedexComponentType.Banner.type)
            val hasNotShownThisSession = sessionImpressionCount == 0

            // Is home tab
            val currentPage = appState.getCurrentPage()
            val requestedPage = request.pageType
            val isHomePage = currentPage == PokedexPageType.HomeTab && requestedPage == PokedexPageType.HomeTab.type

            when {
                isBottomBanner && hasNotShownThisSession && isHomePage -> ConditionsEvaluationResult.AllSatisfied
                else -> ConditionsEvaluationResult.NoneSatisfied
            }
        }
        actions { dependenciesSatisfied, conditionsEvaluationResult ->
            when {
                dependenciesSatisfied && conditionsEvaluationResult == ConditionsEvaluationResult.AllSatisfied -> Action.Grant
                dependenciesSatisfied && conditionsEvaluationResult == ConditionsEvaluationResult.SomeSatisfied -> Action.Defer
                else -> Action.Deny
            }
        }
    }
}

