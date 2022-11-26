package com.alessiocameroni.revomusicplayer.library.artists.artistview.mainscreen.data.tabs

import androidx.compose.runtime.Composable

data class ArtistTabsItemData(
    val name: String,
    val screen: @Composable (() -> Unit)
)