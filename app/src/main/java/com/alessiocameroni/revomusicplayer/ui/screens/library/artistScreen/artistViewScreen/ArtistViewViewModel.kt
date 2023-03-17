package com.alessiocameroni.revomusicplayer.ui.screens.library.artistScreen.artistViewScreen

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.data.classes.ArtistAlbum
import com.alessiocameroni.revomusicplayer.data.classes.ArtistDetails
import com.alessiocameroni.revomusicplayer.data.classes.ArtistSong
import com.alessiocameroni.revomusicplayer.data.classes.SortingValues
import com.alessiocameroni.revomusicplayer.domain.repository.ArtistViewRepository
import com.alessiocameroni.revomusicplayer.domain.repository.SortingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ArtistViewViewModel @Inject constructor(
    private val sortingRepository: SortingRepository,
    private val artistViewRepository: ArtistViewRepository
): ViewModel() {
    var sortingType = mutableStateOf(0)
    var sortingOrder = mutableStateOf(0)

    private val _artistDetails: MutableLiveData<ArtistDetails> = MutableLiveData(
        ArtistDetails(
            artist = "Artist Name",
            numberOfAlbums = 0,
            numberOfTracks = 0,
        )
    )
    val artistDetails = _artistDetails

    private val _artistPicture: MutableLiveData<Uri?> = MutableLiveData(null)
    val artistPicture =  _artistPicture

    private val _albumList: MutableLiveData<List<ArtistAlbum>> = MutableLiveData(emptyList())
    val albumList = _albumList

    private val _songList: MutableLiveData<List<ArtistSong>> = MutableLiveData(emptyList())
    val songList = _songList

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
            val details: ArtistDetails
            withContext(Dispatchers.IO) { details = artistViewRepository.getArtistDetails(artistId) }
            _artistDetails.value = details
        }
    }

    fun initializeAlbumList(artistId: Long) {
        viewModelScope.launch {
            val list: List<ArtistAlbum>
            withContext(Dispatchers.IO) { list = artistViewRepository.getAlbumList(artistId) }
            if (list.isNotEmpty()) { _artistPicture.value = list[0].albumCoverUri }
            _albumList.value = list
        }
    }

    fun initializeSongList(artistId: Long) {
        viewModelScope.launch {
            var list: List<ArtistSong>
            withContext(Dispatchers.IO) { list = artistViewRepository.getSongList(artistId) }
            withContext(Dispatchers.Default) {
                list = sortList(
                    list,
                    sortingType.value,
                    sortingOrder.value
                )
            }
            _songList.value = list
        }
    }

    // List and data management
    private fun sortList(
        list: List<ArtistSong>,
        type: Int,
        order: Int,
    ): List<ArtistSong> {
        var sortedList = list
        sortedList = when(order) {
            0 -> {
                when(type) {
                    0 -> sortedList.sortedBy { it.songTitle }
                    1 -> sortedList.sortedBy { it.track }
                    2 -> sortedList.sortedBy { it.duration }
                    3 -> sortedList.sortedBy { it.album }
                    else -> { sortedList.sortedBy { it.songTitle } }
                }
            }
            1 -> {
                when(type) {
                    0 -> sortedList.sortedByDescending { it.songTitle }
                    1 -> sortedList.sortedByDescending { it.track }
                    2 -> sortedList.sortedByDescending { it.duration }
                    3 -> sortedList.sortedByDescending { it.album }
                    else -> { sortedList.sortedByDescending { it.songTitle } }
                }
            }
            else -> { sortedList.sortedBy { it.songTitle } }
        }
        return sortedList
    }

    private suspend fun onSortChanged(type: Int, order: Int) {
        var list = _songList.value!!
        withContext(Dispatchers.Default) { list = sortList(list, type, order) }
        _songList.value = list
    }

    // Preferences management
    fun setSortData(type: Int, order: Int) {
        viewModelScope.launch {
            sortingRepository.setArtistSongsSorting(SortingValues(type, order))
            onSortChanged(type, order)
        }
    }
}