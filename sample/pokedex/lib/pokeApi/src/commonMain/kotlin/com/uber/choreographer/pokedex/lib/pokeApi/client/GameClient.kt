package com.uber.choreographer.pokedex.lib.pokeApi.client

import com.uber.choreographer.pokedex.lib.pokeApi.model.Generation
import com.uber.choreographer.pokedex.lib.pokeApi.model.NamedApiResourceList
import com.uber.choreographer.pokedex.lib.pokeApi.model.Pokedex
import com.uber.choreographer.pokedex.lib.pokeApi.model.Version
import com.uber.choreographer.pokedex.lib.pokeApi.model.VersionGroup

// Game-related endpoints
interface GameClient {
    suspend fun getGenerationList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getPokedexList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getVersionList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getVersionGroupList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getGeneration(id: Int): Generation
    suspend fun getPokedex(id: Int): Pokedex
    suspend fun getVersion(id: Int): Version
    suspend fun getVersionGroup(id: Int): VersionGroup
}