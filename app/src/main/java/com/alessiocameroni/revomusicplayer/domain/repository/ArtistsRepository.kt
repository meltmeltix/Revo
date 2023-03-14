package com.alessiocameroni.revomusicplayer.domain.repository

import com.alessiocameroni.revomusicplayer.data.classes.Artist

interface ArtistsRepository {
    suspend fun getArtistList(): List<Artist>
}