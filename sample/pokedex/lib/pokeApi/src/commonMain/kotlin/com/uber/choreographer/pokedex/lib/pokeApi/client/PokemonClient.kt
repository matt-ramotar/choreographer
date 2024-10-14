package com.uber.choreographer.pokedex.lib.pokeApi.client

import com.uber.choreographer.pokedex.lib.pokeApi.model.Ability
import com.uber.choreographer.pokedex.lib.pokeApi.model.ApiResourceList
import com.uber.choreographer.pokedex.lib.pokeApi.model.Characteristic
import com.uber.choreographer.pokedex.lib.pokeApi.model.EggGroup
import com.uber.choreographer.pokedex.lib.pokeApi.model.Gender
import com.uber.choreographer.pokedex.lib.pokeApi.model.GrowthRate
import com.uber.choreographer.pokedex.lib.pokeApi.model.LocationAreaEncounter
import com.uber.choreographer.pokedex.lib.pokeApi.model.NamedApiResourceList
import com.uber.choreographer.pokedex.lib.pokeApi.model.Nature
import com.uber.choreographer.pokedex.lib.pokeApi.model.PokeathlonStat
import com.uber.choreographer.pokedex.lib.pokeApi.model.Pokemon
import com.uber.choreographer.pokedex.lib.pokeApi.model.PokemonColor
import com.uber.choreographer.pokedex.lib.pokeApi.model.PokemonForm
import com.uber.choreographer.pokedex.lib.pokeApi.model.PokemonHabitat
import com.uber.choreographer.pokedex.lib.pokeApi.model.PokemonShape
import com.uber.choreographer.pokedex.lib.pokeApi.model.PokemonSpecies
import com.uber.choreographer.pokedex.lib.pokeApi.model.Stat
import com.uber.choreographer.pokedex.lib.pokeApi.model.Type

// Pokemon-related endpoints
interface PokemonClient {
    suspend fun getAbilityList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getCharacteristicList(offset: Int, limit: Int): ApiResourceList
    suspend fun getEggGroupList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getGenderList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getGrowthRateList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getNatureList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getPokeathlonStatList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getPokemonList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getPokemonColorList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getPokemonFormList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getPokemonHabitatList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getPokemonShapeList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getPokemonSpeciesList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getStatList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getTypeList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getAbility(id: Int): Ability
    suspend fun getCharacteristic(id: Int): Characteristic
    suspend fun getEggGroup(id: Int): EggGroup
    suspend fun getGender(id: Int): Gender
    suspend fun getGrowthRate(id: Int): GrowthRate
    suspend fun getNature(id: Int): Nature
    suspend fun getPokeathlonStat(id: Int): PokeathlonStat
    suspend fun getPokemon(id: Int): Pokemon
    suspend fun getPokemonEncounterList(id: Int): List<LocationAreaEncounter>
    suspend fun getPokemonColor(id: Int): PokemonColor
    suspend fun getPokemonForm(id: Int): PokemonForm
    suspend fun getPokemonHabitat(id: Int): PokemonHabitat
    suspend fun getPokemonShape(id: Int): PokemonShape
    suspend fun getPokemonSpecies(id: Int): PokemonSpecies
    suspend fun getStat(id: Int): Stat
    suspend fun getType(id: Int): Type
}