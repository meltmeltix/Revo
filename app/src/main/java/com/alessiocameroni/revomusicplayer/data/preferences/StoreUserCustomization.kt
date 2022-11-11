package com.alessiocameroni.revomusicplayer.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreUserCustomization(private val context: Context) {
    companion object {
        private val Context.dataStore by preferencesDataStore(
            name = "customization_settings"
        )

        val PLAYER_LAYOUT = stringPreferencesKey("player_layout")
    }

    val getLayoutChoice: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[PLAYER_LAYOUT] ?: ""
        }

    /*suspend fun setLayoutChoice(intLayout: Int) {
        context.dataStore.edit { preferences ->
            preferences[PLAYER_LAYOUT] = intLayout
        }
    }*/
}