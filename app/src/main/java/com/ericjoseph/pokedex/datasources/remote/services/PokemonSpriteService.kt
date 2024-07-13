package com.ericjoseph.pokedex.datasources.remote.services

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

fun interface PokemonSpriteService {
    @GET("brilliant-diamond-shining-pearl/normal/1x/{name}.png")
    fun getPokemonSprite(@Path("name") name: String): Call<ResponseBody>
}