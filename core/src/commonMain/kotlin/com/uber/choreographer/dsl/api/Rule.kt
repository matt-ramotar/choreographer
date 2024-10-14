package com.uber.choreographer.dsl.api

import com.uber.choreographer.core.api.AppState

data class Rule<S : AppState>(
    val name: String,
    val componentSet: ComponentSet,
    val conditionsEvaluator: ConditionsEvaluator<S>,
    val actionCreator: (Boolean) -> Action,
    val priority: Int,
)

