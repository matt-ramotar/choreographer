package com.uber.choreographer.pokedex.lib.choreographer

import com.uber.choreographer.core.api.ComponentInstanceId

data class PokedexComponent(
    val instanceId: ComponentInstanceId,
    val type: PokedexComponentType,
    val slotType: PokedexSlotType
)