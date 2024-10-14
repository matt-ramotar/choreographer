package com.uber.choreographer.core.impl

import com.uber.choreographer.core.api.ComponentState
import com.uber.choreographer.core.api.ComponentVisibility

internal object ComponentStateExtensions {
    fun ComponentState.isVisible() = visibility is ComponentVisibility.Visible
}