package com.alessiocameroni.revomusicplayer.domain.repository

import com.alessiocameroni.revomusicplayer.data.classes.album.Album

interface AlbumsRepository {
    suspend fun getAlbumList(): List<Album>
}