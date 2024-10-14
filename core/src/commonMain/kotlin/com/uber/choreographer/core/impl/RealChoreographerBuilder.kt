package com.uber.choreographer.core.impl

import com.uber.choreographer.core.api.AppState
import com.uber.choreographer.core.api.Choreographer
import com.uber.choreographer.core.api.ChoreographerBuilder
import com.uber.choreographer.core.api.ComponentAllowList
import com.uber.choreographer.core.api.MutableAppState
import com.uber.choreographer.dsl.RuleSet
import com.uber.choreographer.dsl.RuleSetBuilder

class RealChoreographerBuilder<S : AppState> : ChoreographerBuilder<S> {
    private var ruleSet: RuleSet<S>? = null
    private var mutableAppState: MutableAppState = DefaultAppState()

    override fun ruleEnforcer(ruleEnforcer: RuleEnforcer<S>): ChoreographerBuilder<S> {
        TODO("Not yet implemented")
    }

    override fun rules(init: RuleSetBuilder<S>.() -> Unit): ChoreographerBuilder<S> = apply {
        val ruleSetBuilder = RuleSetBuilder<S>().apply(init)
        ruleSet = ruleSetBuilder.build()
    }

    override fun maxTimeDeferred(milliseconds: Long): ChoreographerBuilder<S> {
        TODO("Not yet implemented")
    }

    override fun maxTimePendingReview(milliseconds: Long): ChoreographerBuilder<S> {
        TODO("Not yet implemented")
    }

    override fun maxTimeUnderReview(milliseconds: Long): ChoreographerBuilder<S> {
        TODO("Not yet implemented")
    }

    override fun maxWaitTimeAfterGrantingRequest(milliseconds: Long): ChoreographerBuilder<S> {
        TODO("Not yet implemented")
    }

    override fun componentAllowList(componentAllowList: ComponentAllowList): ChoreographerBuilder<S> {
        TODO("Not yet implemented")
    }

    override fun ruleSet(ruleSet: RuleSet<S>): ChoreographerBuilder<S> {
        TODO("Not yet implemented")
    }

    override fun mutableAppState(mutableAppState: MutableAppState): ChoreographerBuilder<S> = apply {
        this.mutableAppState = mutableAppState
    }

    override fun build(): Choreographer {
        TODO("Not yet implemented")
    }
}