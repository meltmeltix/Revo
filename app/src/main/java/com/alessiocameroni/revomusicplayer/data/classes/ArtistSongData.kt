package com.alessiocameroni.revomusicplayer.data.classes

import android.net.Uri

data class ArtistSongData(
    var songId: Long,
    var contentUri: Uri,
    var track: String? = "-",
    var songTitle: String,
    var duration: Int,
    val fixedDuration: String? = "00:00",
    val albumId: Long,
    val album: String,
)