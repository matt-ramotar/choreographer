package com.uber.choreographer.core.impl

import com.uber.choreographer.core.api.Bookkeeper
import com.uber.choreographer.core.api.Bookkeeping
import com.uber.choreographer.core.api.VisibilityDecision
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock


class InMemoryBookkeeper : Bookkeeper {
    private val mutex = Mutex()
    private val decisions = mutableListOf<VisibilityDecision>()

    override suspend fun recordDecision(visibilityDecision: VisibilityDecision) {
        mutex.withLock {
            decisions.add(visibilityDecision)
        }
    }

    override suspend fun bookkeeping(): Bookkeeping {
        return Bookkeeping(
            visibilityDecisions = decisions.toList()
        )
    }
}