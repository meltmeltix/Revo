package com.alessiocameroni.revomusicplayer.data.repositories

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreLibrarySettings(private val context: Context) {
    companion object {
        private val Context.dataStore by preferencesDataStore(
            name = "library_settings"
        )

        val GRID_TYPE = intPreferencesKey("grid_type")
    }

    val getGridType: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[GRID_TYPE] ?: 1
        }

    suspend fun setGridType(intGridType: Int) {
        context.dataStore.edit { preferences ->
            preferences[GRID_TYPE] = intGridType
        }
    }
}