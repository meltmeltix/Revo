package com.alessiocameroni.revomusicplayer.data.repository

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Albums
import android.provider.MediaStore.Audio.Media
import androidx.annotation.WorkerThread
import com.alessiocameroni.revomusicplayer.data.classes.album.AlbumDetails
import com.alessiocameroni.revomusicplayer.data.classes.album.AlbumSong
import com.alessiocameroni.revomusicplayer.domain.repository.AlbumViewRepository
import com.alessiocameroni.revomusicplayer.util.functions.calculateSongDuration

class AlbumViewRepositoryImpl(
    private val context: Context
): AlbumViewRepository {
    private companion object {
        val albumDetailsCollection: Uri =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Albums.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } else {
                Albums.EXTERNAL_CONTENT_URI
            }
        val albumDetailsProjection = arrayOf(
            Albums.ALBUM,
            Media.ARTIST_ID,
            Albums.ARTIST,
        )

        val songsCollection: Uri =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } else {
                Media.EXTERNAL_CONTENT_URI
            }
        val songsProjection = arrayOf(
            Media._ID,
            Media.DURATION,
            Media.TITLE,
            Media.TRACK,
        )
    }

    @WorkerThread
    private fun albumDetailsContentResolver(albumId: Long): AlbumDetails {
        var albumDetails = AlbumDetails(
            title = "Album Title",
            artistId = 0,
            artistName = "Artist Name",
            coverUri = null
        )

        val selection = "${Albums._ID} = $albumId"
        val mCursor = context.contentResolver.query(
            albumDetailsCollection,
            albumDetailsProjection,
            selection,
            null,
            null
        )

        mCursor?.use { cursor ->
            val albumColumn = cursor.getColumnIndexOrThrow(Albums.ALBUM)
            val artistIdColumn = cursor.getColumnIndexOrThrow(Media.ARTIST_ID)
            val artistColumn = cursor.getColumnIndexOrThrow(Albums.ARTIST)

            if(cursor.moveToFirst()) {
                val album = cursor.getString(albumColumn)
                val artistId = cursor.getLong(artistIdColumn)
                val artist = cursor.getString(artistColumn)
                val albumCover: Uri = Uri.parse("content://media/external/audio/albumart")
                val albumCoverUri: Uri = ContentUris.withAppendedId(albumCover, albumId)

                albumDetails = AlbumDetails(
                    title = album,
                    artistId = artistId,
                    artistName = artist,
                    coverUri = albumCoverUri
                )
            }
        }

        return albumDetails
    }

    @WorkerThread
    private fun songContentResolver(albumId: Long): List<AlbumSong> {
        val songList = mutableListOf<AlbumSong>()

        val selection = "${Media.IS_MUSIC} != 0 AND ${Media.ALBUM_ID} = $albumId"
        val sortOrder = "${Media.TITLE} ASC"
        val mCursor = context.contentResolver.query(
            songsCollection,
            songsProjection,
            selection,
            null,
            sortOrder
        )

        mCursor?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(Media._ID)
            val trackColumn = cursor.getColumnIndexOrThrow(Media.TRACK)
            val titleColumn = cursor.getColumnIndexOrThrow(Media.TITLE)
            val durationColumn = cursor.getColumnIndexOrThrow(Media.DURATION)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val contentUri: Uri = ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, id)
                val title = cursor.getString(titleColumn)
                val track = cursor.getInt(trackColumn)
                val duration = cursor.getInt(durationColumn)
                val fixedDuration = calculateSongDuration(duration)

                songList.add(
                    AlbumSong(
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

    override suspend fun getAlbumDetails(albumId: Long):
            AlbumDetails = albumDetailsContentResolver(albumId)

    override suspend fun getSongList(albumId: Long):
            List<AlbumSong> = songContentResolver(albumId)

}