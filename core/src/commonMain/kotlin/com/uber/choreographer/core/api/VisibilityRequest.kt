package com.uber.choreographer.core.api

data class VisibilityRequest(
    val componentType: ComponentType,
    val slotType: SlotType?,
    val pageType: PageType?,
    val deferrable: Boolean,
    val priority: Int = 0,
)