package com.alessiocameroni.revomusicplayer.data.viewmodels

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.alessiocameroni.revomusicplayer.data.classes.SongData

class SongsViewModel: ViewModel() {
    private var initialized = false
    val librarySongs = mutableStateListOf<SongData>()

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
            Media.DISPLAY_NAME,
            Media.DURATION,
            Media.TITLE,
            Media.ALBUM_ID,
            Media.ARTIST_ID,
            Media.ARTIST,
        )

        val selection = Media.IS_MUSIC + "!= 0"
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
            val artistIdColumn = cursor.getColumnIndexOrThrow(Media.ARTIST_ID)
            val artistColumn = cursor.getColumnIndexOrThrow(Media.ARTIST)
            val albumIdColumn = cursor.getColumnIndexOrThrow(Media.ALBUM_ID)
            val durationColumn = cursor.getColumnIndexOrThrow(Media.DURATION)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val contentUri: Uri = ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, id)
                val title = cursor.getString(titleColumn)
                val artistId = cursor.getLong(artistIdColumn)
                val artist = cursor.getString(artistColumn)
                val albumId = cursor.getLong(albumIdColumn)

                val albumCover: Uri = Uri.parse("content://media/external/audio/albumart")
                val albumCoverUri: Uri = ContentUris.withAppendedId(albumCover, albumId)

                val duration = cursor.getInt(durationColumn)

                librarySongs.add(
                    SongData(
                        songId = id,
                        contentUri = contentUri,
                        songTitle = title,
                        artistId = artistId,
                        artist = artist,
                        albumId = albumId,
                        albumCoverUri = albumCoverUri,
                        duration = duration
                    )
                )
            }
        }
        initialized = true
    }

    fun initializeArtistSongList(
        context: Context,
        artistId: Long,
    ) {

    }
}