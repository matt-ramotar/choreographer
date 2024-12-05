package com.uber.choreographer.core.impl.extensions

import com.uber.choreographer.core.api.ComponentState
import com.uber.choreographer.core.api.ComponentVisibility

internal object ComponentStateExtensions {
    fun ComponentState.isVisible() = visibility is ComponentVisibility.Visible
}