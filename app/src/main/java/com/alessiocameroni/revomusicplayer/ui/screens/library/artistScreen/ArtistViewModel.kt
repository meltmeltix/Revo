package com.alessiocameroni.revomusicplayer.ui.screens.library.artistScreen

import android.content.Context
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

        val projection = arrayOf(
            Artists._ID,
            Artists.ARTIST,
            Artists.NUMBER_OF_ALBUMS,
            Artists.NUMBER_OF_TRACKS,
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
            val artistColumn = cursor.getColumnIndexOrThrow(Artists.ARTIST)
            val artistAlbumsNumberColumn = cursor.getColumnIndexOrThrow(Artists.NUMBER_OF_ALBUMS)
            val artistSongsNumberColumn = cursor.getColumnIndexOrThrow(Artists.NUMBER_OF_TRACKS)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val artist = cursor.getString(artistColumn)
                val albumNumber = cursor.getString(artistAlbumsNumberColumn)
                val tracksNumber = cursor.getString(artistSongsNumberColumn)

                libraryArtists.add(
                    ArtistData(
                        artistId = id,
                        artist = artist,
                        albumsNumber = albumNumber,
                        tracksNumber = tracksNumber,
                    )
                )
            }
        }
        initialized = true
    }
}