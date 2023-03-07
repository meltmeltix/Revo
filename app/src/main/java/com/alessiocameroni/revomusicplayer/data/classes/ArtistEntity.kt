package com.alessiocameroni.revomusicplayer.data.classes

import android.net.Uri

data class ArtistEntity(
    var artistId: Long,
    var artist: String,
    val albumCoverUri: Uri,
)