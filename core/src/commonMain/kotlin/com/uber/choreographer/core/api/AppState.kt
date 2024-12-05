package com.uber.choreographer.core.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow


interface AppState {
    val componentStates: StateFlow<Map<ComponentInstanceId, ComponentState>>

    fun getComponentState(instanceId: ComponentInstanceId): ComponentState?
    fun getComponentStates(
        componentType: ComponentType,
        slotType: SlotType? = null,
        pageType: PageType? = null
    ): List<ComponentState>

    fun getVisibleComponents(): List<ComponentState>
    fun getVisibleComponentsInSlot(slotType: SlotType, pageType: PageType? = null): List<ComponentState>
    fun getVisibleComponentsInPage(pageType: PageType): List<ComponentState>

    fun observeComponentState(instanceId: ComponentInstanceId): Flow<ComponentState?>
    fun observeComponentStates(): Flow<Map<ComponentInstanceId, ComponentState>>
}