package com.alessiocameroni.revomusicplayer.domain.repository

import com.alessiocameroni.revomusicplayer.data.classes.SongData
import kotlinx.coroutines.flow.Flow

interface SongsRepository {
    suspend fun fetchSongRepository(): Flow<List<SongData>>
}