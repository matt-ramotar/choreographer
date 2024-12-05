package com.uber.choreographer.pokedex.lib.choreographer

import com.uber.choreographer.core.api.ComponentVisibility
import com.uber.choreographer.dsl.DependenciesBuilder

object PokedexDependencies {

    val bottomBannerDependencies = DependenciesBuilder()
        .dependsOn(PokedexComponentType.Pill.type) {
            it.visibility == ComponentVisibility.NotVisible
        }

}