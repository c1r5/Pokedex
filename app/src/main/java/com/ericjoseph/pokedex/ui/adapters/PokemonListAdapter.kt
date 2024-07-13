package com.ericjoseph.pokedex.ui.adapters

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ericjoseph.pokedex.R
import com.ericjoseph.pokedex.ui.models.PokemonRecyclerViewItem

class PokemonListAdapter(
    private val pokemonItem: MutableList<PokemonRecyclerViewItem>
) : RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>() {

    open class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPokemon: ImageView = itemView.findViewById(R.id.iv_pokemon)
        val tvPokemonCode: TextView = itemView.findViewById(R.id.tv_pokemon_code)
        val tvPokemonName: TextView = itemView.findViewById(R.id.tv_pokemon_name)

        fun bind(item: PokemonRecyclerViewItem) {
            tvPokemonCode.text = item.code
            tvPokemonName.text = item.name
            ivPokemon.setImageBitmap(item.photoBitmap)
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonViewHolder {
        //TODO("Not yet implemented")
        val view = View.inflate(parent.context, R.layout.recycler_view_item, null)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(pokemonItem[position])
    }

    override fun getItemCount(): Int {
        return pokemonItem.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<PokemonRecyclerViewItem>) {
        pokemonItem.clear()
        pokemonItem.addAll(newList)
        notifyDataSetChanged()
    }

    fun addItem(item: PokemonRecyclerViewItem) {
        pokemonItem.add(item)
        notifyItemInserted(pokemonItem.size - 1)
    }

}