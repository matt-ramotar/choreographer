package com.uber.choreographer.core.api

interface ChoreographerConfig {
    fun getComponentAllowList(componentType: ComponentType): ComponentAllowList
}