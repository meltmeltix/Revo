package com.alessiocameroni.revomusicplayer.domain.repository

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.alessiocameroni.revomusicplayer.data.classes.SongData
import kotlinx.coroutines.flow.Flow

interface SongsRepository {
    suspend fun fetchSongRepository(): Flow<SnapshotStateList<SongData>>
}