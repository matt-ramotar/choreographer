package com.uber.choreographer.core.impl

import com.uber.choreographer.core.api.ChoreographerConfig
import com.uber.choreographer.core.api.ComponentAllowList
import com.uber.choreographer.core.api.ComponentType

class DefaultChoreographerConfig : ChoreographerConfig {
    private val componentAllowLists = mutableMapOf<ComponentType, ComponentAllowList>()

    override fun getComponentAllowList(componentType: ComponentType): ComponentAllowList {
        return componentAllowLists[componentType] ?: ComponentAllowList.AllowAny()
    }
}