package com.alessiocameroni.revomusicplayer.ui.screens.library.albumScreen

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Albums
import android.provider.MediaStore.Audio.Media
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.data.classes.AlbumData
import com.alessiocameroni.revomusicplayer.data.repository.SortingRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val sortingRepositoryImpl: SortingRepositoryImpl
): ViewModel() {
    val libraryAlbums = mutableStateListOf<AlbumData>()
    private var initialized = false

    val sortingType = mutableStateOf(0)
    val sortingOrder = mutableStateOf(0)

    init {
        viewModelScope.launch {
            sortingRepositoryImpl.getAlbumSortingType().collect { sortingType.value = it }
        }
        viewModelScope.launch {
            sortingRepositoryImpl.getAlbumSortingOrder().collect { sortingOrder.value = it }
        }
    }

    /**
     * Album fetching
     */
    fun initializeAlbumList(context: Context) {
        if(initialized) return

        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Albums.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } else {
                Albums.EXTERNAL_CONTENT_URI
            }

        val projection = arrayOf(
            Albums._ID,
            Albums.ALBUM,
            Media.ARTIST_ID,
            Albums.ARTIST,
            Albums.FIRST_YEAR,
            Albums.NUMBER_OF_SONGS,
        )

        val selection = null
        val sortOrder = "${Media.ALBUM} ASC"
        val query = context.contentResolver.query(
            collection,
            projection,
            selection,
            null,
            sortOrder
        )

        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(Albums._ID)
            val titleColumn = cursor.getColumnIndexOrThrow(Albums.ALBUM)
            val artistIdColumn = cursor.getColumnIndexOrThrow(Media.ARTIST_ID)
            val artistColumn = cursor.getColumnIndexOrThrow(Albums.ARTIST)
            val firstYearColumn = cursor.getColumnIndexOrThrow(Albums.FIRST_YEAR)
            val numberOfSongsColumn = cursor.getColumnIndexOrThrow(Albums.NUMBER_OF_SONGS)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val title = cursor.getString(titleColumn)
                val artistId = cursor.getLong(artistIdColumn)
                val artist = cursor.getString(artistColumn)

                val albumCover: Uri = Uri.parse("content://media/external/audio/albumart")
                val albumCoverUri: Uri = ContentUris.withAppendedId(albumCover, id)

                val firstYear = cursor.getLong(firstYearColumn)
                val numberOfSongs = cursor.getLong(numberOfSongsColumn)

                libraryAlbums.add(
                    AlbumData(
                        albumId = id,
                        albumTitle = title,
                        artistId = artistId,
                        artist = artist,
                        albumCoverUri = albumCoverUri,
                        year = firstYear,
                        numberOfSongs = numberOfSongs,
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
            sortingRepositoryImpl.setAlbumSortingType(selection)
        }
    }

    fun saveSortOrderSelection(selection: Int) {
        viewModelScope.launch {
            sortingRepositoryImpl.setAlbumSortingOrder(selection)
        }
    }
}