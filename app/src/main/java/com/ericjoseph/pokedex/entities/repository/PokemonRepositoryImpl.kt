package com.ericjoseph.pokedex.entities.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.ericjoseph.pokedex.datasources.dtos.Pokemon
import com.ericjoseph.pokedex.datasources.dtos.PokemonListResponse
import com.ericjoseph.pokedex.datasources.repository.PokemonRepository
import com.ericjoseph.pokedex.di.AppModule.providePokemonApiService
import com.ericjoseph.pokedex.di.AppModule.providePokemonRetrofitClient
import com.ericjoseph.pokedex.di.AppModule.providePokemonSpriteApiService
import com.ericjoseph.pokedex.di.AppModule.providePokemonSpriteRetrofitClient
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject


class PokemonRepositoryImpl @Inject constructor() : PokemonRepository {
    private val gson = Gson()
    private val pokemonApiServiceRetrofit = providePokemonRetrofitClient()
    private val pokemonApiService = providePokemonApiService(pokemonApiServiceRetrofit)

    private val pokemonSpriteRetrofit = providePokemonSpriteRetrofitClient()
    private val pokemonSpriteService = providePokemonSpriteApiService(pokemonSpriteRetrofit)

    private val ioCoroutineScope = CoroutineScope(Dispatchers.IO)
    override suspend fun getPokemons(
        offset: Int,
        limit: Int
    ): PokemonListResponse? {
        val response = ioCoroutineScope.async {
            runCatching {
                pokemonApiService.getPokemons(
                    mapOf(
                        "offset" to offset.toString(),
                        "limit" to limit.toString()
                    )
                ).execute().body()?.use { body ->
                    val content = body.string()
                    gson.fromJson(content, PokemonListResponse::class.java)
                }
            }
        }.await()

        response.onFailure { it.printStackTrace() }

        return response.getOrNull()
    }

    override suspend fun getPokemon(name: String): Pokemon? {
        val response = ioCoroutineScope.async {
            runCatching {
                pokemonApiService.getPokemon(name).execute().body()?.use { body ->
                    val content = body.string()
                    val mapJson = gson.fromJson(content, Map::class.java)
                    Pokemon(
                        name = mapJson["name"] as String,
                        id = mapJson["id"] as Int,
                    )
                }
            }
        }.await()

        response.onFailure { it.printStackTrace() }

        return response.getOrNull()
    }

    override suspend fun getPokemonSprite(name: String): Bitmap? {
        val response = ioCoroutineScope.async {
            runCatching {
                pokemonSpriteService.getPokemonSprite(name).execute().body()?.use { body ->
                    val content = body.bytes()
                    BitmapFactory.decodeByteArray(content, 0, content.size)
                }
            }
        }.await()

        response.onFailure { it.printStackTrace() }

        return response.getOrNull()
    }


}