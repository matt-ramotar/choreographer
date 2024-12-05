package com.uber.choreographer.core.impl.extensions

import com.uber.choreographer.core.api.ComponentVisibility

object ComponentVisibilityExtensions {
    fun ComponentVisibility.isVisible(): Boolean {
        return this is ComponentVisibility.Visible
    }
}