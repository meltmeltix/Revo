package com.alessiocameroni.revomusicplayer.domain.repository

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.alessiocameroni.revomusicplayer.data.classes.AlbumEntity
import kotlinx.coroutines.flow.Flow

interface AlbumsRepository {
    suspend fun fetchAlbumList(): Flow<SnapshotStateList<AlbumEntity>>
}