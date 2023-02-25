package com.alessiocameroni.revomusicplayer.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.alessiocameroni.revomusicplayer.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.map

private const val CUSTOMIZATION_PREFERENCES_NAME = "customization_preferences"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = CUSTOMIZATION_PREFERENCES_NAME
)

class SettingsRepositoryImpl(
    private val context: Context
): SettingsRepository {
    private companion object {
        /**
         * Library Keys
         */
        val SPOTIFY_ENABLED = booleanPreferencesKey("spotify_enabled")

        /**
         * Customization Keys
         */
        val APP_THEME = intPreferencesKey("app_theme")
        val COLOR_SCHEME = intPreferencesKey("color_scheme")
        val PLAYER_LAYOUT = intPreferencesKey("player_layout")
    }

    /**
     * Get Data functions
     */
    override suspend fun getSpotifyEnabledState() = context.dataStore.data
        .map { preferences ->
            preferences[SPOTIFY_ENABLED] ?: true
        }

    override suspend fun getPlayerLayout() = context.dataStore.data
        .map { preferences ->
            preferences[PLAYER_LAYOUT] ?: 1
        }


    /**
     * Set Data functions
     */
    override suspend fun setSpotifyEnabledState(enabledState: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[SPOTIFY_ENABLED] = enabledState
        }
    }

    override suspend fun setPlayerLayout(playerLayoutValue: Int) {
        context.dataStore.edit { preferences ->
            preferences[PLAYER_LAYOUT] = playerLayoutValue
        }
    }
}