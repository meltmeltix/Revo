package com.alessiocameroni.revomusicplayer.library.main.data

import android.net.Uri

data class LibraryAlbumData(
    var albumId: Long,
    var contentUri: Uri,
    var albumTitle: String,
    var artist: String,
    var tracksNumber: Long?,
    var duration: Int?
)