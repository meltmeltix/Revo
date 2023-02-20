package com.alessiocameroni.revomusicplayer.data.classes

data class ArtistData(
    var artistId: Long,
    var artist: String,
    var albumsNumber: String? = "0",
    var tracksNumber: String? = "0",
)