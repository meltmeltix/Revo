package com.alessiocameroni.revomusicplayer.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun getCustomizationData()

    suspend fun getPlayerLayout(): Flow<Int>

    suspend fun setPlayerLayout(playerLayoutValue: Int)
}