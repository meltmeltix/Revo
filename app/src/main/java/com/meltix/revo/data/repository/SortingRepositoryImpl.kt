package com.meltix.revo.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.meltix.revo.domain.repository.SortingRepository
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
        val SONG_SORTING_ORDER = intPreferencesKey("song_sorting_descending")

        val ALBUM_SORTING_TYPE = intPreferencesKey("album_sorting_type")
        val ALBUM_SORTING_ORDER = intPreferencesKey("album_sorting_order")
        val ALBUM_SONGS_SORTING_TYPE = intPreferencesKey("album_songs_sorting_type")
        val ALBUM_SONGS_SORTING_ORDER = intPreferencesKey("album_songs_sorting_order")

        val ARTIST_SORTING_ORDER = intPreferencesKey("artist_sorting_order")
        val ARTIST_SONGS_SORTING_TYPE = intPreferencesKey("artist_songs_sorting_type")
        val ARTIST_SONGS_SORTING_ORDER = intPreferencesKey("artist_songs_sorting_order")
    }

    // Get Data functions
    override fun getSongSortType(): Flow<Int> = context.dataStore.data
        .map { preferences -> preferences[SONG_SORTING_TYPE] ?: 0 }

    override fun getSongSortOrder(): Flow<Int> = context.dataStore.data
        .map { preferences -> preferences[SONG_SORTING_ORDER] ?: 0 }

    override fun getAlbumSortType(): Flow<Int> = context.dataStore.data
        .map { preferences -> preferences[ALBUM_SORTING_TYPE] ?: 0 }

    override fun getAlbumSortOrder(): Flow<Int> = context.dataStore.data
        .map { preferences -> preferences[ALBUM_SORTING_ORDER] ?: 0 }

    override fun getAlbumSongsSortType(): Flow<Int> = context.dataStore.data
        .map { preferences -> preferences[ALBUM_SONGS_SORTING_TYPE] ?: 0 }

    override fun getAlbumSongsSortOrder(): Flow<Int> = context.dataStore.data
        .map { preferences -> preferences[ALBUM_SONGS_SORTING_ORDER] ?: 0 }

    override fun getArtistSortOrder(): Flow<Int> = context.dataStore.data
        .map { preferences -> preferences[ARTIST_SORTING_ORDER] ?: 0 }

    override fun getArtistSongsSortType(): Flow<Int> = context.dataStore.data
        .map { preferences -> preferences[ARTIST_SONGS_SORTING_TYPE] ?: 0 }

    override fun getArtistSongsSortOrder(): Flow<Int> = context.dataStore.data
        .map { preferences -> preferences[ARTIST_SONGS_SORTING_ORDER] ?: 0 }

    // Set Data functions
    override suspend fun setSongSortType(type: Int)
        { context.dataStore.edit { preferences -> preferences[SONG_SORTING_TYPE] = type } }

    override suspend fun setSongSortOrder(order: Int)
        { context.dataStore.edit { preferences -> preferences[SONG_SORTING_ORDER] = order } }

    override suspend fun setAlbumSortType(type: Int)
        { context.dataStore.edit { preferences -> preferences[ALBUM_SORTING_TYPE] = type } }

    override suspend fun setAlbumSortOrder(order: Int)
        { context.dataStore.edit { preferences -> preferences[ALBUM_SORTING_ORDER] = order } }

    override suspend fun setAlbumSongsSortType(type: Int)
        { context.dataStore.edit { preferences -> preferences[ALBUM_SONGS_SORTING_TYPE] = type } }

    override suspend fun setAlbumSongsSortOrder(order: Int)
        { context.dataStore.edit { preferences -> preferences[ALBUM_SONGS_SORTING_ORDER] = order } }

    override suspend fun setArtistOrder(order: Int)
        { context.dataStore.edit { preferences -> preferences[ARTIST_SORTING_ORDER] = order } }

    override suspend fun setArtistSongsSortType(type: Int)
        { context.dataStore.edit { preferences -> preferences[ARTIST_SONGS_SORTING_TYPE] = type } }

    override suspend fun setArtistSongsSortOrder(order: Int)
        { context.dataStore.edit { preferences -> preferences[ARTIST_SONGS_SORTING_ORDER] = order } }
}