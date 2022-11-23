package com.alessiocameroni.revomusicplayer.data.classes

import android.net.Uri

data class AlbumData(
    var albumId: Long,
    var contentUri: Uri,
    var albumTitle: String,
    var artist: String,
    val albumCoverUri: Uri,
    var tracksNumber: Long?,
    var duration: Int?,
)