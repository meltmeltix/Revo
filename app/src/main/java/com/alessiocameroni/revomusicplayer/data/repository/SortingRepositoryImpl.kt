package com.alessiocameroni.revomusicplayer.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.alessiocameroni.revomusicplayer.data.classes.SortData
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
        val ALBUM_SONGS_SORTING_TYPE = intPreferencesKey("album_songs_sorting_type")
        val ALBUM_SONGS_SORTING_ORDER = intPreferencesKey("album_songs_sorting_order")

        val ARTIST_SORTING_ORDER = intPreferencesKey("artist_sorting_order")
        val ARTIST_SONGS_SORTING_TYPE = intPreferencesKey("artist_songs_sorting_type")
        val ARTIST_SONGS_SORTING_ORDER = intPreferencesKey("artist_songs_sorting_order")
    }

    /**
     * Get Data functions
     */
    override suspend fun getSongSorting(): Flow<SortData> = context.dataStore.data
        .map { preferences ->
            SortData(
                type = preferences[SONG_SORTING_TYPE] ?: 0,
                order = preferences[SONG_SORTING_ORDER] ?: 0
            )
        }

    override suspend fun getAlbumSorting(): Flow<SortData> = context.dataStore.data
        .map { preferences ->
            SortData(
                type = preferences[ALBUM_SORTING_TYPE] ?: 0,
                order = preferences[ALBUM_SORTING_ORDER] ?: 0
            )
        }

    override suspend fun getAlbumSongsSorting(): Flow<SortData> = context.dataStore.data
        .map { preferences ->
            SortData(
                type = preferences[ALBUM_SONGS_SORTING_TYPE] ?: 0,
                order = preferences[ALBUM_SONGS_SORTING_ORDER] ?: 0
            )
        }

    override suspend fun getArtistSorting(): Flow<SortData> = context.dataStore.data
        .map { preferences ->
            SortData(
                type = 0,
                order = preferences[ARTIST_SORTING_ORDER] ?: 0
            )
        }

    override suspend fun getArtistSongsSorting(): Flow<SortData> = context.dataStore.data
        .map { preferences ->
            SortData(
                type = preferences[ARTIST_SONGS_SORTING_TYPE] ?: 0,
                order = preferences[ARTIST_SONGS_SORTING_ORDER] ?: 0
            )
        }

    /**
     * Set Data functions
     */
    override suspend fun setSongSorting(sortData: SortData) {
        context.dataStore.edit { preferences ->
            preferences[SONG_SORTING_TYPE] = sortData.type
            preferences[SONG_SORTING_ORDER] = sortData.order
        }
    }

    override suspend fun setAlbumSorting(sortData: SortData) {
        context.dataStore.edit { preferences ->
            preferences[ALBUM_SORTING_TYPE] = sortData.type
            preferences[ALBUM_SORTING_ORDER] = sortData.order
        }
    }

    override suspend fun setAlbumSongsSorting(sortData: SortData) {
        context.dataStore.edit { preferences ->
            preferences[ALBUM_SONGS_SORTING_TYPE] = sortData.type
            preferences[ALBUM_SONGS_SORTING_ORDER] = sortData.order
        }
    }

    override suspend fun setArtistSorting(sortData: SortData) {
        context.dataStore.edit { preferences ->
            preferences[ARTIST_SORTING_ORDER] = sortData.order
        }
    }

    override suspend fun setArtistSongsSorting(sortData: SortData) {
        context.dataStore.edit { preferences ->
            preferences[ARTIST_SONGS_SORTING_TYPE] = sortData.type
            preferences[ARTIST_SONGS_SORTING_ORDER] = sortData.order
        }
    }
}