package com.uber.choreographer.redux.api

import com.uber.choreographer.redux.impl.RealStore

fun interface Middleware<S : State> {
    operator fun invoke(store: RealStore<S>): (next: Dispatcher) -> Dispatcher
}

