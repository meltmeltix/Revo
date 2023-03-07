package com.alessiocameroni.revomusicplayer.data.classes

import android.net.Uri

data class AlbumEntry(
    var albumId: Long,
    var albumTitle: String,
    var artistId: Long,
    var artist: String,
    val albumCoverUri: Uri,
    val year: Long,
    var numberOfSongs: Long,
)