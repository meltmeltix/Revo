package com.meltix.revo.domain.repository

import com.meltix.revo.data.classes.artist.ArtistAlbum
import com.meltix.revo.data.classes.artist.ArtistDetails
import com.meltix.revo.data.classes.artist.ArtistSong

interface ArtistViewRepository {
    suspend fun getArtistDetails(artistId: Long): ArtistDetails
    suspend fun getAlbumList(artistId: Long): List<ArtistAlbum>
    suspend fun getSongList(artistId: Long): List<ArtistSong>
}