package com.alessiocameroni.revomusicplayer.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.alessiocameroni.revomusicplayer.domain.repository.CustomizationRepository
import kotlinx.coroutines.flow.map

private const val CUSTOMIZATION_PREFERENCES_NAME = "customization_preferences"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = CUSTOMIZATION_PREFERENCES_NAME
)

class CustomizationRepositoryImpl(
    private val context: Context
): CustomizationRepository {
    private companion object {
        val APP_THEME = intPreferencesKey("app_theme")
        val COLOR_SCHEME = intPreferencesKey("color_scheme")
        val PLAYER_LAYOUT = intPreferencesKey("player_layout")
    }

    override suspend fun getCustomizationData() {

    }

    override suspend fun getPlayerLayout() = context.dataStore.data
        .map { preferences ->
            preferences[PLAYER_LAYOUT] ?: 0
        }

    override suspend fun setPlayerLayout(playerLayoutValue: Int) {
        context.dataStore.edit { preferences ->
            preferences[PLAYER_LAYOUT] = playerLayoutValue
        }
    }

    /*

    val getPlayerLayout: Flow<Int> =

    suspend fun setPlayerLayout(intLayout: Int) {

    }*/
}