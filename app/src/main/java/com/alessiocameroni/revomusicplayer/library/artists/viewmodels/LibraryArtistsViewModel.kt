package com.alessiocameroni.revomusicplayer.library.artists.viewmodels

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore.Audio.Artists
import android.provider.MediaStore.Audio.Media
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.alessiocameroni.revomusicplayer.library.artists.data.LibraryArtistData

class LibraryArtistsViewModel: ViewModel() {
    val libraryArtists = mutableStateListOf<LibraryArtistData>()

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
                    LibraryArtistData(
                        artistId = id,
                        contentUri = contentUri,
                        artistName = artist,
                        tracksNumber = null,
                        albumsNumber = null
                    )
                )
            }
        }
        initialized = true
    }

    /*fun loadBitmapIfNeeded(context: Context, index: Int) {
        if(librarySongs[index].albumCover != null) return

        backgroundScope.launch {
            val bitmap = getAlbumArt(context, librarySongs[index].contentUri)
            librarySongs[index] = librarySongs[index].copy(albumCover = bitmap)
        }
    }

    private fun getAlbumArt(context: Context, uri: Uri): Bitmap {
        val mmr = MediaMetadataRetriever()
        mmr.setDataSource(context, uri)

        val data = mmr.embeddedPicture

        return if (data != null) {
            BitmapFactory.decodeByteArray(data, 0, data.size)
        } else {
            Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        }
    }*/
}