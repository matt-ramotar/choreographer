package com.uber.choreographer.redux.impl

import com.uber.choreographer.redux.api.Action
import com.uber.choreographer.redux.api.State
import com.uber.choreographer.redux.api.Dispatcher
import com.uber.choreographer.redux.api.Middleware
import com.uber.choreographer.redux.api.Reducer
import com.uber.choreographer.redux.api.Store
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class RealStore<S : State>(
    initialState: S,
    private val reducer: Reducer<S>,
    middlewares: List<Middleware<S>> = emptyList()
) : Store<S> {
    // Internal state flow to hold the state
    private val _state: MutableStateFlow<S> = MutableStateFlow(initialState)

    // Expose the state flow for state observation
    override val state: StateFlow<S> = _state.asStateFlow()

    private var dispatcher: Dispatcher

    init {
        // Create a chain of middleware
        val initialDispatcher = Dispatcher { action ->
            updateState(action)
        }

        // Wrap dispatch with middleware
        dispatcher = middlewares.foldRight(initialDispatcher) { middleware, next ->
            middleware.invoke(this).invoke(next)
        }
    }

    // Dispatch method to dispatch actions
    override suspend fun dispatch(action: Action) {
        dispatcher.invoke(action)
    }

    // Select a part of the state using a selector function
    override fun <T> select(selector: (S) -> T): Flow<T> {
        return state.map { selector(it) }
    }

    private fun updateState(action: Action) {
        val nextState = reducer(_state.value, action) // Apply the reducer to the current state
        _state.value = nextState
    }
}
