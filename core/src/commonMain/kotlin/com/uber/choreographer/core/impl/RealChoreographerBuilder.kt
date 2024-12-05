package com.uber.choreographer.core.impl

import com.uber.choreographer.core.api.Choreographer
import com.uber.choreographer.core.api.ChoreographerBuilder
import com.uber.choreographer.core.api.ComponentAllowList
import com.uber.choreographer.core.api.MutableAppState
import com.uber.choreographer.core.api.RuleEngine
import com.uber.choreographer.dsl.RuleSet
import com.uber.choreographer.dsl.RuleSetBuilder

class RealChoreographerBuilder : ChoreographerBuilder {
    private var ruleSet: RuleSet? = null
    private var mutableAppState: MutableAppState = InMemoryAppState()


    override fun rules(init: RuleSetBuilder.() -> Unit): ChoreographerBuilder = apply {
        val ruleSetBuilder = RuleSetBuilder().apply(init)
        ruleSet = ruleSetBuilder.build()
    }

    override fun ruleEngine(ruleEngine: RuleEngine): ChoreographerBuilder {
        TODO("Not yet implemented")
    }

    override fun maxTimeDeferred(milliseconds: Long): ChoreographerBuilder {
        TODO("Not yet implemented")
    }

    override fun maxTimePendingReview(milliseconds: Long): ChoreographerBuilder {
        TODO("Not yet implemented")
    }

    override fun maxTimeUnderReview(milliseconds: Long): ChoreographerBuilder {
        TODO("Not yet implemented")
    }

    override fun maxWaitTimeAfterGrantingRequest(milliseconds: Long): ChoreographerBuilder {
        TODO("Not yet implemented")
    }

    override fun componentAllowList(componentAllowList: ComponentAllowList): ChoreographerBuilder {
        TODO("Not yet implemented")
    }

    override fun ruleSet(ruleSet: RuleSet): ChoreographerBuilder {
        TODO("Not yet implemented")
    }

    override fun mutableAppState(mutableAppState: MutableAppState): ChoreographerBuilder = apply {
        this.mutableAppState = mutableAppState
    }

    override fun build(): Choreographer {
        TODO("Not yet implemented")
    }
}