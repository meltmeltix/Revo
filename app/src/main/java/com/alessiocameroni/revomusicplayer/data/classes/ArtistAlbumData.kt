package com.alessiocameroni.revomusicplayer.data.classes

import android.net.Uri

data class ArtistAlbumData(
    var albumId: Long,
    var albumTitle: String,
    val albumCoverUri: Uri,
)