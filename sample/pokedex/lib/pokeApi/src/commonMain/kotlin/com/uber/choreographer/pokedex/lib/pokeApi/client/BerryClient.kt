package com.uber.choreographer.pokedex.lib.pokeApi.client

import com.uber.choreographer.pokedex.lib.pokeApi.model.Berry
import com.uber.choreographer.pokedex.lib.pokeApi.model.BerryFirmness
import com.uber.choreographer.pokedex.lib.pokeApi.model.BerryFlavor
import com.uber.choreographer.pokedex.lib.pokeApi.model.NamedApiResourceList

// Berry-related endpoints
interface BerryClient {
    suspend fun getBerryList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getBerryFirmnessList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getBerryFlavorList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getBerry(id: Int): Berry
    suspend fun getBerryFirmness(id: Int): BerryFirmness
    suspend fun getBerryFlavor(id: Int): BerryFlavor
}