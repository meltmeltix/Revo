package com.meltix.revo.ui.screens.library.songScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meltix.revo.data.classes.ContentState
import com.meltix.revo.data.classes.song.Song
import com.meltix.revo.data.classes.sorting.SortingOrder
import com.meltix.revo.data.classes.sorting.SortingType
import com.meltix.revo.domain.repository.SongsRepository
import com.meltix.revo.domain.repository.SortingRepository
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

    private val _contentState: MutableStateFlow<ContentState> = MutableStateFlow(ContentState.LOADING)
    val contentState: StateFlow<ContentState> = _contentState

    val sortingType = sortingRepository.getSongSortType()
        .map { sortTypeList[it] }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), SortingType.TITLE)

    val sortingOrder = sortingRepository.getSongSortOrder()
        .map { SortingOrder.values()[it] }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), SortingOrder.ASCENDING)

    private val _songs: MutableStateFlow<List<Song>> = MutableStateFlow(emptyList())
    val songs: StateFlow<List<Song>> =
        combine(_songs, sortingOrder, sortingType) {
            list, order, type -> sortList(list, order, type)
        }
        .flowOn(Dispatchers.Default)
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { _songs.value = songsRepository.getSongList() }
            if (_songs.value.isNotEmpty()) { _contentState.value = ContentState.SUCCESS }
            else { _contentState.value = ContentState.FAILURE }
        }
    }
    
    fun onRefresh() {
        viewModelScope.launch {
            _contentState.value = ContentState.LOADING
            withContext(Dispatchers.IO) { _songs.value = songsRepository.getSongList() }
            if (_songs.value.isNotEmpty()) { _contentState.value = ContentState.SUCCESS }
            else { _contentState.value = ContentState.FAILURE }
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
        viewModelScope.launch(Dispatchers.Default) {
            _songs.value = sortList(_songs.value, sortingOrder.value, sortingType.value)
        }
    }
}