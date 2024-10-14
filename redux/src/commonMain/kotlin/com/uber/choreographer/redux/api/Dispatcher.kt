package com.uber.choreographer.redux.api

fun interface Dispatcher {
    suspend operator fun invoke(action: Action): Any
}