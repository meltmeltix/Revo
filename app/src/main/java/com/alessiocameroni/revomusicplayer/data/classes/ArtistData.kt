package com.alessiocameroni.revomusicplayer.data.classes

data class ArtistData(
    var artistId: Long,
    var artist: String,
    var tracksNumber: Int?,
    var albumsNumber: Int?
)