package com.alessiocameroni.revomusicplayer.data.repository

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Albums
import android.provider.MediaStore.Audio.Media
import androidx.annotation.WorkerThread
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.alessiocameroni.revomusicplayer.data.classes.AlbumData
import com.alessiocameroni.revomusicplayer.domain.repository.AlbumsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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
        val sortOrder = "${Media.ALBUM} ASC"
    }

    @WorkerThread
    private fun albumContentResolver(): SnapshotStateList<AlbumData> {
        val albumList = mutableStateListOf<AlbumData>()

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
                    AlbumData(
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

    override suspend fun fetchAlbumRepository(): Flow<SnapshotStateList<AlbumData>> =
        flow {
            val list: SnapshotStateList<AlbumData> = albumContentResolver()
            emit(list)
        }
}