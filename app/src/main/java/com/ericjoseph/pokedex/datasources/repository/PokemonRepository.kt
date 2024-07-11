package com.ericjoseph.pokedex.datasources.repository

import com.ericjoseph.pokedex.datasources.remote.models.GetPokemonResponseModel

interface PokemonRepository {
    suspend fun getPokemons(offset: Int, limit: Int): GetPokemonResponseModel?
}