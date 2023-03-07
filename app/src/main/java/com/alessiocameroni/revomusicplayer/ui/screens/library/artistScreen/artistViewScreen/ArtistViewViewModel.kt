package com.alessiocameroni.revomusicplayer.ui.screens.library.artistScreen.artistViewScreen

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.data.classes.ArtistAlbumEntity
import com.alessiocameroni.revomusicplayer.data.classes.ArtistSongEntity
import com.alessiocameroni.revomusicplayer.data.classes.SortingValues
import com.alessiocameroni.revomusicplayer.data.repository.ArtistViewRepositoryImpl
import com.alessiocameroni.revomusicplayer.data.repository.SortingRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistViewViewModel @Inject constructor(
    private val sortingRepositoryImpl: SortingRepositoryImpl,
    private val albumViewRepositoryImpl: ArtistViewRepositoryImpl
): ViewModel() {
    var sortingType = mutableStateOf(0)
    var sortingOrder = mutableStateOf(0)

    var artist = mutableStateOf("Artist Name")
    var artistPictureUri = mutableStateOf<Uri?>(null)
    var numberOfAlbums = mutableStateOf(0)
    var numberOfTracks = mutableStateOf(0)

    var albumList = mutableListOf<ArtistAlbumEntity>()
    var songList = mutableListOf<ArtistSongEntity>()

    init {
        viewModelScope.launch {
            sortingRepositoryImpl.getArtistSongsSorting().collect {
                sortingType.value = it.type
                sortingOrder.value = it.order
            }
        }
    }

    // Artist, album, song initialization
    fun initializeArtistDetails(artistId: Long) {
        viewModelScope.launch {
            albumViewRepositoryImpl.fetchArtistInfo(artistId).collect { details ->
                artist.value = details.artist
                numberOfAlbums.value = details.numberOfAlbums
                numberOfTracks.value = details.numberOfArtists
            }
        }
    }

    fun initializeAlbumList(artistId: Long) {
        viewModelScope.launch {
            albumViewRepositoryImpl.fetchAlbumList(artistId).collect {
                albumList = it
                artistPictureUri.value = it[0].albumCoverUri
            }
        }
    }

    fun initializeSongList(artistId: Long) {
        viewModelScope.launch {
            albumViewRepositoryImpl.fetchSongList(artistId).collect {
                songList = it
            }
        }
    }

    // Preferences management
    fun setSortData(type: Int, order: Int) {
        viewModelScope.launch {
            sortingRepositoryImpl.setArtistSongsSorting(SortingValues(type, order))
        }
    }
}