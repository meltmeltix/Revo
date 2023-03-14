package com.alessiocameroni.revomusicplayer.ui.screens.library.albumScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.data.classes.Album
import com.alessiocameroni.revomusicplayer.data.classes.SortingValues
import com.alessiocameroni.revomusicplayer.domain.repository.AlbumsRepository
import com.alessiocameroni.revomusicplayer.domain.repository.SortingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val sortingRepository: SortingRepository,
    private val albumsRepository: AlbumsRepository
): ViewModel() {
    val sortingType = mutableStateOf(0)
    val sortingOrder = mutableStateOf(0)
    val isListEmpty = mutableStateOf(false)
    private var _albums: MutableLiveData<List<Album>> = MutableLiveData(emptyList())
    val albums = _albums

    init {
        viewModelScope.launch {
            sortingRepository.getAlbumSorting().collect {
                sortingType.value = it.type
                sortingOrder.value = it.order
            }
        }
        viewModelScope.launch(Dispatchers.Main) {
            var list: List<Album>
            withContext(Dispatchers.IO) { list = albumsRepository.getAlbumList() }
            _albums.value = list
            if(list.isNotEmpty()) { sortList(sortingType.value, sortingOrder.value) }
            else { isListEmpty.value = true }
        }
    }

    // List management
    private fun sortList(
        type: Int,
        order: Int,
    ) {
        var list = _albums.value!!
        list = when(order) {
            0 -> {
                when(type) {
                    0 -> list.sortedBy { it.albumTitle }
                    1 -> list.sortedBy { it.artist }
                    2 -> list.sortedBy { it.year }
                    3 -> list.sortedBy { it.numberOfSongs }
                    else -> { list.sortedBy { it.albumTitle } }
                }
            }
            1 -> {
                when(type) {
                    0 -> list.sortedByDescending { it.albumTitle }
                    1 -> list.sortedByDescending { it.artist }
                    2 -> list.sortedByDescending { it.year }
                    3 -> list.sortedByDescending { it.numberOfSongs }
                    else -> { list.sortedByDescending { it.albumTitle } }
                }
            }
            else -> { list.sortedBy { it.albumTitle } }
        }
        _albums.value = list
    }

    // Preferences management
    fun setSortData(type: Int, order: Int) {
        viewModelScope.launch {
            sortingRepository.setAlbumSorting(SortingValues(type, order))
            sortList(type, order)
        }
    }
}