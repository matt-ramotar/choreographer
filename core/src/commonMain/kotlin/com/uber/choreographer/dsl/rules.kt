package com.uber.choreographer.dsl

import com.uber.choreographer.core.api.AppState

fun <S : AppState> rules(init: RuleSetBuilder<S>.() -> Unit): RuleSet<S> {
    return RuleSetBuilder<S>().apply(init).build()
}