package com.alessiocameroni.revomusicplayer.domain.repository

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.alessiocameroni.revomusicplayer.data.classes.SongEntity
import kotlinx.coroutines.flow.Flow

interface SongsRepository {
    suspend fun fetchSongList(): Flow<SnapshotStateList<SongEntity>>
}