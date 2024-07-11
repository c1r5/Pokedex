package com.ericjoseph.pokedex.entities.repository

import com.ericjoseph.pokedex.datasources.DataSourceModule.provideGson
import com.ericjoseph.pokedex.datasources.DataSourceModule.providePokemonApiService
import com.ericjoseph.pokedex.datasources.remote.models.GetPokemonResponseModel
import com.ericjoseph.pokedex.datasources.repository.PokemonRepository
import com.ericjoseph.pokedex.di.AppModule.provideRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject


class PokemonRepositoryImpl @Inject constructor() : PokemonRepository {
    private val gson = provideGson()
    private val retrofit = provideRetrofit()
    private val pokemonApiService = providePokemonApiService(retrofit)
    private val ioCoroutineScope = CoroutineScope(Dispatchers.IO)
    override suspend fun getPokemons(
        offset: Int,
        limit: Int
    ): GetPokemonResponseModel? {
        val response = ioCoroutineScope.async {
            runCatching {
                pokemonApiService.getPokemons(
                    mapOf(
                        "offset" to offset.toString(),
                        "limit" to limit.toString()
                    )
                ).execute().body()?.use { body ->
                    val content = body.string()
                    gson.fromJson(content, GetPokemonResponseModel::class.java)
                }
            }
        }.await()

        response.onFailure { it.printStackTrace() }

        return response.getOrNull()
    }
}