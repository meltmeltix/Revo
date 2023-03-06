package com.alessiocameroni.revomusicplayer.data.repository

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.WorkerThread
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.alessiocameroni.revomusicplayer.data.classes.ArtistData
import com.alessiocameroni.revomusicplayer.domain.repository.ArtistsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ArtistsRepositoryImpl(
    private val context: Context
): ArtistsRepository {
    private companion object {
        var mCursor: Cursor? = null
        val collection: Uri =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } else {
                MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI
            }
        val projection = arrayOf(
            MediaStore.Audio.Media.ARTIST_ID,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM_ID,
        )
        const val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"
        const val sortOrder = "${MediaStore.Audio.Media.ARTIST} ASC"
    }

    @WorkerThread
    private fun artistContentResolver(): SnapshotStateList<ArtistData> {
        val artistList = mutableStateListOf<ArtistData>()

        mCursor = context.contentResolver.query(
            collection,
            projection,
            selection,
            null,
            sortOrder
        )

        mCursor?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST_ID)
            val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val artist = cursor.getString(artistColumn)
                val albumId = cursor.getLong(albumIdColumn)

                val albumCover: Uri = Uri.parse("content://media/external/audio/albumart")
                val albumCoverUri: Uri = ContentUris.withAppendedId(albumCover, albumId)

                if(!artistList.any { it.artistId == id && it.artist == artist } ) {
                    artistList.add(
                        ArtistData(id, artist, albumCoverUri)
                    )
                }

            }
        }

        return artistList
    }

    override suspend fun fetchArtistsRepository(): Flow<SnapshotStateList<ArtistData>> =
        flow {
            val list = artistContentResolver()
            emit(list)
        }
}