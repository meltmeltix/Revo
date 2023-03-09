package com.alessiocameroni.revomusicplayer.data.classes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlists")
data class Playlist(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val numberOfTracks: String,
    val duration: Int
)
