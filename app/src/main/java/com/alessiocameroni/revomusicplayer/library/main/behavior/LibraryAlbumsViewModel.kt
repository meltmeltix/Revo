package com.alessiocameroni.revomusicplayer.library.main.behavior

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore.Audio.Albums
import android.provider.MediaStore.Audio.Media
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.alessiocameroni.revomusicplayer.library.main.data.LibraryAlbumData

class LibraryAlbumsViewModel: ViewModel() {
    val libraryAlbums = mutableStateListOf<LibraryAlbumData>()

    private var initialized = false

    fun initializeListIfNeeded(context: Context) {
        if(initialized) return

        val projection = arrayOf(
            Albums.ALBUM_ID,
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
            val idColumn = cursor.getColumnIndexOrThrow(Albums.ALBUM_ID)
            val titleColumn = cursor.getColumnIndexOrThrow(Media.ALBUM)
            val artistColumn = cursor.getColumnIndexOrThrow(Media.ARTIST)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val contentUri: Uri = ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, id)
                val title = cursor.getString(titleColumn)
                val artist = cursor.getString(artistColumn)

                libraryAlbums.add(
                    LibraryAlbumData(
                        albumId = id,
                        contentUri = contentUri,
                        albumTitle = title,
                        artist = artist,
                        //albumCover = null,\
                        tracksNumber = null,
                        duration = null
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