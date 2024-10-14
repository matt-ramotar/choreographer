package com.uber.choreographer.pokedex.lib.pokeApi.client

import com.uber.choreographer.pokedex.lib.pokeApi.model.ApiResourceList
import com.uber.choreographer.pokedex.lib.pokeApi.model.ContestEffect
import com.uber.choreographer.pokedex.lib.pokeApi.model.ContestType
import com.uber.choreographer.pokedex.lib.pokeApi.model.NamedApiResourceList
import com.uber.choreographer.pokedex.lib.pokeApi.model.SuperContestEffect

// Contest-related endpoints
interface ContestClient {
    suspend fun getContestTypeList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getContestEffectList(offset: Int, limit: Int): ApiResourceList
    suspend fun getSuperContestEffectList(offset: Int, limit: Int): ApiResourceList
    suspend fun getContestType(id: Int): ContestType
    suspend fun getContestEffect(id: Int): ContestEffect
    suspend fun getSuperContestEffect(id: Int): SuperContestEffect
}