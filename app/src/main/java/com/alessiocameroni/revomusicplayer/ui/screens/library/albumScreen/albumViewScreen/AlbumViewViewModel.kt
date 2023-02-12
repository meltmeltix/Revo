package com.alessiocameroni.revomusicplayer.ui.screens.library.albumScreen.albumViewScreen

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alessiocameroni.revomusicplayer.data.classes.AlbumSongData

class AlbumViewViewModel: ViewModel() {
    val albumSongs = mutableListOf<AlbumSongData>()

    private val _albumTitle = MutableLiveData("")
    var albumTitle: LiveData<String> = _albumTitle

    var artistId: Long = 0

    private val _artist = MutableLiveData("")
    var artist: LiveData<String> = _artist

    lateinit var albumCoverUri: Uri

    private var initialized = false

    /*val albumCover: Uri = Uri.parse("content://media/external/audio/albumart")
    val albumCoverUri: Uri = ContentUris.withAppendedId(albumCover, albumId)*/

    fun initializeAlbumSongsList(
        context: Context,
        albumId: Long,
    ) {
        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } else {
                Media.EXTERNAL_CONTENT_URI
            }

        val projection = arrayOf(
            Media._ID,
            Media.DISPLAY_NAME,
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

                _albumTitle.value = cursor.getString(albumTitleColumn)
                artistId = cursor.getLong(artistIdColumn)
                _artist.value = cursor.getString(artistColumn)

                /*val albumCover: Uri = Uri.parse("content://media/external/audio/albumart")
                albumCoverUri = ContentUris.withAppendedId(albumCover, albumId)*/

                albumSongs.add(
                    AlbumSongData(
                        id,
                        contentUri,
                        track,
                        title,
                        duration
                    )
                )
            }
        }
        initialized = true
    }
}