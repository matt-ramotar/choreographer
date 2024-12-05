package com.uber.choreographer.core.impl

import com.uber.choreographer.core.api.AppState
import com.uber.choreographer.core.api.Bookkeeping
import com.uber.choreographer.core.api.RuleEngine
import com.uber.choreographer.core.api.RuleEngineResult
import com.uber.choreographer.core.api.VisibilityRequest
import com.uber.choreographer.core.impl.extensions.RuleExtensions.isApplicable
import com.uber.choreographer.dsl.Action
import com.uber.choreographer.dsl.RuleSet

class DefaultRuleEngine(
    private val ruleSet: RuleSet
) : RuleEngine {

    override suspend fun evaluate(
        request: VisibilityRequest,
        appState: AppState,
        bookkeeping: Bookkeeping
    ): RuleEngineResult {
        val applicableRules = ruleSet.rules.filter { it.isApplicable(request) }
        for (rule in applicableRules) {
            val dependenciesSatisfied = rule.dependencies.satisfied(appState)
            val conditionsResult = rule.conditions.evaluate(request, appState, bookkeeping)
            val action = rule.actions.handle(dependenciesSatisfied, conditionsResult)
            return when (action) {
                Action.Grant -> RuleEngineResult.Granted
                Action.Deny -> RuleEngineResult.Denied
                Action.Defer -> RuleEngineResult.Pending
            }
        }
        return RuleEngineResult.Pending
    }
}


