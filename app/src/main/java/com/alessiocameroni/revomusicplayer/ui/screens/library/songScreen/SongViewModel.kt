package com.alessiocameroni.revomusicplayer.ui.screens.library.songScreen

import android.util.Log
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
            withContext(Dispatchers.IO) {
                list = songsRepository.fetchSongList()
            }
            _songs.value = list
            sortList(sortingType.value, sortingOrder.value)
        }
    }

    // List management
    fun sortList(
        sortType: Int,
        sortOrder: Int,
    ) {
        var list = _songs.value!!
        list = when(sortOrder) {
            0 -> {
                when(sortType) {
                    0 -> list.sortedBy { it.songTitle }
                    1 -> list.sortedBy { it.artist }
                    2 -> list.sortedBy { it.album }
                    3 -> list.sortedBy { it.duration }
                    4 -> list.sortedBy { it.dateAdded }
                    else -> { list.sortedBy { it.songTitle } }
                }
            }
            1 -> {
                when(sortType) {
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
        Log.d("sortListNoReturn", list[0].songTitle)
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