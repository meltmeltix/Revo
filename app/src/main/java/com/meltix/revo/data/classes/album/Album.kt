package com.meltix.revo.data.classes.album

import android.net.Uri

data class Album(
    var albumId: Long,
    var albumTitle: String,
    var artistId: Long,
    var artist: String,
    val albumCoverUri: Uri,
    val year: Long,
    var numberOfSongs: Long,
)