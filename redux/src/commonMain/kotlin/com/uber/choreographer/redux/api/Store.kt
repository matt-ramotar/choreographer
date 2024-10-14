package com.uber.choreographer.redux.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface Store<S : State> {

    val state: StateFlow<S>

    suspend fun dispatch(action: Action)

    fun <T> select(selector: (S) -> T): Flow<T>
}