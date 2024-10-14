package com.uber.choreographer.dsl


import com.uber.choreographer.core.api.AppState


class RuleSetBuilder<S : AppState> {
    private val rules = mutableSetOf<Rule<S>>()

    fun rule(init: RuleBuilder<S>.() -> Unit) {
        val builder = RuleBuilder<S>().apply(init)
        rules.add(builder.build())
    }

    internal fun build(): RuleSet<S> = rules
}


