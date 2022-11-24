package com.alessiocameroni.revomusicplayer.data.viewmodels

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore.Audio.Albums
import android.provider.MediaStore.Audio.Media
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.alessiocameroni.revomusicplayer.data.classes.AlbumData

class AlbumsViewModel: ViewModel() {
    val libraryAlbums = mutableStateListOf<AlbumData>()

    private var initialized = false

    fun initializeListIfNeeded(context: Context) {
        if(initialized) return

        val projection = arrayOf(
            Albums._ID,
            Media.ALBUM,
            Media.ARTIST
        )

        val selection = null
        val sortOrder = "${Media.ALBUM} ASC"
        val query = context.contentResolver.query(
            Albums.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            sortOrder
        )

        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(Albums._ID)
            val titleColumn = cursor.getColumnIndexOrThrow(Media.ALBUM)
            val artistColumn = cursor.getColumnIndexOrThrow(Media.ARTIST)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val contentUri: Uri = ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, id)

                val albumCover: Uri = Uri.parse("content://media/external/audio/albumart")
                val albumCoverUri: Uri = ContentUris.withAppendedId(albumCover, id)

                val title = cursor.getString(titleColumn)
                val artist = cursor.getString(artistColumn)

                libraryAlbums.add(
                    AlbumData(
                        albumId = id,
                        contentUri = contentUri,
                        albumTitle = title,
                        artist = artist,
                        albumCoverUri = albumCoverUri,
                        tracksNumber = null,
                        duration = null
                    )
                )
            }
        }
        initialized = true
    }
}