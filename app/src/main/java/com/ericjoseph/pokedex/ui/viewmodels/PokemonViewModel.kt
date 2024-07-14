package com.ericjoseph.pokedex.ui.viewmodels

import android.net.Uri
import android.widget.GridView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericjoseph.pokedex.datasources.dtos.PokemonListResponse
import com.ericjoseph.pokedex.datasources.repository.PokemonRepository
import com.ericjoseph.pokedex.ui.adapters.PokemonGridViewAdapter
import com.ericjoseph.pokedex.ui.models.PokemonViewItem
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
    private val defaultLimit = 15

    private val _pokemonList = MutableStateFlow<MutableList<PokemonViewItem>>(mutableListOf())
    private val _dataState = MutableStateFlow<DataState?>(null)

    val dataState = _dataState.asStateFlow()
    val pokemonList = _pokemonList.asStateFlow()

    init {
        refreshPokemonList()
    }

    fun refreshPokemonList(offset: Int = currentOffset.value) {
        viewModelScope.launch {
            _dataState.emit(DataState.Loading)
            val result = pokemonRepository.getPokemons(offset, defaultLimit)
            val pokemonList = result?.results?.mapNotNull { pokemon ->
                val name = pokemon.name ?: return@mapNotNull null
                val id = pokemon.url?.split("/")?.last { it.isNotEmpty() } ?: return@mapNotNull null
                val bitmap = pokemonRepository.getPokemonSprite(name) ?: return@mapNotNull null

                PokemonViewItem(
                    pokemonName = name,
                    photoBitmap = bitmap,
                    pokemonId = id.padStart(4, '0')
                )
            } ?: emptyList()
            if (pokemonList.isNotEmpty()) {
                _pokemonList.emit(pokemonList.toMutableList())
                _dataState.emit(DataState.Success)
            } else {
                _dataState.emit(DataState.Error)
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
        refreshPokemonList(nextOffset.value)
    }

    fun previousPage() {
        refreshPokemonList(previousOffset.value)
    }

    fun loadPokemonsIntoGridView(gridView: GridView) {
        gridView.adapter = PokemonGridViewAdapter(gridView.context, pokemonList.value)
        viewModelScope.launch {
            pokemonList.collect { pokemonList ->
                (gridView.adapter as PokemonGridViewAdapter).updateList(pokemonList)
            }
        }
    }

    fun setOnDataStateChangeListener(listener: (DataState) -> Unit) {
        viewModelScope.launch {
            dataState.collect { dataState ->
                dataState?.let { listener(it) }
            }
        }
    }
}

enum class DataState {
    Loading,
    Success,
    Error
}
