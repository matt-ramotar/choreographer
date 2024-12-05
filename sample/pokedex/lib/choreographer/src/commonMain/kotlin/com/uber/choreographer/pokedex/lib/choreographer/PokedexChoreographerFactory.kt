package com.uber.choreographer.pokedex.lib.choreographer

import com.uber.choreographer.core.api.Choreographer
import com.uber.choreographer.core.api.ChoreographerBuilder
import com.uber.choreographer.dsl.rules

class PokedexChoreographerFactory {
    fun create(): Choreographer {
        val builder = ChoreographerBuilder()
        val ruleSet = with(PokedexRules) { rules(bottomBannerVisibilityRule) }
        builder.ruleSet(ruleSet)
        return builder.build()
    }
}
