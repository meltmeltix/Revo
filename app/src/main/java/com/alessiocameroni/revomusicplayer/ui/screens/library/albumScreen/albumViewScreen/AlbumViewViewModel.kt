package com.alessiocameroni.revomusicplayer.ui.screens.library.albumScreen.albumViewScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.data.classes.ContentState
import com.alessiocameroni.revomusicplayer.data.classes.album.AlbumDetails
import com.alessiocameroni.revomusicplayer.data.classes.album.AlbumDuration
import com.alessiocameroni.revomusicplayer.data.classes.album.AlbumSong
import com.alessiocameroni.revomusicplayer.data.classes.preferences.SortingOrder
import com.alessiocameroni.revomusicplayer.data.classes.preferences.SortingType
import com.alessiocameroni.revomusicplayer.domain.repository.AlbumViewRepository
import com.alessiocameroni.revomusicplayer.domain.repository.SortingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class AlbumViewViewModel @Inject constructor(
    private val sortingRepository: SortingRepository,
    private val albumViewRepository: AlbumViewRepository
): ViewModel() {
    val sortTypeList = listOf(
        SortingType.TRACK,
        SortingType.TITLE,
        SortingType.DURATION
    )

    private val _contentState: MutableStateFlow<ContentState> = MutableStateFlow(ContentState.LOADING)
    val contentState: StateFlow<ContentState> = _contentState

    val sortingType = sortingRepository.getAlbumSongsSortType()
        .map { sortTypeList[it] }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), SortingType.TITLE)

    val sortingOrder = sortingRepository.getAlbumSongsSortOrder()
        .map { SortingOrder.values()[it] }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), SortingOrder.ASCENDING)

    private val _songs: MutableStateFlow<List<AlbumSong>> = MutableStateFlow(emptyList())
    val songs: StateFlow<List<AlbumSong>> =
        combine(_songs, sortingOrder, sortingType) {
            list, order, type -> sortList(list, order, type)
        }
        .flowOn(Dispatchers.Default)
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _albumDetails: MutableStateFlow<AlbumDetails> = MutableStateFlow(
        AlbumDetails("Unknown Album", 0, "Unknown Artist", null)
    )
    val albumDetails: StateFlow<AlbumDetails> = _albumDetails

    val albumDuration: StateFlow<AlbumDuration> = _songs
        .map { list -> calculateAlbumDuration(list.sumOf { it.duration }) }
        .stateIn(viewModelScope, SharingStarted.Lazily, AlbumDuration(0, 0, 0, 0))

    fun initializeAlbumData(albumId: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _songs.value = albumViewRepository.getSongList(albumId)
                _albumDetails.value = albumViewRepository.getAlbumDetails(albumId)
            }
            if (_songs.value.isNotEmpty()) { _contentState.value = ContentState.SUCCESS }
            else { _contentState.value = ContentState.FAILURE }
        }
    }

    private fun calculateAlbumDuration(duration: Int): AlbumDuration {
        val fixedDuration: Double = duration.toDouble() / 1000

        val hours: Double = fixedDuration / 3600
        var minutes: Double = (hours - hours.toInt()) * 60
        var seconds: Int = ((minutes - minutes.toInt()) * 60).roundToInt()
        when (seconds) {
            60 -> {
                seconds = 0
                minutes++
            }
        }

        return AlbumDuration(
            duration,
            hours.toInt(),
            minutes.toInt(),
            seconds
        )
    }

    private fun sortList(
        list: List<AlbumSong>,
        order: SortingOrder,
        type: SortingType,
    ): List<AlbumSong> {
        return when(order) {
            SortingOrder.ASCENDING -> {
                when(type) {
                    SortingType.TRACK -> list.sortedBy { it.track }
                    SortingType.TITLE -> list.sortedBy { it.songTitle }
                    SortingType.DURATION -> list.sortedBy { it.duration }
                    else -> { list.sortedBy { it.track } }
                }
            }
            SortingOrder.DESCENDING -> {
                when(type) {
                    SortingType.TRACK -> list.sortedByDescending { it.track }
                    SortingType.TITLE -> list.sortedByDescending { it.songTitle }
                    SortingType.DURATION -> list.sortedByDescending { it.duration }
                    else -> { list.sortedByDescending { it.track } }
                }
            }
        }
    }

    fun setSortType(type: SortingType) {
        viewModelScope.launch {
            sortingRepository.setAlbumSongsSortType(sortTypeList.indexOf(type))
            onSort()
        }
    }

    fun setSortOrder(order: SortingOrder) {
        viewModelScope.launch {
            sortingRepository.setAlbumSongsSortOrder(SortingOrder.values().indexOf(order))
            onSort()
        }
    }

    private fun onSort() {
        viewModelScope.launch(Dispatchers.Default) {
            _songs.value = sortList(_songs.value, sortingOrder.value, sortingType.value)
        }
    }
}