package com.uber.choreographer.pokedex.lib.pokeApi.client

import com.uber.choreographer.pokedex.lib.pokeApi.model.ApiResourceList
import com.uber.choreographer.pokedex.lib.pokeApi.model.Machine

// Machine-related endpoints
interface MachineClient {
    suspend fun getMachineList(offset: Int, limit: Int): ApiResourceList
    suspend fun getMachine(id: Int): Machine
}