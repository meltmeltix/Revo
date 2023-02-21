package com.alessiocameroni.revomusicplayer.data.classes

import android.net.Uri

data class ArtistData(
    var artistId: Long,
    var artist: String,
    val albumCoverUri: Uri,
)