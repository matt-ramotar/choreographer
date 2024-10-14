package com.uber.choreographer.dsl.api

import com.uber.choreographer.core.api.AppState
import com.uber.choreographer.core.api.Bookkeeping
import com.uber.choreographer.core.api.VisibilityRequest

data class Conditions<S: AppState>(
    val request: VisibilityRequest,
    val appState: S,
    val bookkeeping: Bookkeeping
)
