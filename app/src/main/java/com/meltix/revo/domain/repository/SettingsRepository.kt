package com.meltix.revo.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getDestinationsOrder(): Flow<Set<String>>
    fun getSpotifyEnabledState(): Flow<Boolean>
    fun getPlayerLayout(): Flow<Int>
    fun getAlbumViewLayout(): Flow<Int>

    suspend fun setDestinationsOrder(destinationsOrder: Set<String>)
    suspend fun setSpotifyEnabledState(enabledState: Boolean)
    suspend fun setPlayerLayout(playerLayoutValue: Int)
    suspend fun setAlbumViewLayout(albumViewLayoutValue: Int)
}