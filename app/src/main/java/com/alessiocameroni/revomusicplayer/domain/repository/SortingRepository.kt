package com.alessiocameroni.revomusicplayer.domain.repository

import kotlinx.coroutines.flow.Flow

interface SortingRepository {
    // Get data
    fun getSongSortType(): Flow<Int>
    fun getSongSortOrder(): Flow<Int>

    fun getAlbumSortType(): Flow<Int>
    fun getAlbumSortOrder(): Flow<Int>
    fun getAlbumSongsSortType(): Flow<Int>
    fun getAlbumSongsSortOrder(): Flow<Int>

    fun getArtistSortOrder(): Flow<Int>
    fun getArtistSongsSortType(): Flow<Int>
    fun getArtistSongsSortOrder(): Flow<Int>

    // Set data
    suspend fun setSongSortType(type: Int)
    suspend fun setSongSortOrder(order: Int)

    suspend fun setAlbumSortType(type: Int)
    suspend fun setAlbumSortOrder(order: Int)
    suspend fun setAlbumSongsSortType(type: Int)
    suspend fun setAlbumSongsSortOrder(order: Int)

    suspend fun setArtistOrder(order: Int)
    suspend fun setArtistSongsSortType(type: Int)
    suspend fun setArtistSongsSortOrder(order: Int)
}