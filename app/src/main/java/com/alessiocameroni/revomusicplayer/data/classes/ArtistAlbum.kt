package com.alessiocameroni.revomusicplayer.data.classes

import android.net.Uri

data class ArtistAlbum(
    var albumId: Long,
    var albumTitle: String,
    val albumCoverUri: Uri,
)