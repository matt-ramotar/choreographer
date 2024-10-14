package com.uber.choreographer.dsl.api

import com.uber.choreographer.core.api.AppState
import com.uber.choreographer.core.api.ComponentType

class RuleBuilder<S : AppState> {

    private var name: String? = null
    private var priority: Int = 0
    private var conditionsEvaluator: ConditionsEvaluator<S> = ConditionsEvaluator { _ -> true }
    private var actionCreator: ((Boolean) -> Action)? = null
    private var componentSet: ComponentSet = ComponentSet.Any()

    fun named(name: String) {
        this.name = name
    }

    fun whenever(
        conditionsEvaluator: (conditions: Conditions<S>) -> Boolean
    ) {
        this.conditionsEvaluator = ConditionsEvaluator(conditionsEvaluator)
    }

    fun then(actionCreator: (satisfiedConditions: Boolean) -> Action) {
        this.actionCreator = actionCreator
    }

    fun forOnly(components: Set<ComponentType>) {
        this.componentSet = ComponentSet.Only(components)
    }

    fun forOnly(component: ComponentType) {
        this.componentSet = ComponentSet.Only(setOf(component))
    }

    fun forAnyComponent(exceptFor: Set<ComponentType> = emptySet()) {
        this.componentSet = ComponentSet.Any(exceptFor)
    }

    fun priority(priority: Int) {
        this.priority = priority
    }

    internal fun build(): Rule<S> {
        return Rule(
            name = name ?: error("Name is required."),
            conditionsEvaluator = conditionsEvaluator,
            actionCreator = actionCreator ?: error("ActionCreator is required."),
            priority = priority,
            componentSet = componentSet
        )
    }
}