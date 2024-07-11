package com.ericjoseph.pokedex.ui.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ericjoseph.pokedex.R

class AdapterPokemonList: RecyclerView.Adapter<AdapterPokemonList.PokemonViewHolder>() {

    open class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPokemon: ImageView = itemView.findViewById(R.id.iv_pokemon)
        val tvPokemonCode: TextView = itemView.findViewById(R.id.tv_pokemon_code)
        val tvPokemonName: TextView = itemView.findViewById(R.id.tv_pokemon_name)

//        fun bind(pokemon: Pokemon) {
//            ivPokemon.setImageResource(pokemon.image)
//            tvPokemonCode.text = pokemon.code
//            tvPokemonName.text = pokemon.name
//        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


}