package com.uber.choreographer.dsl

import com.uber.choreographer.core.api.AppState

interface Dependencies {
    fun satisfied(appState: AppState): Boolean
}