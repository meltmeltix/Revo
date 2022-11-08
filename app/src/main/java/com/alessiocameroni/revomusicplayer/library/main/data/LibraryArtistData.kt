package com.alessiocameroni.revomusicplayer.library.main.data

import android.net.Uri

data class LibraryArtistData(
    var artistId: Long,
    var contentUri: Uri,
    var artistName: String,
    var tracksNumber: Int?,
    var albumsNumber: Int?
)