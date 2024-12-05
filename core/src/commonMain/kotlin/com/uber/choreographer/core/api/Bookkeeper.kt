package com.uber.choreographer.core.api

interface Bookkeeper {
    suspend fun recordDecision(visibilityDecision: VisibilityDecision)

    // Defers timespan to the user
    suspend fun bookkeeping(): Bookkeeping
}