package com.ericjoseph.pokedex.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericjoseph.pokedex.datasources.remote.models.GetPokemonResponseModel
import com.ericjoseph.pokedex.datasources.repositories.PokemonRepository
import com.ericjoseph.pokedex.usecases.NetworkModuleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel(
    @Inject val pokemonRepository: PokemonRepository
): ViewModel() {

    fun getPokemonList() {
        viewModelScope.launch {
//            val pokemonApiService = dataModule.providePokemonService(retrofit)
//            pokemonApiService.getPokemonList(mapOf(
//                "offset" to "0",
//                "limit" to "20"
//            )).enqueue(object : Callback<ResponseBody>{
//                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                    response.body()?.string()?.let {content ->
//                        val responseModel = dataModule.gson().fromJson(content, GetPokemonResponseModel::class.java)
//                        Log.d("POKEMON_LIST", responseModel.toString())
//                    }
//                }
//
//                override fun onFailure(call: Call<ResponseBody>, failure: Throwable) {
//                    failure.printStackTrace()
//                }
//            })
        }
    }
}