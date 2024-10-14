pluginManagement {
    includeBuild("convention-plugins")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}


dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "choreographer"

// Lib
include(":core")

// Samples

/// Pokedex
//// App
include(":sample:pokedex:app")

//// Feat
include(":sample:pokedex:feat:homeTab")

//// Lib

///// Choreographer
include(":sample:pokedex:lib:choreographer")

///// Design System
include(":sample:pokedex:lib:designSystem:component:banner")
include(":sample:pokedex:lib:designSystem:component:bottomSheet")
include(":sample:pokedex:lib:designSystem:component:dialog")
include(":sample:pokedex:lib:designSystem:component:drawer")
include(":sample:pokedex:lib:designSystem:component:fab")
include(":sample:pokedex:lib:designSystem:component:pill")
include(":sample:pokedex:lib:designSystem:component:snackbar")
include(":sample:pokedex:lib:designSystem:component:tooltip")
include(":sample:pokedex:lib:designSystem:images")

///// Poke API
include(":sample:pokedex:lib:pokeApi")
