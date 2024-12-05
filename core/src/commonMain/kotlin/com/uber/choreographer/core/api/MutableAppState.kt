package com.uber.choreographer.core.api

interface MutableAppState : AppState {
    suspend fun updateComponentState(componentState: ComponentState)
    suspend fun removeComponentState(instanceId: ComponentInstanceId)
}