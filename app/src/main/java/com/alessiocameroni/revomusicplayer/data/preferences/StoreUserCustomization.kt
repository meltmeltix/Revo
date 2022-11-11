package com.alessiocameroni.revomusicplayer.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class StoreUserCustomization(private val context: Context) {
    private val Context.dataStore by preferencesDataStore(name = "customization_settings")

    companion object {
        val PLAYER_LAYOUT = intPreferencesKey("player_layout")
    }

    val getLayoutChoice: String = context.dataStore.data
        .map { preferences ->
            preferences[PLAYER_LAYOUT] ?: 0
        }.toString()

    suspend fun setLayoutChoice(intLayout: Int) {
        context.dataStore.edit { preferences ->
            preferences[PLAYER_LAYOUT] = intLayout
        }
    }
}