package com.uber.choreographer.core.api

sealed interface ComponentVisibility {
    data object NotVisible : ComponentVisibility
    sealed interface Visible : ComponentVisibility {
        data object AnimatingIn : Visible
        data object AnimatingOut : Visible
        data object Idle : Visible
    }
}