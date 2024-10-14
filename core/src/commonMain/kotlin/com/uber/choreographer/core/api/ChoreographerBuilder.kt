package com.uber.choreographer.core.api

import com.uber.choreographer.core.impl.RealChoreographerBuilder
import com.uber.choreographer.core.impl.RuleEnforcer
import com.uber.choreographer.dsl.api.RuleSet
import com.uber.choreographer.dsl.api.RuleSetBuilder

interface ChoreographerBuilder<S : AppState> {
    fun ruleEnforcer(ruleEnforcer: RuleEnforcer<S>): ChoreographerBuilder<S>

    fun maxTimeDeferred(milliseconds: Long): ChoreographerBuilder<S>
    fun maxTimePendingReview(milliseconds: Long): ChoreographerBuilder<S>
    fun maxTimeUnderReview(milliseconds: Long): ChoreographerBuilder<S>
    fun maxWaitTimeAfterGrantingRequest(milliseconds: Long): ChoreographerBuilder<S>

    fun rules(init: RuleSetBuilder<S>.() -> Unit): ChoreographerBuilder<S>

    fun ruleSet(ruleSet: RuleSet<S>): ChoreographerBuilder<S>

    fun componentAllowList(componentAllowList: ComponentAllowList): ChoreographerBuilder<S>
    fun mutableAppState(mutableAppState: MutableAppState): ChoreographerBuilder<S>

    fun build(): Choreographer

    companion object {
        operator fun <S : AppState> invoke(): ChoreographerBuilder<S> {
            return RealChoreographerBuilder()
        }
    }
}