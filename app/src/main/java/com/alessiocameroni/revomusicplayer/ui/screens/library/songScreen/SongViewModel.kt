package com.alessiocameroni.revomusicplayer.ui.screens.library.songScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.data.classes.SongEntity
import com.alessiocameroni.revomusicplayer.data.classes.SortingValues
import com.alessiocameroni.revomusicplayer.data.repository.SongsRepositoryImpl
import com.alessiocameroni.revomusicplayer.data.repository.SortingRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor(
    private val sortingRepositoryImpl: SortingRepositoryImpl,
    private val songsRepositoryImpl: SongsRepositoryImpl
): ViewModel() {
    val sortingType = mutableStateOf(0)
    val sortingOrder = mutableStateOf(0)
    var librarySongs = mutableStateListOf<SongEntity>()

    init {
        viewModelScope.launch {
            sortingRepositoryImpl.getSongSorting().collect {
                sortingType.value = it.type
                sortingOrder.value = it.order
            }
        }
        viewModelScope.launch {
            songsRepositoryImpl.fetchSongList().collect {
                librarySongs = it
            }
        }
    }

    // Preferences management
    fun setSortData(type: Int, order: Int) {
        viewModelScope.launch {
            sortingRepositoryImpl.setSongSorting(SortingValues(type, order))
        }
    }
}