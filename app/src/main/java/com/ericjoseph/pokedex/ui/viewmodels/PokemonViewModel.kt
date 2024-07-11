package com.ericjoseph.pokedex.ui.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericjoseph.pokedex.entities.repository.PokemonRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private var pokemonRepository: PokemonRepositoryImpl
) : ViewModel() {
    private val currentOffset = MutableStateFlow(0)
    private val nextOffset = MutableStateFlow(0)
    private val previousOffset = MutableStateFlow(0)

    fun loadPokemonList() {
        viewModelScope.launch {
            currentOffset.collect { offset ->
                pokemonRepository.getPokemons(offset, 20)?.let { response ->
                    val nextPageUrl = Uri.parse(response.next)
                    val previousPageUrl =
                        response.previous.let { if (it is String) Uri.parse(it) else null }

                    val nextPageQuery = nextPageUrl.getQueryParameter("offset")
                    val previousPageQuery = previousPageUrl?.getQueryParameter("offset")

                    nextOffset.value = nextPageQuery?.toInt() ?: 0
                    previousOffset.value = previousPageQuery?.toInt() ?: 0
                }
            }
        }
    }

    fun nextPage() {
        viewModelScope.launch {
            currentOffset.emit(nextOffset.value)
        }
    }

    fun previousPage() {
        viewModelScope.launch {
            currentOffset.emit(previousOffset.value)
        }
    }
}