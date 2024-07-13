package com.ericjoseph.pokedex.ui.models

import android.graphics.Bitmap

data class PokemonRecyclerViewItem(
    val name: String,
    val photoBitmap: Bitmap,
    val code: String,
)