package com.uber.choreographer.dsl

import com.uber.choreographer.core.api.AppState
import com.uber.choreographer.core.api.Bookkeeping
import com.uber.choreographer.core.api.VisibilityRequest

fun interface Conditions {
    fun evaluate(request: VisibilityRequest, appState: AppState, bookkeeping: Bookkeeping): ConditionsEvaluationResult
}