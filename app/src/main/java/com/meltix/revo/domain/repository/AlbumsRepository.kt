package com.meltix.revo.domain.repository

import com.meltix.revo.data.classes.album.Album

interface AlbumsRepository {
    suspend fun getAlbumList(): List<Album>
}