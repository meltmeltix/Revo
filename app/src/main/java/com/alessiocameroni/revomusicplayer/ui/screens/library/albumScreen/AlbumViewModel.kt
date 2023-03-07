package com.alessiocameroni.revomusicplayer.ui.screens.library.albumScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.data.classes.AlbumEntry
import com.alessiocameroni.revomusicplayer.data.classes.SortingValues
import com.alessiocameroni.revomusicplayer.data.repository.AlbumsRepositoryImpl
import com.alessiocameroni.revomusicplayer.data.repository.SortingRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val sortingRepositoryImpl: SortingRepositoryImpl,
    private val albumsRepositoryImpl: AlbumsRepositoryImpl
): ViewModel() {
    val sortingType = mutableStateOf(0)
    val sortingOrder = mutableStateOf(0)
    var libraryAlbums = mutableStateListOf<AlbumEntry>()

    init {
        viewModelScope.launch {
            sortingRepositoryImpl.getAlbumSorting().collect {
                sortingType.value = it.type
                sortingOrder.value = it.order
            }
        }

        viewModelScope.launch {
            albumsRepositoryImpl.fetchAlbumList().collect {
                libraryAlbums = it
            }
        }
    }

    // Preferences management
    fun setSortData(type: Int, order: Int) {
        viewModelScope.launch {
            sortingRepositoryImpl.setAlbumSorting(SortingValues(type, order))
        }
    }
}