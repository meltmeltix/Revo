package com.alessiocameroni.revomusicplayer.domain.repository

import kotlinx.coroutines.flow.Flow

interface SortingRepository {
    suspend fun getSongSortingType(): Flow<Int>
    suspend fun getSongSortingOrder(): Flow<Int>
    suspend fun getAlbumSortingType(): Flow<Int>
    suspend fun getAlbumSortingOrder(): Flow<Int>

    suspend fun setSongSortingType(sortingValue: Int)
    suspend fun setSongSortingOrder(sortingValue: Int)
    suspend fun setAlbumSortingType(sortingValue: Int)
    suspend fun setAlbumSortingOrder(sortingValue: Int)
}