package com.alessiocameroni.revomusicplayer.data.classes

import android.net.Uri

data class AlbumDetails(
    val title: String,
    val artistId: Long,
    val artistName: String,
    val coverUri: Uri?,
)
