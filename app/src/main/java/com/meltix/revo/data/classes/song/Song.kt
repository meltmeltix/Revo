package com.meltix.revo.data.classes.song

import android.net.Uri

data class Song(
    var songId: Long,
    var contentUri: Uri,
    var songTitle: String,
    var artistId: Long,
    var artist: String,
    var albumId: Long,
    val album: String,
    val albumCoverUri: Uri,
    var duration: Int,
    val dateAdded: Long,
)