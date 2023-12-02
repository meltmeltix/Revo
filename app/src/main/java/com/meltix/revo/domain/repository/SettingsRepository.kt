package com.meltix.revo.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getDestinationsOrder(): Flow<Set<String>>
    fun getFabPosition(): Flow<Int>
    fun getFabAction(): Flow<Int>
    fun getPlayerLayout(): Flow<Int>
    fun getAlbumViewLayout(): Flow<Int>

    suspend fun setDestinationsOrder(destinationsOrder: Set<String>)
    suspend fun setFabPosition(fabPosition: Int)
    suspend fun setFabAction(fabAction: Int)
    suspend fun setPlayerLayout(playerLayoutValue: Int)
    suspend fun setAlbumViewLayout(albumViewLayoutValue: Int)
}