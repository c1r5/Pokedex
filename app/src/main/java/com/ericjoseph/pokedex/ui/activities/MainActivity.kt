package com.ericjoseph.pokedex.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import com.ericjoseph.pokedex.R
import com.ericjoseph.pokedex.databinding.ActivityMainBinding
import com.ericjoseph.pokedex.ui.viewmodels.PokemonViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val activityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val searchView by lazy { activityMainBinding.searchView }
    private val recyclerView by lazy { activityMainBinding.recyclerView }
    private val pokemonViewModel by viewModels<PokemonViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)
        setSupportActionBar(findViewById(R.id.toolbar))
        pokemonViewModel.loadPokemonList()
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               return true
            }
        })
    }
}