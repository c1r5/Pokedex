package com.ericjoseph.pokedex.datasources.remote.services


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface PokemonApiService {
    @GET("pokemon")
    fun getPokemons(@QueryMap queryParams: Map<String, String>): Call<ResponseBody>

    @GET("pokemon/{id}")
    fun getPokemon(@Path("id") id: Int): Call<ResponseBody>

    @GET("pokemon/{name}")
    fun getPokemon(@Path("name") name: String): Call<ResponseBody>
}