package com.alessiocameroni.revomusicplayer.ui.screens.library.albumScreen.albumViewScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.data.classes.AlbumDetails
import com.alessiocameroni.revomusicplayer.data.classes.AlbumSong
import com.alessiocameroni.revomusicplayer.data.classes.SortingValues
import com.alessiocameroni.revomusicplayer.domain.repository.AlbumViewRepository
import com.alessiocameroni.revomusicplayer.domain.repository.SortingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class AlbumViewViewModel @Inject constructor(
    private val sortingRepository: SortingRepository,
    private val albumViewRepository: AlbumViewRepository
): ViewModel() {
    var sortingType = mutableStateOf(0)
    var sortingOrder = mutableStateOf(0)

    private var _details: MutableLiveData<AlbumDetails> = MutableLiveData(
        AlbumDetails(
            title = "Unknown Album",
            artistId = 0,
            artistName = "Unknown Artist",
            coverUri = null
        )
    )
    val details = _details

    var albumHoursAmount = mutableStateOf(0)
    var albumMinutesAmount = mutableStateOf(0)
    var albumSecondsAmount = mutableStateOf(0)

    private var _songs: MutableLiveData<List<AlbumSong>> = MutableLiveData(emptyList())
    val songs = _songs

    init {
        viewModelScope.launch {
            sortingRepository.getAlbumSongsSorting().collect {
                sortingType.value = it.type
                sortingOrder.value = it.order
            }
        }
    }

    // List and album details fetching
    fun getAlbumDetails(albumId: Long) {
        viewModelScope.launch {
            val details: AlbumDetails
            withContext(Dispatchers.IO) { details = albumViewRepository.getAlbumDetails(albumId) }
            _details.value = details
        }
    }

    fun initializeSongList(albumId: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            var list: List<AlbumSong>
            withContext(Dispatchers.IO) { list = albumViewRepository.getSongList(albumId) }
            withContext(Dispatchers.Default) {
                list = sortList(
                    list,
                    sortingType.value,
                    sortingOrder.value
                )
            }
            calculateAlbumDuration(list.sumOf { it.duration })
            _songs.value = list
        }
    }

    // List and data management
    private fun calculateAlbumDuration(duration: Int?) {
        val fixedDuration: Double = (duration ?: 0).toDouble() / 1000

        val hours: Double = fixedDuration / 3600
        var minutes: Double = (hours - hours.toInt()) * 60
        var seconds: Int = ((minutes - minutes.toInt()) * 60).roundToInt()
        when (seconds) {
            60 -> {
                seconds = 0
                minutes++
            }
        }

        albumHoursAmount.value = hours.toInt()
        albumMinutesAmount.value = minutes.toInt()
        albumSecondsAmount.value = seconds
    }

    private fun sortList(
        list: List<AlbumSong>,
        type: Int,
        order: Int
    ): List<AlbumSong> {
        var sortedList = list
        sortedList = when(order) {
            0 -> {
                when(type) {
                    0 -> sortedList.sortedBy { it.track }
                    1 -> sortedList.sortedBy { it.songTitle }
                    2 -> sortedList.sortedBy { it.duration }
                    else -> { sortedList.sortedBy { it.track } }
                }
            }
            1 -> {
                when(type) {
                    0 -> sortedList.sortedByDescending { it.track }
                    1 -> sortedList.sortedByDescending { it.songTitle }
                    2 -> sortedList.sortedByDescending { it.duration }
                    else -> { sortedList.sortedByDescending { it.track } }
                }
            }
            else -> { sortedList.sortedBy { it.track } }
        }
        return sortedList
    }

    private fun onSortChange(type: Int, order: Int) {
        var list = _songs.value!!
        list = sortList(list, type, order)
        _songs.value = list
    }

    // Preferences management
    fun setSortData(type: Int, order: Int) {
        viewModelScope.launch {
            sortingRepository.setAlbumSongsSorting(SortingValues(type, order))
            onSortChange(type, order)
        }
    }
}