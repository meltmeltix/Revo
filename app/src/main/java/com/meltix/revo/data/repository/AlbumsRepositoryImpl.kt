package com.meltix.revo.data.repository

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Albums
import android.provider.MediaStore.Audio.Media
import androidx.annotation.WorkerThread
import com.meltix.revo.data.classes.album.Album
import com.meltix.revo.domain.repository.AlbumsRepository

class AlbumsRepositoryImpl(
    private val context: Context
): AlbumsRepository {
    private companion object {
        var mCursor: Cursor? = null
        val collection: Uri =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Albums.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } else {
                Albums.EXTERNAL_CONTENT_URI
            }
        val projection = arrayOf(
            Albums._ID,
            Albums.ALBUM,
            Media.ARTIST_ID,
            Albums.ARTIST,
            Albums.FIRST_YEAR,
            Albums.NUMBER_OF_SONGS,
        )
        val selection = null
        const val sortOrder = "${Media.ALBUM} ASC"
    }

    @WorkerThread
    private fun albumContentResolver(): List<Album> {
        val albumList = mutableListOf<Album>()

        mCursor = context.contentResolver.query(
            collection,
            projection,
            selection,
            null,
            sortOrder
        )

        mCursor?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(Albums._ID)
            val titleColumn = cursor.getColumnIndexOrThrow(Albums.ALBUM)
            val artistIdColumn = cursor.getColumnIndexOrThrow(Media.ARTIST_ID)
            val artistColumn = cursor.getColumnIndexOrThrow(Albums.ARTIST)
            val firstYearColumn = cursor.getColumnIndexOrThrow(Albums.FIRST_YEAR)
            val numberOfSongsColumn = cursor.getColumnIndexOrThrow(Albums.NUMBER_OF_SONGS)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val title = cursor.getString(titleColumn)
                val artistId = cursor.getLong(artistIdColumn)
                val artist = cursor.getString(artistColumn)

                val albumCover: Uri = Uri.parse("content://media/external/audio/albumart")
                val albumCoverUri: Uri = ContentUris.withAppendedId(albumCover, id)

                val firstYear = cursor.getLong(firstYearColumn)
                val numberOfSongs = cursor.getLong(numberOfSongsColumn)

                albumList.add(
                    Album(
                        albumId = id,
                        albumTitle = title,
                        artistId = artistId,
                        artist = artist,
                        albumCoverUri = albumCoverUri,
                        year = firstYear,
                        numberOfSongs = numberOfSongs,
                    )
                )
            }
        }
        return albumList
    }

    override suspend fun getAlbumList(): List<Album> = albumContentResolver()
}