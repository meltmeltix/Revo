package com.alessiocameroni.revomusicplayer.library.songs.data

import android.net.Uri

data class LibrarySongData(
    var songId: Long,
    var contentUri: Uri,
    var songTitle: String,
    var artist: String,
    var albumId: Long,
    var duration: Int
)