package com.ericjoseph.pokedex.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ericjoseph.pokedex.R
import com.ericjoseph.pokedex.databinding.ActivityMainBinding
import com.ericjoseph.pokedex.ui.viewmodels.DataState
import com.ericjoseph.pokedex.ui.viewmodels.PokemonViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val activityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val searchView by lazy { activityMainBinding.searchView }
    private val pokemonViewModel by viewModels<PokemonViewModel>()
    private val pokemonGridView by lazy { activityMainBinding.pokemonGridview.pokemonGridview }
    private val swipeRefreshLayout by lazy { activityMainBinding.pokemonGridview.swiperefreshPokemonConteiner }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)
        setSupportActionBar(findViewById(R.id.toolbar))

        pokemonViewModel.loadPokemonsIntoGridView(pokemonGridView)

        swipeRefreshLayout.setOnRefreshListener {
            pokemonViewModel.refreshPokemonList()
        }

        pokemonViewModel.setOnDataStateChangeListener {
            when (it) {
                DataState.Loading -> swipeRefreshLayout.isRefreshing = true
                DataState.Success -> swipeRefreshLayout.isRefreshing = false
                DataState.Error -> {
                    swipeRefreshLayout.isRefreshing = false
                }
            }
        }
    }
}