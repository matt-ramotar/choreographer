package com.uber.choreographer.core.api

import kotlinx.coroutines.flow.Flow


interface Choreographer {
    fun requestVisibility(request: VisibilityRequest): Flow<VisibilityRequestStatus>
}

