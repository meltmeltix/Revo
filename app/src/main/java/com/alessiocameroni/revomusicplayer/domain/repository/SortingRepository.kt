package com.alessiocameroni.revomusicplayer.domain.repository

import com.alessiocameroni.revomusicplayer.data.classes.preferences.SortingValues
import kotlinx.coroutines.flow.Flow

interface SortingRepository {
    // Get data
    fun getSongSortType(): Flow<Int>
    fun getSongSortOrder(): Flow<Int>

    fun getAlbumSortType(): Flow<Int>
    fun getAlbumSortOrder(): Flow<Int>
    fun getAlbumSongsSortType(): Flow<Int>
    fun getAlbumSongsSortOrder(): Flow<Int>

    suspend fun getArtistSorting(): Flow<SortingValues>
    suspend fun getArtistSongsSorting(): Flow<SortingValues>

    // Set data
    suspend fun setSongSortType(type: Int)
    suspend fun setSongSortOrder(order: Int)

    suspend fun setAlbumSortType(type: Int)
    suspend fun setAlbumSortOrder(order: Int)
    suspend fun setAlbumSongsSortType(type: Int)
    suspend fun setAlbumSongsSortOrder(order: Int)

    suspend fun setArtistSorting(oldSortingValues: SortingValues)
    suspend fun setArtistSongsSorting(oldSortingValues: SortingValues)
}