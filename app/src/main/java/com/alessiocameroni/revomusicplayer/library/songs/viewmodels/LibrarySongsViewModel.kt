package com.alessiocameroni.revomusicplayer.library.songs.viewmodels

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.alessiocameroni.revomusicplayer.library.songs.data.LibrarySongData

class LibrarySongsViewModel: ViewModel() {
    private var initialized = false
    val librarySongs = mutableStateListOf<LibrarySongData>()

    fun initializeListIfNeeded(context: Context) {
        if(initialized) return

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
            Media.ARTIST
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
            val artistColumn = cursor.getColumnIndexOrThrow(Media.ARTIST)
            val albumIdColumn = cursor.getColumnIndexOrThrow(Media.ALBUM_ID)
            val durationColumn = cursor.getColumnIndexOrThrow(Media.DURATION)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val contentUri: Uri = ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, id)
                val title = cursor.getString(titleColumn)
                val artist = cursor.getString(artistColumn)
                val albumId = cursor.getLong(albumIdColumn)
                val duration = cursor.getInt(durationColumn)

                librarySongs.add(
                    LibrarySongData(
                        songId = id,
                        contentUri = contentUri,
                        songTitle = title,
                        artist = artist,
                        albumId = albumId,
                        duration = duration
                    )
                )
            }
        }
        initialized = true
    }
}