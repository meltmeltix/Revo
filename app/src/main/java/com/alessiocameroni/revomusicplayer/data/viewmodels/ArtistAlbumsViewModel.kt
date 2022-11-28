package com.alessiocameroni.revomusicplayer.data.viewmodels

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.alessiocameroni.revomusicplayer.data.classes.AlbumData

class ArtistAlbumsViewModel: ViewModel() {
    val libraryAlbums = mutableStateListOf<AlbumData>()
    private var initialized = false

    fun initializeArtistAlbumList(
        context: Context,
        artistId: Long
    ) {
        if(initialized) return

        val projection = arrayOf(
            MediaStore.Audio.Albums._ID,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST
        )

        val selection = "${MediaStore.Audio.Media.ARTIST_ID} = $artistId"
        val sortOrder = "${MediaStore.Audio.Media.ALBUM} ASC"
        val query = context.contentResolver.query(
            MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            sortOrder
        )

        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums._ID)
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
            val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val contentUri: Uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)

                val albumCover: Uri = Uri.parse("content://media/external/audio/albumart")
                val albumCoverUri: Uri = ContentUris.withAppendedId(albumCover, id)

                val title = cursor.getString(titleColumn)
                val artist = cursor.getString(artistColumn)

                libraryAlbums.add(
                    AlbumData(
                        albumId = id,
                        contentUri = contentUri,
                        albumTitle = title,
                        artistId = artistId,
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