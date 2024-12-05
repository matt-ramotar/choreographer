package com.uber.choreographer.core.api

import kotlinx.coroutines.flow.StateFlow

interface ComponentStateRepository {
    val componentStates: StateFlow<Map<ComponentInstanceId, ComponentState>>

    fun getComponentState(instanceId: ComponentInstanceId): ComponentState?
    fun getComponentStates(
        componentType: ComponentType,
        slotType: SlotType? = null,
        pageType: PageType? = null
    ): List<ComponentState>

    suspend fun addOrUpdateComponentState(componentState: ComponentState)
    suspend fun removeComponentState(instanceId: ComponentInstanceId)
}
