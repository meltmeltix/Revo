package com.alessiocameroni.revomusicplayer.domain.repository

import com.alessiocameroni.revomusicplayer.data.classes.Album

interface AlbumsRepository {
    suspend fun fetchAlbumList(): List<Album>
}