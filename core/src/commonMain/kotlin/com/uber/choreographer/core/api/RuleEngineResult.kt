package com.uber.choreographer.core.api

sealed class RuleEngineResult {
    data object Granted : RuleEngineResult()
    data object Denied : RuleEngineResult()
    data object Pending : RuleEngineResult()
}