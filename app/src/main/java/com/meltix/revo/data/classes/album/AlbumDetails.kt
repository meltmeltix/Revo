package com.meltix.revo.data.classes.album

import android.net.Uri

data class AlbumDetails(
    val title: String,
    val artistId: Long,
    val artistName: String,
    val coverUri: Uri?
)
