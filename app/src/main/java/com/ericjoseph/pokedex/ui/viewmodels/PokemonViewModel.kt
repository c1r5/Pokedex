package com.ericjoseph.pokedex.ui.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericjoseph.pokedex.datasources.dtos.PokemonListResponse
import com.ericjoseph.pokedex.datasources.repository.PokemonRepository
import com.ericjoseph.pokedex.ui.models.PokemonRecyclerViewItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private var pokemonRepository: PokemonRepository
) : ViewModel() {
    private val currentOffset = MutableStateFlow(0)
    private val nextOffset = MutableStateFlow(0)
    private val previousOffset = MutableStateFlow(0)
    private val defaultLimit = 20
    private val _pokemonList = MutableStateFlow<List<PokemonRecyclerViewItem>>(emptyList())

    val pokemonList = _pokemonList.asStateFlow()

    fun loadPokemonList() {
        viewModelScope.launch {
            currentOffset.collect { offset ->
                pokemonRepository.getPokemons(offset, defaultLimit)?.let {
                    setPages(it)
                    it.results
                }?.mapNotNull { pokemon ->
                    val name = pokemon.name
                    val id = pokemon.url?.split("/")?.last { it.isNotEmpty() }

                    if (name != null && id != null) {
                        pokemonRepository.getPokemonSprite(name)?.let {
                            PokemonRecyclerViewItem(
                                name = name,
                                photoBitmap = it,
                                code = id
                            )
                        }
                    } else {
                        null
                    }
                }?.let {
                    _pokemonList.emit(it)
                }
            }
        }
    }

    private fun setPages(pokemonListResponse: PokemonListResponse) {
        val nextPageUrl = Uri.parse(pokemonListResponse.next)
        val previousPageUrl =
            pokemonListResponse.previous.let { if (it is String) Uri.parse(it) else null }

        val nextPageQuery = nextPageUrl.getQueryParameter("offset")
        val previousPageQuery = previousPageUrl?.getQueryParameter("offset")

        nextOffset.value = nextPageQuery?.toInt() ?: 0
        previousOffset.value = previousPageQuery?.toInt() ?: 0
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