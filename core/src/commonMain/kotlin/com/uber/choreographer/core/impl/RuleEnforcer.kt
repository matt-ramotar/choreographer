package com.uber.choreographer.core.impl

import com.uber.choreographer.core.api.AppState
import com.uber.choreographer.core.api.Bookkeeping
import com.uber.choreographer.core.api.VisibilityRequest


fun interface RuleEnforcer<S : AppState> {
    suspend fun isCompliant(request: VisibilityRequest, appState: S, bookkeeping: Bookkeeping): RuleEnforcerResponse
}