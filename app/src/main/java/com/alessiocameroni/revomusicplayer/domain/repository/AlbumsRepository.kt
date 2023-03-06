package com.alessiocameroni.revomusicplayer.domain.repository

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.alessiocameroni.revomusicplayer.data.classes.AlbumData
import kotlinx.coroutines.flow.Flow

interface AlbumsRepository {
    suspend fun fetchAlbumsRepository(): Flow<SnapshotStateList<AlbumData>>
}