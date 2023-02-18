package com.alessiocameroni.revomusicplayer.data.classes

import android.net.Uri

data class ArtistData(
    var artistId: Long,
    var artist: String,
    var albumsNumber: String? = "0",
    var tracksNumber: String? = "0",
    val artistPictureUri: Uri?,
)