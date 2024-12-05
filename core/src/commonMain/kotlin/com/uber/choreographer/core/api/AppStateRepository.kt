package com.uber.choreographer.core.api

interface AppStateRepository {
    val componentStateRepository: ComponentStateRepository

    // Users can add additional repositories here
    // Observing overall app state changes if needed
}
