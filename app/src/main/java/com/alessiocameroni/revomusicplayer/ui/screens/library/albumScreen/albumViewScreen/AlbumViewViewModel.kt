package com.alessiocameroni.revomusicplayer.ui.screens.library.albumScreen.albumViewScreen

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.data.classes.AlbumSongData
import com.alessiocameroni.revomusicplayer.data.classes.SortingValues
import com.alessiocameroni.revomusicplayer.data.repository.SortingRepositoryImpl
import com.alessiocameroni.revomusicplayer.util.functions.calculateSongDuration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class AlbumViewViewModel @Inject constructor(
    private val sortingRepositoryImpl: SortingRepositoryImpl
): ViewModel() {
    val albumSongs = mutableListOf<AlbumSongData>()
    private var albumSongsInitialized = false
    private var albumInfoRetrieved = false

    var albumCoverUri = mutableStateOf<Uri?>(null)
    var albumTitle = mutableStateOf("Album Title")
    var artistId: Long = 0
    var artist = mutableStateOf("Artist Name")
    var albumSongAmount = mutableStateOf(0)
    var albumHoursAmount = mutableStateOf(0)
    var albumMinutesAmount = mutableStateOf(0)
    var albumSecondsAmount = mutableStateOf(0)

    var sortingType = mutableStateOf(0)
    var sortingOrder = mutableStateOf(0)

    init {
        viewModelScope.launch {
            sortingRepositoryImpl.getAlbumSongsSorting().collect {
                sortingType.value = it.type
                sortingOrder.value = it.order
            }
        }
    }

    /**
     * Album fetching
     */
    fun initializeAlbumSongsList(
        context: Context,
        albumId: Long,
    ) {
        if (albumSongsInitialized) return

        val totalDuration = mutableStateOf(0)

        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } else {
                Media.EXTERNAL_CONTENT_URI
            }

        val projection = arrayOf(
            Media._ID,
            Media.DURATION,
            Media.TITLE,
            Media.TRACK,
            Media.ALBUM,
            Media.ARTIST_ID,
            Media.ARTIST,
        )

        val selection =
            "${Media.IS_MUSIC} != 0 AND ${Media.ALBUM_ID} = $albumId"
        val sortOrder = "${Media.TITLE} ASC"
        val query = context.contentResolver.query(
            collection,
            projection,
            selection,
            null,
            sortOrder
        )

        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(Media._ID)
            val trackColumn = cursor.getColumnIndexOrThrow(Media.TRACK)
            val titleColumn = cursor.getColumnIndexOrThrow(Media.TITLE)
            val durationColumn = cursor.getColumnIndexOrThrow(Media.DURATION)
            val albumTitleColumn = cursor.getColumnIndexOrThrow(Media.ALBUM)
            val artistIdColumn = cursor.getColumnIndexOrThrow(Media.ARTIST_ID)
            val artistColumn = cursor.getColumnIndexOrThrow(Media.ARTIST)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val contentUri: Uri = ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, id)
                val title = cursor.getString(titleColumn)
                val track = cursor.getInt(trackColumn)
                val duration = cursor.getInt(durationColumn)
                val fixedDuration = calculateSongDuration(duration)

                retrieveAlbumInfo(
                    albumId,
                    cursor,
                    albumTitleColumn,
                    artistIdColumn,
                    artistColumn
                )

                albumSongs.add(
                    AlbumSongData(
                        id,
                        contentUri,
                        track,
                        title,
                        duration,
                        fixedDuration
                    )
                )

                albumSongAmount.value = albumSongAmount.value.plus(1)
                totalDuration.value = totalDuration.value.plus(duration)
            }

            calculateAlbumDuration(totalDuration.value)
        }
        albumSongsInitialized = true
    }

    private fun retrieveAlbumInfo(
        albumId: Long,
        cursor: Cursor,
        albumTitleColumn: Int,
        artistIdColumn: Int,
        artistColumn: Int
    ) {
        if (albumInfoRetrieved) return

        albumTitle.value = cursor.getString(albumTitleColumn)
        artistId = cursor.getLong(artistIdColumn)
        artist.value = cursor.getString(artistColumn)

        val albumCover: Uri = Uri.parse("content://media/external/audio/albumart")
        albumCoverUri.value = ContentUris.withAppendedId(albumCover, albumId)

        albumInfoRetrieved = true
    }

    private fun calculateAlbumDuration(duration: Int?) {
        val fixedDuration: Double = (duration ?: 0).toDouble() / 1000

        val hours: Double = fixedDuration / 3600
        var minutes: Double = (hours - hours.toInt()) * 60
        var seconds: Int = ((minutes - minutes.toInt()) * 60).roundToInt()
        when(seconds) {
            60 -> {
                seconds = 0
                minutes++
            }
        }

        albumHoursAmount.value = hours.toInt()
        albumMinutesAmount.value = minutes.toInt()
        albumSecondsAmount.value = seconds
    }

    /**
     * Preferences management
     */
    fun setSortData(type: Int, order: Int) {
        viewModelScope.launch {
            sortingRepositoryImpl.setAlbumSongsSorting(SortingValues(type, order))
        }
    }
}