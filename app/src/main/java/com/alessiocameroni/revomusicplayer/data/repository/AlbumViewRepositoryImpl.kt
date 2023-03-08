package com.alessiocameroni.revomusicplayer.data.repository

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media
import androidx.annotation.WorkerThread
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.alessiocameroni.revomusicplayer.data.classes.AlbumDetails
import com.alessiocameroni.revomusicplayer.data.classes.AlbumSongEntity
import com.alessiocameroni.revomusicplayer.domain.repository.AlbumViewRepository
import com.alessiocameroni.revomusicplayer.util.functions.calculateSongDuration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AlbumViewRepositoryImpl(
    private val context: Context
): AlbumViewRepository {
    private companion object {
        val collection: Uri =
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
            Media.ALBUM,
            Media.ARTIST_ID,
            Media.ARTIST,
        )

        var albumCoverUri: Uri? = null
        var albumTitle: String = "Album Title"
        var artistId: Long = 0
        var artist: String = "Artist Name"
    }

    @WorkerThread
    private fun songContentResolver(albumId: Long): SnapshotStateList<AlbumSongEntity> {
        val songList = mutableStateListOf<AlbumSongEntity>()
        var gotAlbumDetails = false

        val selection =
            "${Media.IS_MUSIC} != 0 AND ${Media.ALBUM_ID} = $albumId"
        val sortOrder = "${Media.TITLE} ASC"
        val mCursor = context.contentResolver.query(
            collection,
            projection,
            selection,
            null,
            sortOrder
        )

        mCursor?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(Media._ID)
            val trackColumn = cursor.getColumnIndexOrThrow(Media.TRACK)
            val titleColumn = cursor.getColumnIndexOrThrow(Media.TITLE)
            val durationColumn = cursor.getColumnIndexOrThrow(Media.DURATION)
            val albumTitleColumn = cursor.getColumnIndexOrThrow(Media.ALBUM)
            val artistIdColumn = cursor.getColumnIndexOrThrow(Media.ARTIST_ID)
            val artistColumn = cursor.getColumnIndexOrThrow(Media.ARTIST)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val contentUri: Uri = ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, id)
                val title = cursor.getString(titleColumn)
                val track = cursor.getInt(trackColumn)
                val duration = cursor.getInt(durationColumn)
                val fixedDuration = calculateSongDuration(duration)

                if(!gotAlbumDetails) {
                    albumTitle = cursor.getString(albumTitleColumn)
                    artistId = cursor.getLong(artistIdColumn)
                    artist = cursor.getString(artistColumn)
                    val albumCover: Uri = Uri.parse("content://media/external/audio/albumart")
                    albumCoverUri = ContentUris.withAppendedId(albumCover, albumId)

                    gotAlbumDetails = true
                }

                songList.add(
                    AlbumSongEntity(
                        id,
                        contentUri,
                        track,
                        title,
                        duration,
                        fixedDuration
                    )
                )
            }
        }

        return songList
    }

    override suspend fun fetchSongList(albumId: Long):
        Flow<SnapshotStateList<AlbumSongEntity>> = flow {
            val list = songContentResolver(albumId)
            emit(list)
        }

    override suspend fun fetchAlbumDetails(albumId: Long): Flow<AlbumDetails> = flow {
        emit(
            AlbumDetails(
                albumTitle,
                artistId,
                artist,
                albumCoverUri
            )
        )
    }
}