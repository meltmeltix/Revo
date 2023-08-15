package com.meltix.revo.data.classes.playlist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlists")
data class Playlist(
    val title: String,
    val icon: String,
    val numberOfTracks: String,
    val duration: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
)
