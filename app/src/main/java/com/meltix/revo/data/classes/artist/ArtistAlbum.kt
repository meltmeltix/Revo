package com.meltix.revo.data.classes.artist

import android.net.Uri

data class ArtistAlbum(
    var albumId: Long,
    var albumTitle: String,
    val albumCoverUri: Uri,
)