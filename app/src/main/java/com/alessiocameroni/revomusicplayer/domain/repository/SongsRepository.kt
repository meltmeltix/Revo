package com.alessiocameroni.revomusicplayer.domain.repository

import com.alessiocameroni.revomusicplayer.data.classes.song.Song

interface SongsRepository {
    suspend fun getSongList(): List<Song>
}