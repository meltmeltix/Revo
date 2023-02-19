package com.alessiocameroni.revomusicplayer.ui.screens.library.artistScreen.artistViewScreen

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Artists
import android.provider.MediaStore.Audio.Media
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alessiocameroni.revomusicplayer.data.classes.ArtistAlbumData
import com.alessiocameroni.revomusicplayer.data.classes.ArtistSongData
import com.alessiocameroni.revomusicplayer.util.functions.calculateSongDuration

class ArtistViewViewModel: ViewModel() {
    val artistSongs = mutableListOf<ArtistSongData>()
    val artistAlbums = mutableListOf<ArtistAlbumData>()
    private var artistAlbumListInitialized = false
    private var artistSongListInitialized = false
    private var artistInfoRetrieved = false

    private val _artist = MutableLiveData("")
    private val _artistPictureUri = MutableLiveData<Uri>()
    private val _artistTracksNumber = MutableLiveData("0")
    private val _artistAlbumsNumber = MutableLiveData("0")
    private val _albumAmountCheck = MutableLiveData(false)

    var artist: LiveData<String> = _artist
    var artistPictureUri: LiveData<Uri> = _artistPictureUri
    var artistAlbumsNumber: LiveData<String> = _artistAlbumsNumber
    var artistTrackNumber: LiveData<String> = _artistTracksNumber
    var albumAmountCheck: LiveData<Boolean> = _albumAmountCheck

    fun initializeArtistInfo(
        context: Context,
        artistId: Long
    ) {
        if (artistInfoRetrieved) return

        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Artists.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } else {
                Artists.EXTERNAL_CONTENT_URI
            }
        val projection = arrayOf(
            Artists.ARTIST,
            Artists.NUMBER_OF_ALBUMS,
            Artists.NUMBER_OF_TRACKS
        )
        val selection = "${Artists._ID} = $artistId"
        val query = context.contentResolver.query(
            collection,
            projection,
            selection,
            null,
            null
        )

        query?.use {  cursor ->
            val artistColumn = cursor.getColumnIndexOrThrow(Artists.ARTIST)
            val artistsAlbumsNumberColumn = cursor.getColumnIndexOrThrow(Artists.NUMBER_OF_ALBUMS)
            val artistTracksNumberColumn = cursor.getColumnIndexOrThrow(Artists.NUMBER_OF_TRACKS)

            cursor.moveToNext()

            _artist.value = cursor.getString(artistColumn)
            _artistAlbumsNumber.value = cursor.getString(artistsAlbumsNumberColumn)
            _artistTracksNumber.value = cursor.getString(artistTracksNumberColumn)
        }

        artistInfoRetrieved = true
    }

    fun initializeArtistAlbumList(
        context: Context,
        artistId: Long,
    ) {
        if (artistAlbumListInitialized) return

        var retrievedArtistPicture = false

        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } else {
                Media.EXTERNAL_CONTENT_URI
            }
        val projection = arrayOf(
            Media.ALBUM_ID,
            Media.ALBUM,
            Media.ARTIST_ID
        )
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
            val albumTitleColumn = cursor.getColumnIndexOrThrow(Media.ALBUM)

            while (cursor.moveToNext()) {
                val albumId = cursor.getLong(albumIdColumn)
                val albumTitle = cursor.getString(albumTitleColumn)
                val albumCover: Uri = Uri.parse("content://media/external/audio/albumart")
                val albumCoverUri: Uri = ContentUris.withAppendedId(albumCover, albumId)
                if(!retrievedArtistPicture) {
                    _artistPictureUri.value = albumCoverUri
                    retrievedArtistPicture = true
                }

                if(
                    !artistAlbums
                        .contains(ArtistAlbumData(albumId, albumTitle, albumCoverUri))
                ) {
                    artistAlbums
                        .add(ArtistAlbumData(albumId, albumTitle, albumCoverUri))
                }
            }
        }

        _albumAmountCheck.value = artistAlbums.isNotEmpty()
        artistAlbumListInitialized = true
    }

    fun initializeArtistSongList(
        context: Context,
        artistId: Long
    ) {
        if (artistSongListInitialized) return

        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } else {
                Media.EXTERNAL_CONTENT_URI
            }

        val projection = arrayOf(
            Media._ID,
            Media.DURATION,
            Media.TITLE,
            Media.TRACK,
            Media.ALBUM_ID,
            Media.ALBUM,
        )

        val selection = "${Media.IS_MUSIC} != 0 AND ${Media.ARTIST_ID} = $artistId"
        val sortOrder = "${Media.TITLE} ASC"
        val query = context.contentResolver.query(
            collection,
            projection,
            selection,
            null,
            sortOrder
        )

        query?.use {  cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(Media._ID)
            val trackColumn = cursor.getColumnIndexOrThrow(Media.TRACK)
            val titleColumn = cursor.getColumnIndexOrThrow(Media.TITLE)
            val durationColumn = cursor.getColumnIndexOrThrow(Media.DURATION)
            val albumIdColumn = cursor.getColumnIndexOrThrow(Media.ALBUM_ID)
            val albumColumn = cursor.getColumnIndexOrThrow(Media.ALBUM)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val contentUri: Uri = ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, id)
                val track = cursor.getString(trackColumn)
                val title = cursor.getString(titleColumn)
                val duration = cursor.getInt(durationColumn)
                val fixedDuration = calculateSongDuration(duration)
                val albumId = cursor.getLong(albumIdColumn)
                val album = cursor.getString(albumColumn)

                artistSongs.add(
                    ArtistSongData(
                        id,
                        contentUri,
                        track,
                        title,
                        duration,
                        fixedDuration,
                        albumId,
                        album,
                    )
                )
            }
        }
        artistSongListInitialized = true
    }
}