package com.meltix.revo.domain.repository

import com.meltix.revo.data.classes.artist.Artist

interface ArtistsRepository {
    suspend fun getArtistList(): List<Artist>
}