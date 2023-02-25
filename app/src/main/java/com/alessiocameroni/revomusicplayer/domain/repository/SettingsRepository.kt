package com.alessiocameroni.revomusicplayer.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun getSettingsData()

    suspend fun getSpotifyEnabledState(): Flow<Boolean>

    suspend fun getPlayerLayout(): Flow<Int>

    suspend fun setPlayerLayout(playerLayoutValue: Int)
}