package com.uber.choreographer.pokedex.lib.pokeApi.client

import com.uber.choreographer.pokedex.lib.pokeApi.model.Location
import com.uber.choreographer.pokedex.lib.pokeApi.model.LocationArea
import com.uber.choreographer.pokedex.lib.pokeApi.model.NamedApiResourceList
import com.uber.choreographer.pokedex.lib.pokeApi.model.PalParkArea
import com.uber.choreographer.pokedex.lib.pokeApi.model.Region

// Location-related endpoints
interface LocationClient {
    suspend fun getLocationList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getLocationAreaList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getPalParkAreaList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getRegionList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getLocation(id: Int): Location
    suspend fun getLocationArea(id: Int): LocationArea
    suspend fun getPalParkArea(id: Int): PalParkArea
    suspend fun getRegion(id: Int): Region
}