package com.uber.choreographer.pokedex.lib.choreographer

import com.uber.choreographer.core.api.Choreographer
import com.uber.choreographer.core.api.ChoreographerBuilder
import com.uber.choreographer.pokedex.lib.choreographer.PokedexRules.ruleSet

class PokedexChoreographerFactory {
    fun create(): Choreographer {
        val builder = ChoreographerBuilder.invoke<PokedexAppState>()

        val ruleSet = ruleSet()

        builder.ruleSet(ruleSet)

        return builder.build()
    }
}
