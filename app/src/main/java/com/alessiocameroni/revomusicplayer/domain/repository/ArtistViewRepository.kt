package com.alessiocameroni.revomusicplayer.domain.repository

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.alessiocameroni.revomusicplayer.data.classes.ArtistAlbum
import com.alessiocameroni.revomusicplayer.data.classes.ArtistDetails
import com.alessiocameroni.revomusicplayer.data.classes.ArtistSongEntity
import kotlinx.coroutines.flow.Flow

interface ArtistViewRepository {
    suspend fun fetchArtistInfo(artistId: Long): Flow<ArtistDetails>
    suspend fun fetchAlbumList(artistId: Long): Flow<SnapshotStateList<ArtistAlbum>>
    suspend fun fetchSongList(artistId: Long): Flow<SnapshotStateList<ArtistSongEntity>>
}