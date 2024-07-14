package com.ericjoseph.pokedex.ui.models

import android.graphics.Bitmap

data class PokemonViewItem(
    val pokemonName: String,
    val photoBitmap: Bitmap,
    val pokemonId: String,
)