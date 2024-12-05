package com.uber.choreographer.core.impl

import com.uber.choreographer.core.api.ComponentInstanceId
import com.uber.choreographer.core.api.ComponentState
import com.uber.choreographer.core.api.ComponentStateRepository
import com.uber.choreographer.core.api.ComponentType
import com.uber.choreographer.core.api.PageType
import com.uber.choreographer.core.api.SlotType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class InMemoryComponentStateRepository : ComponentStateRepository {
    private val mutex = Mutex()
    private val _componentStates = MutableStateFlow<Map<ComponentInstanceId, ComponentState>>(emptyMap())
    override val componentStates: StateFlow<Map<ComponentInstanceId, ComponentState>> = _componentStates.asStateFlow()

    override fun getComponentState(instanceId: ComponentInstanceId): ComponentState? {
        return _componentStates.value[instanceId]
    }

    override fun getComponentStates(
        componentType: ComponentType,
        slotType: SlotType?,
        pageType: PageType?
    ): List<ComponentState> {
        return _componentStates.value.values.filter { state ->
            state.type == componentType &&
                (slotType == null || state.slot == slotType) &&
                (pageType == null || state.page == pageType)
        }
    }

    override suspend fun addOrUpdateComponentState(componentState: ComponentState) {
        mutex.withLock {
            _componentStates.value = _componentStates.value.toMutableMap().apply {
                put(componentState.instanceId, componentState)
            }
        }
    }

    override suspend fun removeComponentState(instanceId: ComponentInstanceId) {
        mutex.withLock {
            _componentStates.value = _componentStates.value.toMutableMap().apply {
                remove(instanceId)
            }
        }
    }
}
