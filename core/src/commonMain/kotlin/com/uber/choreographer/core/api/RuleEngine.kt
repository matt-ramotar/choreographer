package com.uber.choreographer.core.api

interface RuleEngine {
    suspend fun evaluate(
        request: VisibilityRequest,
        appState: AppState,
        bookkeeping: Bookkeeping
    ): RuleEngineResult
}

