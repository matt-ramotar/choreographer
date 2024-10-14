package com.uber.choreographer.core.api

enum class DeniedRequestReason {
    RuleNotSatisfied,
    LowPriority,
    DependenciesUnmet,
    GrantedButConsumerDidNotRespond,
    ExceededMaxTimePendingReview,
    ExceededMaxTimeDeferred,
    ExceededMaxTimeUnderReview,
    Other
}