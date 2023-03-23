package com.alessiocameroni.revomusicplayer.domain.repository

import com.alessiocameroni.revomusicplayer.data.classes.preferences.SortingValues
import kotlinx.coroutines.flow.Flow

interface SortingRepository {
    // Get data
    fun getSongSortType(): Flow<Int>
    fun getSongSortOrder(): Flow<Int>

    suspend fun getAlbumSorting(): Flow<SortingValues>
    suspend fun getAlbumSongsSorting(): Flow<SortingValues>

    suspend fun getArtistSorting(): Flow<SortingValues>
    suspend fun getArtistSongsSorting(): Flow<SortingValues>

    // Set data
    suspend fun setSongSortType(type: Int)
    suspend fun setSongSortOrder(order: Int)

    suspend fun setAlbumSorting(oldSortingValues: SortingValues)
    suspend fun setAlbumSongsSorting(oldSortingValues: SortingValues)

    suspend fun setArtistSorting(oldSortingValues: SortingValues)
    suspend fun setArtistSongsSorting(oldSortingValues: SortingValues)
}