package com.alessiocameroni.revomusicplayer.ui.screens.library.artistScreen

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Artists
import android.provider.MediaStore.Audio.Media
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.alessiocameroni.revomusicplayer.data.classes.ArtistData

class ArtistViewModel: ViewModel() {
    val libraryArtists = mutableStateListOf<ArtistData>()
    private var initialized = false

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
            Media.ALBUM_ID
        )
        val selection = null
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

                //!libraryArtists.contains(ArtistData(id, artist))

                if(!libraryArtists.any { it.artistId == id && it.artist == artist } ) {
                    libraryArtists.add(
                        ArtistData(id, artist, albumCoverUri)
                    )
                }

            }
        }
        initialized = true
    }
}