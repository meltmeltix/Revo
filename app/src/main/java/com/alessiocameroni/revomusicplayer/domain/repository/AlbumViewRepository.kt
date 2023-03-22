package com.alessiocameroni.revomusicplayer.domain.repository

import com.alessiocameroni.revomusicplayer.data.classes.album.AlbumDetails
import com.alessiocameroni.revomusicplayer.data.classes.album.AlbumSong

interface AlbumViewRepository {
    suspend fun getAlbumDetails(albumId: Long): AlbumDetails
    suspend fun getSongList(albumId: Long): List<AlbumSong>
}