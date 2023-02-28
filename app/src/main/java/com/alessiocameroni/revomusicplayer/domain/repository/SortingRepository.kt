package com.alessiocameroni.revomusicplayer.domain.repository

import com.alessiocameroni.revomusicplayer.data.classes.SortData
import kotlinx.coroutines.flow.Flow

interface SortingRepository {
    suspend fun getSongSorting(): Flow<SortData>
    suspend fun getAlbumSorting(): Flow<SortData>
    suspend fun getArtistSorting(): Flow<SortData>

    suspend fun setSongSorting(sortData: SortData)
    suspend fun setAlbumSorting(sortData: SortData)
    suspend fun setArtistSorting(sortData: SortData)
}