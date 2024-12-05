package com.uber.choreographer.core.impl

import com.uber.choreographer.core.api.ComponentInstanceId
import com.uber.choreographer.core.api.ComponentState
import com.uber.choreographer.core.api.ComponentStateRepository
import com.uber.choreographer.core.api.ComponentType
import com.uber.choreographer.core.api.MutableAppState
import com.uber.choreographer.core.api.PageType
import com.uber.choreographer.core.api.SlotType
import com.uber.choreographer.core.impl.extensions.ComponentVisibilityExtensions.isVisible
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

class InMemoryAppState(
    private val componentStateRepository: ComponentStateRepository = InMemoryComponentStateRepository()
) : MutableAppState {

    override val componentStates: StateFlow<Map<ComponentInstanceId, ComponentState>>
        get() = componentStateRepository.componentStates

    override fun getComponentState(instanceId: ComponentInstanceId): ComponentState? {
        return componentStateRepository.getComponentState(instanceId)
    }

    override fun getComponentStates(
        componentType: ComponentType,
        slotType: SlotType?,
        pageType: PageType?
    ): List<ComponentState> {
        return componentStateRepository.getComponentStates(componentType, slotType, pageType)
    }

    override fun getVisibleComponents(): List<ComponentState> {
        return componentStates.value.values.filter { it.visibility.isVisible() }
    }

    override fun getVisibleComponentsInSlot(
        slotType: SlotType,
        pageType: PageType?
    ): List<ComponentState> {
        return getVisibleComponents().filter { state ->
            state.slot == slotType && (pageType == null || state.page == pageType)
        }
    }

    override fun getVisibleComponentsInPage(pageType: PageType): List<ComponentState> {
        return getVisibleComponents().filter { state ->
            state.page == pageType
        }
    }

    override fun observeComponentState(instanceId: ComponentInstanceId): Flow<ComponentState?> {
        return componentStates.map { it[instanceId] }
    }

    override fun observeComponentStates(): Flow<Map<ComponentInstanceId, ComponentState>> {
        return componentStates
    }

    override suspend fun updateComponentState(componentState: ComponentState) {
        componentStateRepository.addOrUpdateComponentState(componentState)
    }

    override suspend fun removeComponentState(instanceId: ComponentInstanceId) {
        componentStateRepository.removeComponentState(instanceId)
    }
}
