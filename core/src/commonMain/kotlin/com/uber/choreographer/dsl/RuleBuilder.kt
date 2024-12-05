package com.uber.choreographer.dsl

import com.uber.choreographer.core.api.AppState
import com.uber.choreographer.core.api.Bookkeeping
import com.uber.choreographer.core.api.VisibilityRequest

class RuleBuilder {
    private var name: String = ""
    private var componentSet: ComponentSet = ComponentSet.Only(included = emptySet()) // Default to applying to none
    private var dependencies: Dependencies = DependenciesBuilder() // Default to no dependencies
    private var actions: Actions = Actions { _, _ -> Action.Deny } // Default to denying
    private var conditions: Conditions = Conditions { _, _, _ -> ConditionsEvaluationResult.AllSatisfied }

    fun name(name: String) = apply { this.name = name }

    fun appliesTo(componentSet: ComponentSet) = apply {
        when (componentSet) {
            is ComponentSet.Any -> {
                when (val prevComponentSet = this.componentSet) {
                    is ComponentSet.Any -> {
                        val prevExcluded = prevComponentSet.excluded
                        val nextExcluded = prevExcluded + componentSet.excluded
                        val nextComponentSet = prevComponentSet.copy(excluded = nextExcluded)
                        this.componentSet = nextComponentSet
                    }

                    is ComponentSet.Only -> {
                        this.componentSet = componentSet
                    }
                }
            }

            is ComponentSet.Only -> {
                when (val prevComponentSet = this.componentSet) {
                    is ComponentSet.Any -> {
                        this.componentSet = componentSet
                    }

                    is ComponentSet.Only -> {
                        val prevIncluded = prevComponentSet.included
                        val nextIncluded = prevIncluded + componentSet.included
                        val nextComponentSet = prevComponentSet.copy(included = nextIncluded)
                        this.componentSet = nextComponentSet
                    }
                }
            }
        }
    }

    fun dependsOn(init: DependenciesBuilder.() -> Unit) = apply {
        dependencies = DependenciesBuilder().apply(init)
    }

    fun dependsOn(dependencies: Dependencies) = apply {
        this.dependencies = dependencies
    }

    fun whenConditions(init: (request: VisibilityRequest, appState: AppState, bookkeeping: Bookkeeping) -> ConditionsEvaluationResult) =
        apply {
            this.conditions = Conditions { request, appState, bookkeeping ->
                init(request, appState, bookkeeping)
            }
        }

    fun actions(init: (dependenciesSatisfied: Boolean, conditionsEvaluationResult: ConditionsEvaluationResult) -> Action) =
        apply {
            this.actions = Actions { dependenciesSatisfied, conditionsEvaluationResult ->
                init(
                    dependenciesSatisfied,
                    conditionsEvaluationResult
                )
            }
        }

    fun build(): Rule {
        return Rule(
            name = name,
            appliesTo = this.componentSet,
            dependencies = dependencies,
            conditions = conditions,
            actions = actions,
        )
    }
}
