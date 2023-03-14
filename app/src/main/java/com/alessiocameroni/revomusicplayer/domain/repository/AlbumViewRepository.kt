package com.alessiocameroni.revomusicplayer.domain.repository

import com.alessiocameroni.revomusicplayer.data.classes.AlbumDetails
import com.alessiocameroni.revomusicplayer.data.classes.AlbumSong

interface AlbumViewRepository {
    suspend fun getAlbumDetails(albumId: Long): AlbumDetails
    suspend fun getSongList(albumId: Long): List<AlbumSong>
}