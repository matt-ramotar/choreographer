package com.uber.choreographer.core.api

interface Bookkeeper {
    fun decision(visibilityDecision: VisibilityDecision)

    // Defers timespan to the user
    suspend fun bookkeeping(): Bookkeeping
}