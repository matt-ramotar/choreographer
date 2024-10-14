package com.uber.choreographer.dsl.api

import com.uber.choreographer.core.api.AppState

fun interface ConditionsEvaluator<S : AppState> {
    operator fun invoke(
        conditions: Conditions<S>
    ): Boolean
}