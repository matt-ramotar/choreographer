package com.uber.choreographer.core.api

data class ComponentState(
    val instanceId: ComponentInstanceId,
    val type: ComponentType,
    val visibility: ComponentVisibility,
    val requestStatus: VisibilityRequestStatus,
    val slot: SlotType? = null, // Not all components will be tied to a slot
    val page: PageType? = null, // Not all components will be tied to a page
)