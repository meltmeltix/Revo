package com.alessiocameroni.revomusicplayer.domain.repository

import com.alessiocameroni.revomusicplayer.data.classes.preferences.NewSortingValues
import com.alessiocameroni.revomusicplayer.data.classes.preferences.SortingValues
import kotlinx.coroutines.flow.Flow

interface SortingRepository {
    /**
     * Get data
     */
    fun getSongSorting(): Flow<NewSortingValues>

    suspend fun getAlbumSorting(): Flow<SortingValues>
    suspend fun getAlbumSongsSorting(): Flow<SortingValues>

    suspend fun getArtistSorting(): Flow<SortingValues>
    suspend fun getArtistSongsSorting(): Flow<SortingValues>

    /**
     * Set data
     */
    suspend fun setSongSorting(sortingValues: NewSortingValues)

    suspend fun setAlbumSorting(sortingValues: SortingValues)
    suspend fun setAlbumSongsSorting(sortingValues: SortingValues)

    suspend fun setArtistSorting(sortingValues: SortingValues)
    suspend fun setArtistSongsSorting(sortingValues: SortingValues)
}