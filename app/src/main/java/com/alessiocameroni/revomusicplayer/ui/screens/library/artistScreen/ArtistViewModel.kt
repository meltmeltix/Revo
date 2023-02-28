package com.alessiocameroni.revomusicplayer.ui.screens.library.artistScreen

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Artists
import android.provider.MediaStore.Audio.Media
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.data.classes.ArtistData
import com.alessiocameroni.revomusicplayer.data.classes.SortData
import com.alessiocameroni.revomusicplayer.data.repository.SortingRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val sortingRepositoryImpl: SortingRepositoryImpl
): ViewModel() {
    val libraryArtists = mutableStateListOf<ArtistData>()
    private var initialized = false

    val sortingOrder = mutableStateOf(0)

    init {
        viewModelScope.launch {
            sortingRepositoryImpl.getArtistSorting().collect {
                sortingOrder.value = it.order
            }
        }
    }

    /**
     * Artist fetching
     */
    fun initializeArtistList(context: Context) {
        if(initialized) return

        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } else {
                Artists.EXTERNAL_CONTENT_URI
            }

        val projection = arrayOf(
            Media.ARTIST_ID,
            Media.ARTIST,
            Media.ALBUM_ID,
        )
        val selection = "${Media.IS_MUSIC} != 0"
        val sortOrder = "${Media.ARTIST} ASC"
        val query = context.contentResolver.query(
            collection,
            projection,
            selection,
            null,
            sortOrder
        )

        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(Media.ARTIST_ID)
            val artistColumn = cursor.getColumnIndexOrThrow(Media.ARTIST)
            val albumIdColumn = cursor.getColumnIndexOrThrow(Media.ALBUM_ID)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val artist = cursor.getString(artistColumn)
                val albumId = cursor.getLong(albumIdColumn)

                val albumCover: Uri = Uri.parse("content://media/external/audio/albumart")
                val albumCoverUri: Uri = ContentUris.withAppendedId(albumCover, albumId)

                if(!libraryArtists.any { it.artistId == id && it.artist == artist } ) {
                    libraryArtists.add(
                        ArtistData(id, artist, albumCoverUri)
                    )
                }

            }
        }
        initialized = true
    }

    /**
     * Preferences management
     */
    fun setSortData(order: Int) {
        viewModelScope.launch {
            sortingRepositoryImpl.setArtistSorting(SortData(0, order))
        }
    }
}