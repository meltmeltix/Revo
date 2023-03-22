package com.alessiocameroni.revomusicplayer.domain.repository

import com.alessiocameroni.revomusicplayer.data.classes.artist.Artist

interface ArtistsRepository {
    suspend fun getArtistList(): List<Artist>
}