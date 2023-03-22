package com.alessiocameroni.revomusicplayer.domain.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.alessiocameroni.revomusicplayer.data.classes.playlist.Playlist

@Dao
interface PlaylistsDao {

    @Upsert
    suspend fun upsertPlaylist(playlist: Playlist)

    @Delete
    suspend fun deletePlaylist(playlist: Playlist)

    @Query("SELECT * FROM playlists ORDER BY id")
    fun getPlaylists(): List<Playlist>

}