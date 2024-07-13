package com.ericjoseph.pokedex.datasources.repository

import android.graphics.Bitmap
import com.ericjoseph.pokedex.datasources.dtos.Pokemon
import com.ericjoseph.pokedex.datasources.dtos.PokemonListResponse

interface PokemonRepository {
    suspend fun getPokemons(offset: Int, limit: Int): PokemonListResponse?
    suspend fun getPokemon(name: String): Pokemon?
    suspend fun getPokemon(id: Int): Pokemon? {
        return null
    }

    suspend fun getPokemonSprite(name: String): Bitmap?
}