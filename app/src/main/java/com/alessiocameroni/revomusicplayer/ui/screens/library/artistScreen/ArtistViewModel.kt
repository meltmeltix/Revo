package com.alessiocameroni.revomusicplayer.ui.screens.library.artistScreen

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Artists
import android.provider.MediaStore.Audio.Media
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alessiocameroni.revomusicplayer.data.classes.ArtistData

class ArtistViewModel: ViewModel() {
    val libraryArtists = mutableStateListOf<ArtistData>()
    private var initialized = false

    private val _artistPictureUri = MutableLiveData<Uri>()
    private var artistPictureUri = _artistPictureUri

    fun initializeArtistList(context: Context) {
        if(initialized) return

        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Artists.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } else {
                Artists.EXTERNAL_CONTENT_URI
            }

        val projection = arrayOf(
            Artists._ID,
            Artists.ARTIST,
            Artists.NUMBER_OF_ALBUMS,
            Artists.NUMBER_OF_TRACKS,
        )
        val selection = null
        val sortOrder = "${Artists.ARTIST} ASC"
        val query = context.contentResolver.query(
            collection,
            projection,
            selection,
            null,
            sortOrder
        )

        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(Artists._ID)
            val artistColumn = cursor.getColumnIndexOrThrow(Artists.ARTIST)
            val artistAlbumsNumberColumn = cursor.getColumnIndexOrThrow(Artists.NUMBER_OF_ALBUMS)
            val artistTracksNumberColumn = cursor.getColumnIndexOrThrow(Artists.NUMBER_OF_TRACKS)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val artist = cursor.getString(artistColumn)
                val albumNumber = cursor.getString(artistAlbumsNumberColumn)
                val tracksNumber = cursor.getString(artistTracksNumberColumn)

                retrieveAlbumImage(
                    context,
                    id
                )

                libraryArtists.add(
                    ArtistData(
                        artistId = id,
                        artist = artist,
                        albumsNumber = albumNumber,
                        tracksNumber = tracksNumber,
                        artistPictureUri = artistPictureUri.value
                    )
                )
            }
        }
        initialized = true
    }

    private fun retrieveAlbumImage(
        context: Context,
        artistId: Long
    ) {
        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } else {
                Media.EXTERNAL_CONTENT_URI
            }

        val projection = arrayOf(Media.ALBUM_ID)
        val selection = "${Media.ARTIST_ID} = $artistId"
        val sortOrder = "${Media.ALBUM} ASC"
        val query = context.contentResolver.query(
            collection,
            projection,
            selection,
            null,
            sortOrder
        )

        query?.use { cursor ->
            val albumIdColumn = cursor.getColumnIndexOrThrow(Media.ALBUM_ID)

            cursor.moveToNext()

            val albumId = cursor.getLong(albumIdColumn)
            val albumCover: Uri = Uri.parse("content://media/external/audio/albumart")
            _artistPictureUri.value = ContentUris.withAppendedId(albumCover, albumId)
        }
    }
}