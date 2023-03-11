package com.alessiocameroni.revomusicplayer.domain.repository

import com.alessiocameroni.revomusicplayer.data.classes.Song

interface SongsRepository {
    suspend fun fetchSongList(): List<Song>
}