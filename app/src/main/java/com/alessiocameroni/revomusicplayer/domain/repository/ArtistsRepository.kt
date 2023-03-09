package com.alessiocameroni.revomusicplayer.domain.repository

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.alessiocameroni.revomusicplayer.data.classes.Artist
import kotlinx.coroutines.flow.Flow

interface ArtistsRepository {
    suspend fun fetchArtistList(): Flow<SnapshotStateList<Artist>>
}