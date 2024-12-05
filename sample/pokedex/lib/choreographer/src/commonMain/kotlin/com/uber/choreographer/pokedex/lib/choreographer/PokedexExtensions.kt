package com.uber.choreographer.pokedex.lib.choreographer

import com.uber.choreographer.core.api.AppState
import com.uber.choreographer.core.api.Bookkeeping
import com.uber.choreographer.core.api.ComponentType

object PokedexExtensions {
    fun Bookkeeping.sessionImpressionCount(componentType: ComponentType): Int {
        TODO()
    }


    fun AppState.getCurrentPage(): PokedexPageType {
        TODO()
    }
}