package com.uber.choreographer.dsl


class RuleSetBuilder {
    private val rules = mutableSetOf<Rule>()

    fun rule(init: RuleBuilder.() -> Unit) {
        val builder = RuleBuilder().apply(init)
        rules.add(builder.build())
    }

    fun use(rule: Rule) {
        rules.add(rule)
    }

    internal fun build(): RuleSet = RuleSet(rules)
}


