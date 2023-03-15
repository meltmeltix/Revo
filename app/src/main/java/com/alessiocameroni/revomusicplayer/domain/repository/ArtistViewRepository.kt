package com.alessiocameroni.revomusicplayer.domain.repository

import com.alessiocameroni.revomusicplayer.data.classes.ArtistAlbum
import com.alessiocameroni.revomusicplayer.data.classes.ArtistDetails
import com.alessiocameroni.revomusicplayer.data.classes.ArtistSong

interface ArtistViewRepository {
    suspend fun getArtistDetails(artistId: Long): ArtistDetails
    suspend fun getAlbumList(artistId: Long): List<ArtistAlbum>
    suspend fun getSongList(artistId: Long): List<ArtistSong>
}