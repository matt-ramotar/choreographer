package com.uber.choreographer.pokedex.lib.pokeApi.client

import com.uber.choreographer.pokedex.lib.pokeApi.model.ApiResourceList
import com.uber.choreographer.pokedex.lib.pokeApi.model.EvolutionChain
import com.uber.choreographer.pokedex.lib.pokeApi.model.EvolutionTrigger
import com.uber.choreographer.pokedex.lib.pokeApi.model.NamedApiResourceList

// Evolution-related endpoints
interface EvolutionClient {
    suspend fun getEvolutionChainList(offset: Int, limit: Int): ApiResourceList
    suspend fun getEvolutionTriggerList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getEvolutionChain(id: Int): EvolutionChain
    suspend fun getEvolutionTrigger(id: Int): EvolutionTrigger
}