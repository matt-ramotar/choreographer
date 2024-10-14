package com.uber.choreographer.redux.api

fun interface Reducer<S: State> {
    operator fun invoke(state: S, action: Action): S
}