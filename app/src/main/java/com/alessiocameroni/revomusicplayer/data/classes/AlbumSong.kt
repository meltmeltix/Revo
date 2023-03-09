package com.alessiocameroni.revomusicplayer.data.classes

import android.net.Uri

data class AlbumSong(
    var songId: Long,
    var contentUri: Uri,
    var track: Int,
    var songTitle: String,
    var duration: Int,
    val fixedDuration: String? = "00:00",
)