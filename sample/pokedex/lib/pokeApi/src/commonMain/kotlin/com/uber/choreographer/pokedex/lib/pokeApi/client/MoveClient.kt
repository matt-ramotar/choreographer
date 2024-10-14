package com.uber.choreographer.pokedex.lib.pokeApi.client

import com.uber.choreographer.pokedex.lib.pokeApi.model.Move
import com.uber.choreographer.pokedex.lib.pokeApi.model.MoveAilment
import com.uber.choreographer.pokedex.lib.pokeApi.model.MoveBattleStyle
import com.uber.choreographer.pokedex.lib.pokeApi.model.MoveCategory
import com.uber.choreographer.pokedex.lib.pokeApi.model.MoveDamageClass
import com.uber.choreographer.pokedex.lib.pokeApi.model.MoveLearnMethod
import com.uber.choreographer.pokedex.lib.pokeApi.model.MoveTarget
import com.uber.choreographer.pokedex.lib.pokeApi.model.NamedApiResourceList

// Move-related endpoints
interface MoveClient {
    suspend fun getMoveList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getMoveAilmentList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getMoveBattleStyleList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getMoveCategoryList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getMoveDamageClassList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getMoveLearnMethodList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getMoveTargetList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getMove(id: Int): Move
    suspend fun getMoveAilment(id: Int): MoveAilment
    suspend fun getMoveBattleStyle(id: Int): MoveBattleStyle
    suspend fun getMoveCategory(id: Int): MoveCategory
    suspend fun getMoveDamageClass(id: Int): MoveDamageClass
    suspend fun getMoveLearnMethod(id: Int): MoveLearnMethod
    suspend fun getMoveTarget(id: Int): MoveTarget
}