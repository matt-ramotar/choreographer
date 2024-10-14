package com.uber.choreographer.core.impl

import com.uber.choreographer.core.api.ComponentInstanceId
import com.uber.choreographer.core.api.ComponentState
import com.uber.choreographer.core.api.MutableAppState
import com.uber.choreographer.core.api.PageType
import com.uber.choreographer.core.api.SlotType
import com.uber.choreographer.core.api.VisibilityRequestStatus
import com.uber.choreographer.core.impl.ComponentStateExtensions.isVisible

open class DefaultAppState : MutableAppState {
    private val componentStates = mutableMapOf<ComponentInstanceId, ComponentState>()
    private val componentsBySlot = mutableMapOf<SlotType, MutableSet<ComponentInstanceId>>()
    private val componentsByPage = mutableMapOf<PageType, MutableSet<ComponentInstanceId>>()

    override fun getComponentState(instanceId: ComponentInstanceId): ComponentState? {
        return componentStates[instanceId]
    }

    override fun getVisibleComponents(): List<ComponentState> {
        return componentStates.values.filter { it.isVisible() }
    }

    override fun getVisibleComponentsInSlot(slotType: SlotType, pageType: PageType?): List<ComponentState> {
        val instanceIds = componentsBySlot[slotType] ?: return emptyList()
        return instanceIds.mapNotNull { instanceId ->
            componentStates[instanceId]?.takeIf { componentState ->
                componentState.isVisible() && (componentState.page == pageType || pageType == null)
            }
        }

    }

    override fun getVisibleComponentsInPage(pageType: PageType): List<ComponentState> {
        val instanceIds = componentsByPage[pageType] ?: return emptyList()
        return instanceIds.mapNotNull { instanceId ->
            componentStates[instanceId]?.takeIf { it.isVisible() }
        }
    }

    override fun getComponentStates(): Collection<ComponentState> {
        return componentStates.values
    }

    override fun getDeferredComponentsForSlotInOrder(slotType: SlotType, pageType: PageType?): List<ComponentState> {
        return componentStates.values.filter {
            (it.requestStatus is VisibilityRequestStatus.Deferred &&
                it.slot == slotType && (pageType == null || it.page == pageType))
        }
    }

    override suspend fun updateComponentState(componentState: ComponentState) {
        val prevState = componentStates[componentState.instanceId]

        // Update indexing for slot
        prevState?.slot?.let { slot ->
            componentsBySlot[slot]?.remove(componentState.instanceId)
        }
        componentState.slot?.let { slot ->
            componentsBySlot.getOrPut(slot) { mutableSetOf() }.add(componentState.instanceId)
        }


        // Update indexing for page
        prevState?.page?.let { page ->
            componentsByPage[page]?.remove(componentState.instanceId)
        }
        componentState.page?.let { page ->
            componentsByPage.getOrPut(page) { mutableSetOf() }.add(componentState.instanceId)
        }

        // Update deferred indexing flor slot


        componentStates[componentState.instanceId] = componentState
    }


}