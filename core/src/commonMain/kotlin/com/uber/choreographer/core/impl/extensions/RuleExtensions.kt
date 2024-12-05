package com.uber.choreographer.core.impl.extensions

import com.uber.choreographer.core.api.VisibilityRequest
import com.uber.choreographer.dsl.ComponentSet
import com.uber.choreographer.dsl.Rule

object RuleExtensions {
    fun Rule.isApplicable(request: VisibilityRequest): Boolean {
        return when (val appliesTo = this.appliesTo) {
            is ComponentSet.Any -> !appliesTo.excluded.contains(request.componentType)
            is ComponentSet.Only -> appliesTo.included.contains(request.componentType)
        }
    }
}

