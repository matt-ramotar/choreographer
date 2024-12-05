package com.uber.choreographer.dsl

fun interface Actions {
    fun handle(dependenciesSatisfied: Boolean, conditionsEvaluationResult: ConditionsEvaluationResult): Action
}