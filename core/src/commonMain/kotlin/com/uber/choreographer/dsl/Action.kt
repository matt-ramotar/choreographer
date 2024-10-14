package com.uber.choreographer.dsl

sealed class Action {
    data object Grant : Action()
    data object Deny : Action()
    data object Defer : Action()
}

