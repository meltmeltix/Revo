package com.alessiocameroni.revomusicplayer.data.classes

import android.net.Uri

data class ArtistData(
    var artistId: Long,
    var contentUri: Uri,
    var artistName: String,
    var tracksNumber: Int?,
    var albumsNumber: Int?
)