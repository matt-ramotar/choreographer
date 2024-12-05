package com.uber.choreographer.dsl

import com.uber.choreographer.core.api.AppState
import com.uber.choreographer.core.api.ComponentState
import com.uber.choreographer.core.api.ComponentType
import com.uber.choreographer.core.api.PageType
import com.uber.choreographer.core.api.SlotType

class DependenciesBuilder : Dependencies {
    private val conditions = mutableListOf<(AppState) -> Boolean>()

    fun dependsOn(
        componentType: ComponentType,
        slotType: SlotType? = null,
        pageType: PageType? = null,
        condition: (ComponentState) -> Boolean = { true }
    ) = apply {
        conditions.add { appState ->
            appState.getComponentStates(componentType, slotType, pageType).any(condition)
        }
    }

    fun and(init: DependenciesBuilder.() -> Unit) = apply {
        val subDependency = DependenciesBuilder().apply(init)
        conditions.add { appState -> subDependency.satisfied(appState) }
    }

    fun or(init: DependenciesBuilder.() -> Unit) = apply {
        val subDependency = DependenciesBuilder().apply(init)
        val previousConditions = conditions.toList()
        conditions.clear()
        conditions.add { appState ->
            previousConditions.any { it(appState) } || subDependency.satisfied(appState)
        }
    }

    fun not(init: DependenciesBuilder.() -> Unit) = apply {
        val subDependency = DependenciesBuilder().apply(init)
        conditions.add { appState -> !subDependency.satisfied(appState) }
    }

    override fun satisfied(appState: AppState): Boolean {
        return conditions.all { it(appState) }
    }
}