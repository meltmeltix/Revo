package com.alessiocameroni.revomusicplayer.domain.repository

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.alessiocameroni.revomusicplayer.data.classes.Song
import kotlinx.coroutines.flow.Flow

interface SongsRepository {
    suspend fun fetchSongList(): Flow<SnapshotStateList<Song>>
}