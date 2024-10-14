package com.uber.choreographer.core.impl

import com.uber.choreographer.core.api.Bookkeeper
import com.uber.choreographer.core.api.Bookkeeping
import com.uber.choreographer.core.api.ComponentInstanceId
import com.uber.choreographer.core.api.VisibilityDecision


class MemoryCachingBookkeeper : Bookkeeper {
    private val memoryCache = mutableMapOf<ComponentInstanceId, VisibilityDecision>()

    override fun decision(visibilityDecision: VisibilityDecision) {
        memoryCache[visibilityDecision.component] = visibilityDecision
    }

    override suspend fun bookkeeping(): Bookkeeping {
        return Bookkeeping(
            visibilityDecisions = memoryCache.values.toList()
        )
    }
}