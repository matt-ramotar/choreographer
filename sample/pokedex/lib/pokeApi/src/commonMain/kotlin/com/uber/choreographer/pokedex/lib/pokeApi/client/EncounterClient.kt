package com.uber.choreographer.pokedex.lib.pokeApi.client

import com.uber.choreographer.pokedex.lib.pokeApi.model.EncounterCondition
import com.uber.choreographer.pokedex.lib.pokeApi.model.EncounterConditionValue
import com.uber.choreographer.pokedex.lib.pokeApi.model.EncounterMethod
import com.uber.choreographer.pokedex.lib.pokeApi.model.NamedApiResourceList

// Encounter-related endpoints
interface EncounterClient {
    suspend fun getEncounterMethodList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getEncounterConditionList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getEncounterConditionValueList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getEncounterMethod(id: Int): EncounterMethod
    suspend fun getEncounterCondition(id: Int): EncounterCondition
    suspend fun getEncounterConditionValue(id: Int): EncounterConditionValue
}