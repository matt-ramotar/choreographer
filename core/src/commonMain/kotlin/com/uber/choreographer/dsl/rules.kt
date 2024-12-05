package com.uber.choreographer.dsl

fun ruleSet(init: MutableSet<Rule>.() -> Unit): RuleSet {
    val rules = mutableSetOf<Rule>()
    with(rules) {
        init()
    }

    return RuleSet(rules)
}

fun rules(vararg rules: Rule): RuleSet =
    RuleSet(rules.toSet())

fun rule(init: RuleBuilder.() -> Unit): Rule {
    return RuleBuilder().apply(init).build()
}

