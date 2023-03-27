package com.alessiocameroni.revomusicplayer.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getSpotifyEnabledState(): Flow<Boolean>
    fun getPlayerLayout(): Flow<Int>
    fun getAlbumViewLayout(): Flow<Int>

    suspend fun setSpotifyEnabledState(enabledState: Boolean)
    suspend fun setPlayerLayout(playerLayoutValue: Int)
    suspend fun setAlbumViewLayout(albumViewLayoutValue: Int)
}