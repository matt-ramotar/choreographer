package com.uber.choreographer.dsl

import com.uber.choreographer.core.api.AppState

fun interface ConditionsEvaluator<S : AppState> {
    operator fun invoke(
        conditions: Conditions<S>
    ): EvaluationResult
}