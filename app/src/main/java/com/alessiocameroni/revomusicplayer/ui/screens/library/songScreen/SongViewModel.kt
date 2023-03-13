package com.alessiocameroni.revomusicplayer.ui.screens.library.songScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.data.classes.Song
import com.alessiocameroni.revomusicplayer.data.classes.SortingValues
import com.alessiocameroni.revomusicplayer.domain.repository.SongsRepository
import com.alessiocameroni.revomusicplayer.domain.repository.SortingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor(
    private val sortingRepository: SortingRepository,
    private val songsRepository: SongsRepository
): ViewModel() {
    val sortingType = mutableStateOf(0)
    val sortingOrder = mutableStateOf(0)
    val isListEmpty = mutableStateOf(false)
    private var _songs: MutableLiveData<List<Song>> = MutableLiveData(emptyList())
    val songs = _songs

    init {
        viewModelScope.launch {
            sortingRepository.getSongSorting().collect {
                sortingType.value = it.type
                sortingOrder.value = it.order
            }
        }
        viewModelScope.launch(Dispatchers.Main) {
            var list: List<Song>
            withContext(Dispatchers.IO) { list = songsRepository.fetchSongList() }
            _songs.value = list
            if (list.isNotEmpty()) { sortList(sortingType.value, sortingOrder.value) }
            else { isListEmpty.value = true }
        }
    }

    // List management
    private fun sortList(
        type: Int,
        order: Int,
    ) {
        var list = _songs.value!!
        list = when(order) {
            0 -> {
                when(type) {
                    0 -> list.sortedBy { it.songTitle }
                    1 -> list.sortedBy { it.artist }
                    2 -> list.sortedBy { it.album }
                    3 -> list.sortedBy { it.duration }
                    4 -> list.sortedBy { it.dateAdded }
                    else -> { list.sortedBy { it.songTitle } }
                }
            }
            1 -> {
                when(type) {
                    0 -> list.sortedByDescending { it.songTitle }
                    1 -> list.sortedByDescending { it.artist }
                    2 -> list.sortedByDescending { it.album }
                    3 -> list.sortedByDescending { it.duration }
                    4 -> list.sortedByDescending { it.dateAdded }
                    else -> { list.sortedByDescending { it.songTitle } }
                }
            }
            else -> { list.sortedBy { it.songTitle } }
        }
        _songs.value = list
    }

    // Preferences management
    fun setSortData(type: Int, order: Int) {
        viewModelScope.launch {
            sortingRepository.setSongSorting(SortingValues(type, order))
            sortList(type, order)
        }
    }
}