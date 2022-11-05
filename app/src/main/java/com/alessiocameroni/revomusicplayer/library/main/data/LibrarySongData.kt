package com.alessiocameroni.revomusicplayer.library.main.data

data class LibrarySongData (
    var songId: Long,
    var songTitle: String,
    var artist: String,
    var albumId: Long,
    var albumTitle: String,
    var duration: String
)