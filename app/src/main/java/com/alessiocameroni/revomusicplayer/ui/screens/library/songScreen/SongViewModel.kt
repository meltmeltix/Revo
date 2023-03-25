package com.alessiocameroni.revomusicplayer.ui.screens.library.songScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.data.classes.ContentState
import com.alessiocameroni.revomusicplayer.data.classes.preferences.SortingOrder
import com.alessiocameroni.revomusicplayer.data.classes.preferences.SortingType
import com.alessiocameroni.revomusicplayer.data.classes.song.Song
import com.alessiocameroni.revomusicplayer.domain.repository.SongsRepository
import com.alessiocameroni.revomusicplayer.domain.repository.SortingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor(
    private val sortingRepository: SortingRepository,
    private val songsRepository: SongsRepository
): ViewModel() {
    val sortTypeList = listOf(
        SortingType.TITLE,
        SortingType.ARTIST,
        SortingType.ALBUM,
        SortingType.DURATION,
        SortingType.DATE_ADDED
    )

    private var _contentState: MutableStateFlow<ContentState> = MutableStateFlow(ContentState.LOADING)
    val contentState: StateFlow<ContentState> = _contentState

    var sortingType = sortingRepository.getSongSortType()
        .map { sortTypeList[it] }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), SortingType.TITLE)

    var sortingOrder = sortingRepository.getSongSortOrder()
        .map { SortingOrder.values()[it] }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), SortingOrder.ASCENDING)

    private var _songs: MutableStateFlow<List<Song>> = MutableStateFlow(emptyList())
    val songs: StateFlow<List<Song>> = sortingOrder
        .combine(sortingType) { order, type -> sortList(_songs.value, order, type) }
        .flowOn(Dispatchers.Default)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    init {
        viewModelScope.launch {
            var list: List<Song>
            withContext(Dispatchers.IO) { list = songsRepository.getSongList() }
            if (list.isNotEmpty()) { _contentState.value = ContentState.SUCCESS }
            else { _contentState.value = ContentState.FAILURE }
            _songs.value = list
        }
    }

    private fun sortList(
        list: List<Song>,
        order: SortingOrder,
        type: SortingType,
    ): List<Song> {
        return when(order) {
            SortingOrder.ASCENDING -> {
                when(type) {
                    SortingType.TITLE -> list.sortedBy { it.songTitle }
                    SortingType.ARTIST -> list.sortedBy { it.artist }
                    SortingType.ALBUM -> list.sortedBy { it.album }
                    SortingType.DURATION -> list.sortedBy { it.duration }
                    SortingType.DATE_ADDED -> list.sortedBy { it.dateAdded }
                    else -> { list.sortedBy { it.songTitle } }
                }
            }
            SortingOrder.DESCENDING -> {
                when(type) {
                    SortingType.TITLE -> list.sortedByDescending { it.songTitle }
                    SortingType.ARTIST -> list.sortedByDescending { it.artist }
                    SortingType.ALBUM -> list.sortedByDescending { it.album }
                    SortingType.DURATION -> list.sortedByDescending { it.duration }
                    SortingType.DATE_ADDED -> list.sortedByDescending { it.dateAdded }
                    else -> { list.sortedByDescending { it.songTitle } }
                }
            }
        }
    }

    fun setSortType(type: SortingType) {
        viewModelScope.launch {
            sortingRepository.setSongSortType(sortTypeList.indexOf(type))
            onSort()
        }
    }

    fun setSortOrder(order: SortingOrder) {
        viewModelScope.launch {
            sortingRepository.setSongSortOrder(SortingOrder.values().indexOf(order))
            onSort()
        }
    }

    private fun onSort() {
        viewModelScope.launch {
            var list = _songs.value
            withContext(Dispatchers.IO) {
                list = sortList(list, sortingOrder.value, sortingType.value)
            }
            _songs.value = list
        }
    }
}