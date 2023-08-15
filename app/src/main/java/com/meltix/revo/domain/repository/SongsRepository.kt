package com.meltix.revo.domain.repository

import com.meltix.revo.data.classes.song.Song

interface SongsRepository {
    suspend fun getSongList(): List<Song>
}