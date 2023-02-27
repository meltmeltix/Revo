package com.alessiocameroni.revomusicplayer.ui.screens.library.songScreen

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.data.classes.SongData
import com.alessiocameroni.revomusicplayer.data.repository.SortingRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor(
    private val sortingRepositoryImpl: SortingRepositoryImpl
): ViewModel() {
    val librarySongs = mutableStateListOf<SongData>()
    private var initialized = false

    val sortingType = mutableStateOf(0)
    val sortingOrder = mutableStateOf(0)

    init {
        viewModelScope.launch {
            sortingRepositoryImpl.getSongSortingType().collect { sortingType.value = it }
        }
        viewModelScope.launch {
            sortingRepositoryImpl.getSongSortingOrder().collect { sortingOrder.value = it }
        }
    }

    /**
     * Song Fetching
     */
    fun initializeSongList(context: Context) {
        if (initialized) return

        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } else {
                Media.EXTERNAL_CONTENT_URI
            }

        val projection = arrayOf(
            Media._ID,
            Media.TITLE,
            Media.ALBUM_ID,
            Media.ALBUM,
            Media.ARTIST_ID,
            Media.ARTIST,
            Media.DURATION,
            Media.DATE_ADDED
        )

        val selection = "${Media.IS_MUSIC} != 0"
        val sortOrder = "${Media.DISPLAY_NAME} ASC"
        val query = context.contentResolver.query(
            collection,
            projection,
            selection,
            null,
            sortOrder
        )

        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(Media._ID)
            val titleColumn = cursor.getColumnIndexOrThrow(Media.TITLE)
            val albumIdColumn = cursor.getColumnIndexOrThrow(Media.ALBUM_ID)
            val albumColumn = cursor.getColumnIndexOrThrow(Media.ALBUM)
            val artistIdColumn = cursor.getColumnIndexOrThrow(Media.ARTIST_ID)
            val artistColumn = cursor.getColumnIndexOrThrow(Media.ARTIST)
            val durationColumn = cursor.getColumnIndexOrThrow(Media.DURATION)
            val dateAddedColumn = cursor.getColumnIndexOrThrow(Media.DATE_ADDED)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val contentUri: Uri = ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, id)
                val title = cursor.getString(titleColumn)
                val artistId = cursor.getLong(artistIdColumn)
                val artist = cursor.getString(artistColumn)
                val albumId = cursor.getLong(albumIdColumn)
                val album = cursor.getString(albumColumn)

                val albumCover: Uri = Uri.parse("content://media/external/audio/albumart")
                val albumCoverUri: Uri = ContentUris.withAppendedId(albumCover, albumId)

                val duration = cursor.getInt(durationColumn)
                val dateAdded = cursor.getLong(dateAddedColumn)

                librarySongs.add(
                    SongData(
                        songId = id,
                        contentUri = contentUri,
                        songTitle = title,
                        artistId = artistId,
                        artist = artist,
                        albumId = albumId,
                        album = album,
                        albumCoverUri = albumCoverUri,
                        duration = duration,
                        dateAdded = dateAdded
                    )
                )
            }
        }
        initialized = true
    }

    /**
     * Preferences management
     */
    fun saveSortTypeSelection(selection: Int) {
        viewModelScope.launch {
            sortingRepositoryImpl.setSongSortingType(selection)
        }
    }

    fun saveSortOrderSelection(selection: Int) {
        viewModelScope.launch {
            sortingRepositoryImpl.setSongSortingOrder(selection)
        }
    }
}