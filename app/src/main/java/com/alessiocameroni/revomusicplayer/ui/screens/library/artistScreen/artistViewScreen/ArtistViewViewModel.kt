package com.alessiocameroni.revomusicplayer.ui.screens.library.artistScreen.artistViewScreen

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.data.classes.ArtistAlbum
import com.alessiocameroni.revomusicplayer.data.classes.ArtistSongEntity
import com.alessiocameroni.revomusicplayer.data.classes.SortingValues
import com.alessiocameroni.revomusicplayer.domain.repository.ArtistViewRepository
import com.alessiocameroni.revomusicplayer.domain.repository.SortingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistViewViewModel @Inject constructor(
    private val sortingRepository: SortingRepository,
    private val albumViewRepository: ArtistViewRepository
): ViewModel() {
    var sortingType = mutableStateOf(0)
    var sortingOrder = mutableStateOf(0)

    var artist = mutableStateOf("Artist Name")
    var artistPictureUri = mutableStateOf<Uri?>(null)
    var numberOfAlbums = mutableStateOf(0)
    var numberOfTracks = mutableStateOf(0)

    var albumList = mutableListOf<ArtistAlbum>()
    var songList = mutableListOf<ArtistSongEntity>()

    init {
        viewModelScope.launch {
            sortingRepository.getArtistSongsSorting().collect {
                sortingType.value = it.type
                sortingOrder.value = it.order
            }
        }
    }

    // Artist, album, song initialization
    fun initializeArtistDetails(artistId: Long) {
        viewModelScope.launch {
            albumViewRepository.fetchArtistInfo(artistId).collect { details ->
                artist.value = details.artist
                numberOfAlbums.value = details.numberOfAlbums
                numberOfTracks.value = details.numberOfTracks
            }
        }
    }

    fun initializeAlbumList(artistId: Long) {
        viewModelScope.launch {
            albumViewRepository.fetchAlbumList(artistId).collect {
                albumList = it
                artistPictureUri.value = it[0].albumCoverUri
            }
        }
    }

    fun initializeSongList(artistId: Long) {
        viewModelScope.launch {
            albumViewRepository.fetchSongList(artistId).collect {
                songList = it
            }
        }
    }

    // Preferences management
    fun setSortData(type: Int, order: Int) {
        viewModelScope.launch {
            sortingRepository.setArtistSongsSorting(SortingValues(type, order))
        }
    }
}