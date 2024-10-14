package com.uber.choreographer.dsl

sealed class EvaluationResult {
    data object SatisfiedAllConditions : EvaluationResult()
    data object SatisfiedSomeConditions : EvaluationResult()
    data object DidNotSatisfyAnyConditions : EvaluationResult()
}