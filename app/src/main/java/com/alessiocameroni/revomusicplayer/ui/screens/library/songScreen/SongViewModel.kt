package com.alessiocameroni.revomusicplayer.ui.screens.library.songScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.data.classes.SongEntity
import com.alessiocameroni.revomusicplayer.data.classes.SortingValues
import com.alessiocameroni.revomusicplayer.domain.repository.SongsRepository
import com.alessiocameroni.revomusicplayer.domain.repository.SortingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor(
    private val sortingRepository: SortingRepository,
    private val songsRepository: SongsRepository
): ViewModel() {
    val sortingType = mutableStateOf(0)
    val sortingOrder = mutableStateOf(0)
    var librarySongs = mutableStateListOf<SongEntity>()

    init {
        viewModelScope.launch {
            sortingRepository.getSongSorting().collect {
                sortingType.value = it.type
                sortingOrder.value = it.order
            }
        }
        viewModelScope.launch {
            songsRepository.fetchSongList().collect {
                librarySongs = it
            }
        }
    }

    // Preferences management
    fun setSortData(type: Int, order: Int) {
        viewModelScope.launch {
            sortingRepository.setSongSorting(SortingValues(type, order))
        }
    }
}