package com.ericjoseph.pokedex.datasources.repositories

import com.ericjoseph.pokedex.datasources.remote.DataSourceModule
import com.ericjoseph.pokedex.datasources.remote.models.GetPokemonResponseModel
import com.ericjoseph.pokedex.usecases.NetworkModuleUseCase
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
@Component(dependencies = arrayOf(PokemonRepository::class))
class PokemonRepository(
    @Inject val networkModule: NetworkModuleUseCase,
    @Inject val dataSourceModule: DataSourceModule
) {

    private val retrofit = networkModule.provideRetrofit()
    private val pokemonApiService = dataSourceModule.providePokemonApiService(retrofit)
    private val gson = DataSourceModule.provideGson()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    @Provides
    suspend fun getPokemons(
        offset: Int,
        limit: Int
    ) {
        val response = coroutineScope.launch {
            pokemonApiService.getPokemonList(
                mapOf(
                    "offset" to offset.toString(),
                    "limit" to limit.toString()
                )
            ).execute()
        }



    }
}