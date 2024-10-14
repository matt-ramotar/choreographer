package com.uber.choreographer.dsl.api

sealed class Action {
    data object Grant : Action()
    data object Deny : Action()
    data object Defer : Action()
}

