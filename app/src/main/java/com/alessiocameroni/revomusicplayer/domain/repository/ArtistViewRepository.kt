package com.alessiocameroni.revomusicplayer.domain.repository

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.alessiocameroni.revomusicplayer.data.classes.ArtistAlbum
import com.alessiocameroni.revomusicplayer.data.classes.ArtistDetails
import com.alessiocameroni.revomusicplayer.data.classes.ArtistSongEntity
import kotlinx.coroutines.flow.Flow

interface ArtistViewRepository {
    suspend fun getArtistDetails(artistId: Long): Flow<ArtistDetails>
    suspend fun getAlbumList(artistId: Long): Flow<SnapshotStateList<ArtistAlbum>>
    suspend fun getSongList(artistId: Long): Flow<SnapshotStateList<ArtistSongEntity>>
}