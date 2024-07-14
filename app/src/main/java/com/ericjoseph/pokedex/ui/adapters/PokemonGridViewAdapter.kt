package com.ericjoseph.pokedex.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.ericjoseph.pokedex.R
import com.ericjoseph.pokedex.ui.models.PokemonViewItem

class PokemonGridViewAdapter(
    private val context: Context,
    private val pokemonList: MutableList<PokemonViewItem>
) : BaseAdapter() {
    override fun getCount(): Int {
        return pokemonList.size
    }

    override fun getItem(p0: Int): Any {
        return pokemonList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(pos: Int, view: View?, viewGroup: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = view
            ?: inflater.inflate(
                R.layout.pokemon_view_item,
                null
            )
        val ivPokemon = v.findViewById<ImageView>(R.id.iv_pokemon)
        val tvPokemonName = v.findViewById<TextView>(R.id.tv_pokemon_name)
        val tvPokemonCode = v.findViewById<TextView>(R.id.tv_pokemon_code)

        val pokemon = pokemonList[pos]

        ivPokemon?.setImageBitmap(pokemon.photoBitmap)
        tvPokemonName?.text = pokemon.pokemonName
        tvPokemonCode?.text = pokemon.pokemonId
        return v
    }

    fun updateList(newList: List<PokemonViewItem>) {
        pokemonList.clear()
        pokemonList.addAll(newList)
        notifyDataSetChanged()
    }
}