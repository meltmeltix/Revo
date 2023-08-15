package com.meltix.revo.data.classes.artist

import android.net.Uri

data class Artist(
    var artistId: Long,
    var artist: String,
    val albumCoverUri: Uri,
)