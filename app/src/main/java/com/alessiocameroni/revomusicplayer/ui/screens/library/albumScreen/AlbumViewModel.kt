package com.alessiocameroni.revomusicplayer.ui.screens.library.albumScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.data.classes.AlbumEntity
import com.alessiocameroni.revomusicplayer.data.classes.SortingValues
import com.alessiocameroni.revomusicplayer.domain.repository.AlbumsRepository
import com.alessiocameroni.revomusicplayer.domain.repository.SortingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val sortingRepository: SortingRepository,
    private val albumsRepository: AlbumsRepository
): ViewModel() {
    val sortingType = mutableStateOf(0)
    val sortingOrder = mutableStateOf(0)
    var libraryAlbums = mutableStateListOf<AlbumEntity>()

    init {
        viewModelScope.launch {
            sortingRepository.getAlbumSorting().collect {
                sortingType.value = it.type
                sortingOrder.value = it.order
            }
        }

        viewModelScope.launch {
            albumsRepository.fetchAlbumList().collect {
                libraryAlbums = it
            }
        }
    }

    // Preferences management
    fun setSortData(type: Int, order: Int) {
        viewModelScope.launch {
            sortingRepository.setAlbumSorting(SortingValues(type, order))
        }
    }
}