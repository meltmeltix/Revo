package com.alessiocameroni.revomusicplayer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alessiocameroni.revomusicplayer.data.classes.playlist.Playlist
import com.alessiocameroni.revomusicplayer.domain.database.PlaylistsDao

@Database(
    entities = [Playlist::class],
    version = 1
)

abstract class PlaylistsDatabase: RoomDatabase() {
    abstract val dao: PlaylistsDao
}