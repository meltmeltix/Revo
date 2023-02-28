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
        val SONG_SORTING_TYPE = intPreferencesKey("song_sorting_type")
        val SONG_SORTING_ORDER = intPreferencesKey("song_sorting_order")
        val ALBUM_SORTING_TYPE = intPreferencesKey("album_sorting_type")
        val ALBUM_SORTING_ORDER = intPreferencesKey("album_sorting_order")
        val ARTIST_SORTING_TYPE = intPreferencesKey("artist_sorting_type")
        val ARTIST_SORTING_ORDER = intPreferencesKey("artist_sorting_order")
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

    override suspend fun getAlbumSortingType(): Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[ALBUM_SORTING_TYPE] ?: 0
        }

    override suspend fun getAlbumSortingOrder(): Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[ALBUM_SORTING_ORDER] ?: 0
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

    override suspend fun setAlbumSortingType(sortingValue: Int) {
        context.dataStore.edit { preferences ->
            preferences[ALBUM_SORTING_TYPE] = sortingValue
        }
    }

    override suspend fun setAlbumSortingOrder(sortingValue: Int) {
        context.dataStore.edit { preferences ->
            preferences[ALBUM_SORTING_ORDER] = sortingValue
        }
    }
}