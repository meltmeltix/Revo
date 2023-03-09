package com.alessiocameroni.revomusicplayer.data.repository

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Artists
import android.provider.MediaStore.Audio.Media
import androidx.annotation.WorkerThread
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.alessiocameroni.revomusicplayer.data.classes.ArtistAlbum
import com.alessiocameroni.revomusicplayer.data.classes.ArtistDetails
import com.alessiocameroni.revomusicplayer.data.classes.ArtistSongEntity
import com.alessiocameroni.revomusicplayer.domain.repository.ArtistViewRepository
import com.alessiocameroni.revomusicplayer.util.functions.calculateSongDuration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ArtistViewRepositoryImpl(
    private val context: Context
): ArtistViewRepository {
    private companion object {
        val collection: Uri =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } else {
                Media.EXTERNAL_CONTENT_URI
            }

        val artistCollection: Uri =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Artists.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } else {
                Artists.EXTERNAL_CONTENT_URI
            }
        val artistProjection = arrayOf(
            Artists.ARTIST,
            Artists.NUMBER_OF_ALBUMS,
            Artists.NUMBER_OF_TRACKS
        )

        val albumProjection = arrayOf(
            Media.ALBUM_ID,
            Media.ALBUM,
            Media.ARTIST_ID
        )

        val songProjection = arrayOf(
            Media._ID,
            Media.DURATION,
            Media.TITLE,
            Media.TRACK,
            Media.ALBUM_ID,
            Media.ALBUM,
        )
    }

    @WorkerThread
    private fun artistDetailsContentResolver(artistId: Long): ArtistDetails {
        var artistDetails = ArtistDetails(
            "Artist Name",
            0,
            0
        )

        val selection = "${Artists._ID} = $artistId"
        val mCursor = context.contentResolver.query(
            artistCollection,
            artistProjection,
            selection,
            null,
            null
        )

        mCursor?.use {  cursor ->
            val artistColumn = cursor.getColumnIndexOrThrow(Artists.ARTIST)
            val artistsNumberOfAlbumsColumn = cursor.getColumnIndexOrThrow(Artists.NUMBER_OF_ALBUMS)
            val artistNumberOfTracksColumn = cursor.getColumnIndexOrThrow(Artists.NUMBER_OF_TRACKS)

            cursor.moveToFirst()

            artistDetails = ArtistDetails(
                cursor.getString(artistColumn),
                cursor.getInt(artistsNumberOfAlbumsColumn),
                cursor.getInt(artistNumberOfTracksColumn)
            )
        }

        return artistDetails
    }

    @WorkerThread
    private fun albumContentResolver(artistId: Long): SnapshotStateList<ArtistAlbum> {
        val albumList = mutableStateListOf<ArtistAlbum>()

        val selection = "${Media.IS_MUSIC} != 0 AND ${Media.ARTIST_ID} = $artistId"
        val sortOrder = "${Media.ALBUM} ASC"
        val mCursor = context.contentResolver.query(
            collection,
            albumProjection,
            selection,
            null,
            sortOrder
        )

        mCursor?.use { cursor ->
            val albumIdColumn = cursor.getColumnIndexOrThrow(Media.ALBUM_ID)
            val albumTitleColumn = cursor.getColumnIndexOrThrow(Media.ALBUM)

            while (cursor.moveToNext()) {
                val albumId = cursor.getLong(albumIdColumn)
                val albumTitle = cursor.getString(albumTitleColumn)
                val albumCover: Uri = Uri.parse("content://media/external/audio/albumart")
                val albumCoverUri: Uri = ContentUris.withAppendedId(albumCover, albumId)

                if(
                    !albumList.contains(ArtistAlbum(albumId, albumTitle, albumCoverUri))
                ) {
                    albumList.add(ArtistAlbum(albumId, albumTitle, albumCoverUri))
                }
            }
        }

        return albumList
    }

    @WorkerThread
    private fun songContentResolver(artistId: Long): SnapshotStateList<ArtistSongEntity> {
        val songList = mutableStateListOf<ArtistSongEntity>()

        val selection = "${Media.IS_MUSIC} != 0 AND ${Media.ARTIST_ID} = $artistId"
        val sortOrder = "${Media.TITLE} ASC"
        val mCursor = context.contentResolver.query(
            collection,
            songProjection,
            selection,
            null,
            sortOrder
        )

        mCursor?.use {  cursor ->
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

                songList.add(
                    ArtistSongEntity(
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

        return songList
    }

    override suspend fun fetchArtistInfo(artistId: Long): Flow<ArtistDetails> =
        flow {
            val artistDetails = artistDetailsContentResolver(artistId)
            emit(artistDetails)
        }

    override suspend fun fetchAlbumList(artistId: Long):
        Flow<SnapshotStateList<ArtistAlbum>> = flow {
            val list = albumContentResolver(artistId)
            emit(list)
        }

    override suspend fun fetchSongList(artistId: Long):
        Flow<SnapshotStateList<ArtistSongEntity>> = flow {
            val list = songContentResolver(artistId)
            emit(list)
        }
}