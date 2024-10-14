package com.uber.choreographer.core.api

import kotlinx.datetime.LocalDateTime

data class VisibilityDecision(
    val instanceId: ComponentInstanceId,
    val component: ComponentType,
    val slot: SlotType?,
    val page: PageType?,
    val granted: Boolean,
    val timestamp: LocalDateTime
)