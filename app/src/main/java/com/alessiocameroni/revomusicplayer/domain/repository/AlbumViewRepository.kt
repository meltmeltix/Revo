package com.alessiocameroni.revomusicplayer.domain.repository

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.alessiocameroni.revomusicplayer.data.classes.AlbumDetails
import com.alessiocameroni.revomusicplayer.data.classes.AlbumSongEntity
import kotlinx.coroutines.flow.Flow

interface AlbumViewRepository {
    suspend fun fetchSongList(albumId: Long): Flow<SnapshotStateList<AlbumSongEntity>>
    suspend fun fetchAlbumDetails(albumId: Long): Flow<AlbumDetails>
}