package com.uber.choreographer.core.api

import com.uber.choreographer.core.impl.RealChoreographerBuilder
import com.uber.choreographer.dsl.RuleSet
import com.uber.choreographer.dsl.RuleSetBuilder

interface ChoreographerBuilder {
    fun ruleEngine(ruleEngine: RuleEngine): ChoreographerBuilder

    fun maxTimeDeferred(milliseconds: Long): ChoreographerBuilder
    fun maxTimePendingReview(milliseconds: Long): ChoreographerBuilder
    fun maxTimeUnderReview(milliseconds: Long): ChoreographerBuilder
    fun maxWaitTimeAfterGrantingRequest(milliseconds: Long): ChoreographerBuilder

    fun rules(init: RuleSetBuilder.() -> Unit): ChoreographerBuilder

    fun ruleSet(ruleSet: RuleSet): ChoreographerBuilder

    fun componentAllowList(componentAllowList: ComponentAllowList): ChoreographerBuilder
    fun mutableAppState(mutableAppState: MutableAppState): ChoreographerBuilder

    fun build(): Choreographer

    companion object {
        operator fun invoke(): ChoreographerBuilder {
            return RealChoreographerBuilder()
        }
    }
}