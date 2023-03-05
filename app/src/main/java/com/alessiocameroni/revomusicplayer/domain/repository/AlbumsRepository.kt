package com.alessiocameroni.revomusicplayer.domain.repository

import com.alessiocameroni.revomusicplayer.data.classes.AlbumData
import kotlinx.coroutines.flow.Flow

interface AlbumsRepository {
    suspend fun fetchAlbumsRepository(): Flow<List<AlbumData>>
}