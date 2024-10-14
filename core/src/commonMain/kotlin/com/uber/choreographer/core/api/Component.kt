package com.uber.choreographer.core.api


typealias ComponentInstanceId = String
typealias ComponentType = String

interface Component {
    val type: ComponentType
}

