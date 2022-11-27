package com.alessiocameroni.revomusicplayer.data.viewmodels

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore.Audio.Artists
import android.provider.MediaStore.Audio.Media
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.alessiocameroni.revomusicplayer.data.classes.ArtistData

class ArtistsViewModel: ViewModel() {
    val libraryArtists = mutableStateListOf<ArtistData>()

    private var initialized = false

    fun initializeListIfNeeded(context: Context) {
        if(initialized) return

        val projection = arrayOf(
            Artists._ID,
            Media.ARTIST
        )

        val selection = null
        val sortOrder = "${Media.ARTIST} ASC"
        val query = context.contentResolver.query(
            Artists.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            sortOrder
        )

        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(Artists._ID)
            val artistColumn = cursor.getColumnIndexOrThrow(Media.ARTIST)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val contentUri: Uri = ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, id)
                val artist = cursor.getString(artistColumn)

                libraryArtists.add(
                    ArtistData(
                        artistId = id,
                        contentUri = contentUri,
                        artist = artist,
                        tracksNumber = null,
                        albumsNumber = null
                    )
                )
            }
        }
        initialized = true
    }
}