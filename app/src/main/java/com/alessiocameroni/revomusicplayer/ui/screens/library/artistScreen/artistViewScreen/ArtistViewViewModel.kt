package com.alessiocameroni.revomusicplayer.ui.screens.library.artistScreen.artistViewScreen

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore.Audio.Albums
import android.provider.MediaStore.Audio.Media
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alessiocameroni.revomusicplayer.data.classes.ArtistAlbumData
import com.alessiocameroni.revomusicplayer.data.classes.ArtistSongData

class ArtistViewViewModel: ViewModel() {
    val artistSongs = mutableListOf<ArtistSongData>()
    val artistAlbums = mutableListOf<ArtistAlbumData>()
    private var artistListsInitialized = false
    private var artistInfoRetrieved = false

    private val _artistSongAmount = MutableLiveData(0)
    private val _artistAlbumAmount = MutableLiveData(0)

    var artistSongAmount: LiveData<Int> = _artistSongAmount
    var artistAlbumAmount: LiveData<Int> = _artistAlbumAmount

    fun initializeArtistLists(
        context: Context,
        artistId: Long,
    ) {
        if (artistListsInitialized) return

        val projection = arrayOf(
            Media.ALBUM,
            Media.ALBUM_ID,
        )

        val selection = "${Media.ARTIST_ID} = $artistId"
        val sortOrder = "${Media.ALBUM} ASC"
        val query = context.contentResolver.query(
            Albums.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            sortOrder
        )

        query?.use { cursor ->
            val albumIdColumn = cursor.getColumnIndexOrThrow(Media.ALBUM_ID)
            val albumTitleColumn = cursor.getColumnIndexOrThrow(Media.ALBUM)

            while (cursor.moveToNext()) {
                val albumId = cursor.getLong(albumIdColumn)
                val albumTitle = cursor.getString(albumTitleColumn)

                val albumCover: Uri = Uri.parse("content://media/external/audio/albumart")
                val albumCoverUri: Uri = ContentUris.withAppendedId(albumCover, albumId)

                artistAlbums.add(
                    ArtistAlbumData(
                        albumId = albumId,
                        albumTitle = albumTitle,
                        albumCoverUri = albumCoverUri
                    )
                )

                _artistAlbumAmount.value = _artistAlbumAmount.value?.plus(1)
            }
        }

        artistListsInitialized = true
    }

    fun retrieveArtistInfo(

    ) {

    }
}