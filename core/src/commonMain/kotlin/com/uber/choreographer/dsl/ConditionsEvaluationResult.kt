package com.uber.choreographer.dsl

sealed class ConditionsEvaluationResult {
    data object AllSatisfied : ConditionsEvaluationResult()
    data object SomeSatisfied : ConditionsEvaluationResult()
    data object NoneSatisfied : ConditionsEvaluationResult()
}