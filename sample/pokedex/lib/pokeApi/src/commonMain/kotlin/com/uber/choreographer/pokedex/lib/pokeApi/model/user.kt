package com.uber.choreographer.pokedex.lib.pokeApi.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val name: String,
    val email: String,
    val avatarUrl: String
)