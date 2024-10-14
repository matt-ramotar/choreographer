package com.uber.choreographer.pokedex.lib.pokeApi.client

import com.uber.choreographer.pokedex.lib.pokeApi.model.Language
import com.uber.choreographer.pokedex.lib.pokeApi.model.NamedApiResourceList

// Utility endpoints
interface UtilityClient {
    suspend fun getLanguageList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getLanguage(id: Int): Language
}