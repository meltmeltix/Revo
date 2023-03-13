package com.alessiocameroni.revomusicplayer.data.repository

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Artists
import android.provider.MediaStore.Audio.Media
import androidx.annotation.WorkerThread
import com.alessiocameroni.revomusicplayer.data.classes.Artist
import com.alessiocameroni.revomusicplayer.domain.repository.ArtistsRepository

class ArtistsRepositoryImpl(
    private val context: Context
): ArtistsRepository {
    private companion object {
        var mCursor: Cursor? = null
        val collection: Uri =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } else {
                Artists.EXTERNAL_CONTENT_URI
            }
        val projection = arrayOf(
            Media.ARTIST_ID,
            Media.ARTIST,
            Media.ALBUM_ID,
        )
        const val selection = "${Media.IS_MUSIC} != 0"
        const val sortOrder = "${Media.ARTIST} ASC"
    }

    @WorkerThread
    private fun artistContentResolver(): List<Artist> {
        val artistList = mutableListOf<Artist>()

        mCursor = context.contentResolver.query(
            collection,
            projection,
            selection,
            null,
            sortOrder
        )

        mCursor?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(Media.ARTIST_ID)
            val artistColumn = cursor.getColumnIndexOrThrow(Media.ARTIST)
            val albumIdColumn = cursor.getColumnIndexOrThrow(Media.ALBUM_ID)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val artist = cursor.getString(artistColumn)
                val albumId = cursor.getLong(albumIdColumn)

                val albumCover: Uri = Uri.parse("content://media/external/audio/albumart")
                val albumCoverUri: Uri = ContentUris.withAppendedId(albumCover, albumId)

                if(!artistList.any { it.artistId == id && it.artist == artist } ) {
                    artistList.add(
                        Artist(id, artist, albumCoverUri)
                    )
                }

            }
        }

        return artistList
    }

    override suspend fun fetchArtistList(): List<Artist> = artistContentResolver()
}