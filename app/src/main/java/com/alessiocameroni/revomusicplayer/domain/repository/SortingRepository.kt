package com.alessiocameroni.revomusicplayer.domain.repository

import com.alessiocameroni.revomusicplayer.data.classes.SortingValues
import kotlinx.coroutines.flow.Flow

interface SortingRepository {
    /**
     * Get data
     */
    suspend fun getSongSorting(): Flow<SortingValues>

    suspend fun getAlbumSorting(): Flow<SortingValues>
    suspend fun getAlbumSongsSorting(): Flow<SortingValues>

    suspend fun getArtistSorting(): Flow<SortingValues>
    suspend fun getArtistSongsSorting(): Flow<SortingValues>

    /**
     * Set data
     */
    suspend fun setSongSorting(sortingValues: SortingValues)

    suspend fun setAlbumSorting(sortingValues: SortingValues)
    suspend fun setAlbumSongsSorting(sortingValues: SortingValues)

    suspend fun setArtistSorting(sortingValues: SortingValues)
    suspend fun setArtistSongsSorting(sortingValues: SortingValues)
}