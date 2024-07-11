package com.ericjoseph.pokedex.datasources

import com.ericjoseph.pokedex.datasources.remote.services.PokemonApiService
import com.ericjoseph.pokedex.datasources.repository.PokemonRepository
import com.ericjoseph.pokedex.entities.repository.PokemonRepositoryImpl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    fun providePokemonApiService(retrofit: Retrofit): PokemonApiService = retrofit.create(PokemonApiService::class.java)

    @Provides
    fun provideGson() = Gson()

    @Provides
    fun providePokemonRepository(): PokemonRepository = PokemonRepositoryImpl()
}