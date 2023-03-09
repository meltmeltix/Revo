package com.alessiocameroni.revomusicplayer.ui.screens.library.albumScreen.albumViewScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.data.classes.AlbumDetails
import com.alessiocameroni.revomusicplayer.data.classes.AlbumSong
import com.alessiocameroni.revomusicplayer.data.classes.SortingValues
import com.alessiocameroni.revomusicplayer.domain.repository.AlbumViewRepository
import com.alessiocameroni.revomusicplayer.domain.repository.SortingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class AlbumViewViewModel @Inject constructor(
    private val sortingRepository: SortingRepository,
    private val albumViewRepository: AlbumViewRepository
): ViewModel() {
    var albumDetails = mutableStateOf(
        AlbumDetails(
            title = "Unknown Album",
            artistId = 0,
            artistName = "Unknown Artist",
            coverUri = null
        )
    )
    var albumHoursAmount = mutableStateOf(0)
    var albumMinutesAmount = mutableStateOf(0)
    var albumSecondsAmount = mutableStateOf(0)

    var sortingType = mutableStateOf(0)
    var sortingOrder = mutableStateOf(0)
    var songList = mutableStateListOf<AlbumSong>()

    init {
        viewModelScope.launch {
            sortingRepository.getAlbumSongsSorting().collect {
                sortingType.value = it.type
                sortingOrder.value = it.order
            }
        }
    }

    // Album fetching
    fun initializeSongList(albumId: Long) {
        viewModelScope.launch {
            albumViewRepository.fetchSongList(albumId).collect {
                songList = it
            }
            calculateAlbumDuration(songList.sumOf { it.duration })
        }
    }

    fun initializeAlbumDetails(albumId: Long) {
        viewModelScope.launch {
            albumViewRepository.fetchAlbumDetails(albumId).collect {
                albumDetails.value = it
            }
        }
    }

    private fun calculateAlbumDuration(duration: Int?) {
        val fixedDuration: Double = (duration ?: 0).toDouble() / 1000

        val hours: Double = fixedDuration / 3600
        var minutes: Double = (hours - hours.toInt()) * 60
        var seconds: Int = ((minutes - minutes.toInt()) * 60).roundToInt()
        when(seconds) {
            60 -> {
                seconds = 0
                minutes++
            }
        }

        albumHoursAmount.value = hours.toInt()
        albumMinutesAmount.value = minutes.toInt()
        albumSecondsAmount.value = seconds
    }

    // Preferences management
    fun setSortData(type: Int, order: Int) {
        viewModelScope.launch {
            sortingRepository.setAlbumSongsSorting(SortingValues(type, order))
        }
    }
}