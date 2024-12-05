package com.uber.choreographer.dsl

data class Rule(
    val name: String,
    val appliesTo: ComponentSet,
    val dependencies: Dependencies,
    val conditions: Conditions,
    val actions: Actions,
)


