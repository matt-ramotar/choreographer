package com.uber.choreographer.dsl.api

import com.uber.choreographer.core.api.ComponentType

sealed class ComponentSet {

    data class Only(
        val included: Set<ComponentType>
    ) : ComponentSet()

    data class Any(
        val excluded: Set<ComponentType> = setOf()
    ) : ComponentSet()
}