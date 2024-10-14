package com.uber.choreographer.pokedex.lib.pokeApi.client

import com.uber.choreographer.pokedex.lib.pokeApi.model.Item
import com.uber.choreographer.pokedex.lib.pokeApi.model.ItemAttribute
import com.uber.choreographer.pokedex.lib.pokeApi.model.ItemCategory
import com.uber.choreographer.pokedex.lib.pokeApi.model.ItemFlingEffect
import com.uber.choreographer.pokedex.lib.pokeApi.model.ItemPocket
import com.uber.choreographer.pokedex.lib.pokeApi.model.NamedApiResourceList

// Item-related endpoints
interface ItemClient {
    suspend fun getItemList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getItemAttributeList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getItemCategoryList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getItemFlingEffectList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getItemPocketList(offset: Int, limit: Int): NamedApiResourceList
    suspend fun getItem(id: Int): Item
    suspend fun getItemAttribute(id: Int): ItemAttribute
    suspend fun getItemCategory(id: Int): ItemCategory
    suspend fun getItemFlingEffect(id: Int): ItemFlingEffect
    suspend fun getItemPocket(id: Int): ItemPocket
}