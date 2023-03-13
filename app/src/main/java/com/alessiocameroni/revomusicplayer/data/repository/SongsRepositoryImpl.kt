package com.alessiocameroni.revomusicplayer.data.repository

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media
import androidx.annotation.WorkerThread
import com.alessiocameroni.revomusicplayer.data.classes.Song
import com.alessiocameroni.revomusicplayer.domain.repository.SongsRepository

class SongsRepositoryImpl(
    private val context: Context
): SongsRepository {
    private companion object {
        var mCursor: Cursor? = null
        val collection: Uri =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } else {
                Media.EXTERNAL_CONTENT_URI
            }
        val projection = arrayOf(
            Media._ID,
            Media.TITLE,
            Media.ALBUM_ID,
            Media.ALBUM,
            Media.ARTIST_ID,
            Media.ARTIST,
            Media.DURATION,
            Media.DATE_ADDED
        )
        const val selection = "${Media.IS_MUSIC} != 0"
        const val sortOrder = "${Media.DISPLAY_NAME} ASC"
    }

    @WorkerThread
    private fun songContentResolver(): List<Song> {
        val songList = mutableListOf<Song>()

        mCursor = context.contentResolver.query(
            collection,
            projection,
            selection,
            null,
            sortOrder
        )

        mCursor?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(Media._ID)
            val titleColumn = cursor.getColumnIndexOrThrow(Media.TITLE)
            val albumIdColumn = cursor.getColumnIndexOrThrow(Media.ALBUM_ID)
            val albumColumn = cursor.getColumnIndexOrThrow(Media.ALBUM)
            val artistIdColumn = cursor.getColumnIndexOrThrow(Media.ARTIST_ID)
            val artistColumn = cursor.getColumnIndexOrThrow(Media.ARTIST)
            val durationColumn = cursor.getColumnIndexOrThrow(Media.DURATION)
            val dateAddedColumn = cursor.getColumnIndexOrThrow(Media.DATE_ADDED)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val contentUri: Uri = ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, id)
                val title = cursor.getString(titleColumn)
                val artistId = cursor.getLong(artistIdColumn)
                val artist = cursor.getString(artistColumn)
                val albumId = cursor.getLong(albumIdColumn)
                val album = cursor.getString(albumColumn)

                val albumCover: Uri = Uri.parse("content://media/external/audio/albumart")
                val albumCoverUri: Uri = ContentUris.withAppendedId(albumCover, albumId)

                val duration = cursor.getInt(durationColumn)
                val dateAdded = cursor.getLong(dateAddedColumn)

                songList.add(
                    Song(
                        songId = id,
                        contentUri = contentUri,
                        songTitle = title,
                        artistId = artistId,
                        artist = artist,
                        albumId = albumId,
                        album = album,
                        albumCoverUri = albumCoverUri,
                        duration = duration,
                        dateAdded = dateAdded
                    )
                )
            }
        }
        return songList
    }

    override suspend fun fetchSongList(): List<Song> = songContentResolver()
}