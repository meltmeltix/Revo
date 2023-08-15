package com.meltix.revo.domain.repository

import com.meltix.revo.data.classes.album.AlbumDetails
import com.meltix.revo.data.classes.album.AlbumSong

interface AlbumViewRepository {
    suspend fun getAlbumDetails(albumId: Long): AlbumDetails
    suspend fun getSongList(albumId: Long): List<AlbumSong>
}