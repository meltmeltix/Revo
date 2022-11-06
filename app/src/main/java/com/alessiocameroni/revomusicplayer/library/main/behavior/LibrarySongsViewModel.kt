package com.alessiocameroni.revomusicplayer.library.main.behavior

import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.library.main.data.LibrarySongData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class LibrarySongsViewModel: ViewModel() {
    val librarySongs = mutableStateListOf<LibrarySongData>()

    private var initialized = false
    private val backgroundScope = viewModelScope.plus(Dispatchers.Default)

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
            //val albumTitleColumn = cursor.getColumnIndexOrThrow(Media.ALBUM)
            val durationColumn = cursor.getColumnIndexOrThrow(Media.DURATION)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val contentUri: Uri = ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, id)
                val title = cursor.getString(titleColumn)
                val artist = cursor.getString(artistColumn)
                val albumId = cursor.getLong(albumIdColumn)
                //val albumTitle = cursor.getString(albumTitleColumn)
                val duration = cursor.getInt(durationColumn)

                librarySongs.add(
                    LibrarySongData(
                        songId = id,
                        contentUri = contentUri,
                        songTitle = title,
                        artist = artist,
                        albumId = albumId,
                        //albumTitle = albumTitle,
                        albumCover = null,
                        duration = duration
                    )
                )
            }
        }
        initialized = true
    }

    fun loadBitmapIfNeeded(context: Context, index: Int) {
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
    }
}