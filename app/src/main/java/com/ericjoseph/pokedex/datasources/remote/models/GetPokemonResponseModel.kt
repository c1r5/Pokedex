package com.ericjoseph.pokedex.datasources.remote.models

data class GetPokemonResponseModel(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Pokemons>
)

data class Pokemons(
    val name: String,
    val url: String
)