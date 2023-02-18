package com.alessiocameroni.revomusicplayer.ui.screens.library.albumScreen.albumViewScreen

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alessiocameroni.revomusicplayer.data.classes.AlbumSongData
import com.alessiocameroni.revomusicplayer.util.functions.calculateSongDuration
import kotlin.math.roundToInt

class AlbumViewViewModel: ViewModel() {
    val albumSongs = mutableListOf<AlbumSongData>()
    private var albumSongsInitialized = false
    private var albumInfoRetrieved = false

    private val _albumCoverUri = MutableLiveData<Uri>()
    private val _albumTitle = MutableLiveData("")
    private val _artist = MutableLiveData("")
    private val _albumSongAmount = MutableLiveData(0)
    private val _albumHoursAmount = MutableLiveData(0)
    private val _albumMinutesAmount = MutableLiveData(0)
    private val _albumSecondsAmount = MutableLiveData(0)

    var albumCoverUri: LiveData<Uri> = _albumCoverUri
    var albumTitle: LiveData<String> = _albumTitle
    var artistId: Long = 0
    var artist: LiveData<String> = _artist
    var albumSongAmount: LiveData<Int> = _albumSongAmount
    var albumHoursAmount: LiveData<Int> = _albumHoursAmount
    var albumMinutesAmount: LiveData<Int> = _albumMinutesAmount
    var albumSecondsAmount: LiveData<Int> = _albumSecondsAmount

    fun initializeAlbumSongsList(
        context: Context,
        albumId: Long,
    ) {
        if (albumSongsInitialized) return

        val _totalDuration = MutableLiveData(0)
        val totalDuration: LiveData<Int> = _totalDuration

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
        val sortOrder = "${Media.TRACK} ASC"
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
                val track = cursor.getString(trackColumn)
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

                _albumSongAmount.value = _albumSongAmount.value?.plus(1)
                _totalDuration.value = _totalDuration.value?.plus(duration)
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

        _albumTitle.value = cursor.getString(albumTitleColumn)
        artistId = cursor.getLong(artistIdColumn)
        _artist.value = cursor.getString(artistColumn)

        val albumCover: Uri = Uri.parse("content://media/external/audio/albumart")
        _albumCoverUri.value = ContentUris.withAppendedId(albumCover, albumId)

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

        _albumHoursAmount.value = hours.toInt()
        _albumMinutesAmount.value = minutes.toInt()
        _albumSecondsAmount.value = seconds
    }
}