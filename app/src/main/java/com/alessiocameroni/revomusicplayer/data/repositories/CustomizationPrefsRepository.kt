package com.alessiocameroni.revomusicplayer.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CustomizationPrefsRepository(val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "customization_settings"
    )

    companion object {
        private val APP_THEME = intPreferencesKey("app_theme")
        private val COLOR_SCHEME = intPreferencesKey("color_scheme")
        private val PLAYER_LAYOUT = intPreferencesKey("player_layout")
    }

    val getPlayerLayout: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[PLAYER_LAYOUT] ?: 1
        }

    suspend fun setPlayerLayout(intLayout: Int) {
        context.dataStore.edit { preferences ->
            preferences[PLAYER_LAYOUT] = intLayout
        }
    }
}