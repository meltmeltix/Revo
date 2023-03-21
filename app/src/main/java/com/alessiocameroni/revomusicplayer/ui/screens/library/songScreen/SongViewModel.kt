package com.alessiocameroni.revomusicplayer.ui.screens.library.songScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.data.classes.Song
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
    val songs: LiveData<List<Song>> = _songs

    init {

    }

    // List management
    private fun sortList(
        list: List<Song>,
        type: Int,
        order: Int,
    ): List<Song> {
        var sortedList = list
        sortedList = when(order) {
            0 -> {
                when(type) {
                    0 -> sortedList.sortedBy { it.songTitle }
                    1 -> sortedList.sortedBy { it.artist }
                    2 -> sortedList.sortedBy { it.album }
                    3 -> sortedList.sortedBy { it.duration }
                    4 -> sortedList.sortedBy { it.dateAdded }
                    else -> { sortedList.sortedBy { it.songTitle } }
                }
            }
            1 -> {
                when(type) {
                    0 -> sortedList.sortedByDescending { it.songTitle }
                    1 -> sortedList.sortedByDescending { it.artist }
                    2 -> sortedList.sortedByDescending { it.album }
                    3 -> sortedList.sortedByDescending { it.duration }
                    4 -> sortedList.sortedByDescending { it.dateAdded }
                    else -> { sortedList.sortedByDescending { it.songTitle } }
                }
            }
            else -> { sortedList.sortedBy { it.songTitle } }
        }
        return sortedList
    }

    private suspend fun onSortChange(type: Int, order: Int) {
        var list = _songs.value!!
        withContext(Dispatchers.Default) { list = sortList(list, type, order) }
        _songs.value = list
    }

    // Preferences management
    fun setSortData(type: Int, order: Int) {
        viewModelScope.launch {

        }
    }
}