package com.alessiocameroni.revomusicplayer.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.alessiocameroni.revomusicplayer.domain.repository.SortingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val SORTING_PREFERENCES_NAME = "sorting_preferences"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = SORTING_PREFERENCES_NAME
)

class SortingRepositoryImpl(
    private val context: Context
): SortingRepository {
    private companion object {
        /**
         * Song Screen keys
         */
        val SONG_SORTING_TYPE = intPreferencesKey("song_sorting_type")
        val SONG_SORTING_ORDER = intPreferencesKey("song_sorting_order")
    }

    /**
     * Get Data functions
     */
    override suspend fun getSongSortingType(): Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[SONG_SORTING_TYPE] ?: 0
        }

    override suspend fun getSongSortingOrder(): Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[SONG_SORTING_ORDER] ?: 0
        }

    /**
     * Set Data functions
     */
    override suspend fun setSongSortingType(sortingValue: Int) {
        context.dataStore.edit { preferences ->
            preferences[SONG_SORTING_TYPE] = sortingValue
        }
    }

    override suspend fun setSongSortingOrder(sortingValue: Int) {
        context.dataStore.edit { preferences ->
            preferences[SONG_SORTING_ORDER] = sortingValue
        }
    }
}