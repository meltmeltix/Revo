package com.alessiocameroni.revomusicplayer.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun getSpotifyEnabledState(): Flow<Boolean>
    suspend fun getPlayerLayout(): Flow<Int>

    suspend fun setSpotifyEnabledState(enabledState: Boolean)
    suspend fun setPlayerLayout(playerLayoutValue: Int)
}