package com.uber.choreographer.core.api

sealed class ComponentAllowList {
    data class AllowAny(
        val blockedSlots: List<SlotType> = emptyList(),
        val blockedPages: List<PageType> = emptyList()
    ) : ComponentAllowList()

    data class AllowOnly(
        val allowedSlots: List<SlotType>,
        val allowedPages: List<PageType>
    ) : ComponentAllowList()
}