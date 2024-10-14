package com.uber.choreographer.core.api


interface AppState {
    fun getComponentState(instanceId: ComponentInstanceId): ComponentState?
    fun getVisibleComponents(): List<ComponentState>
    fun getVisibleComponentsInSlot(slotType: SlotType, pageType: PageType?): List<ComponentState>
    fun getVisibleComponentsInPage(pageType: PageType): List<ComponentState>
    fun getComponentStates(): Collection<ComponentState>
    fun getDeferredComponentsForSlotInOrder(slotType: SlotType, pageType: PageType?): List<ComponentState>
}