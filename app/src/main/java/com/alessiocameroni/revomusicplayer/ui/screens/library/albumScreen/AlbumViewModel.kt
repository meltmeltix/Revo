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
            if(list.isNotEmpty()) {
                withContext(Dispatchers.Default) {
                    list = sortList(
                        list,
                        sortingType.value,
                        sortingOrder.value
                    )
                }
            } else { isListEmpty.value = true }
            _albums.value = list
        }
    }

    // List management
    private fun sortList(
        list: List<Album>,
        type: Int,
        order: Int,
    ): List<Album> {
        var sortedList = list
        sortedList = when(order) {
            0 -> {
                when(type) {
                    0 -> sortedList.sortedBy { it.albumTitle }
                    1 -> sortedList.sortedBy { it.artist }
                    2 -> sortedList.sortedBy { it.year }
                    3 -> sortedList.sortedBy { it.numberOfSongs }
                    else -> { sortedList.sortedBy { it.albumTitle } }
                }
            }
            1 -> {
                when(type) {
                    0 -> sortedList.sortedByDescending { it.albumTitle }
                    1 -> sortedList.sortedByDescending { it.artist }
                    2 -> sortedList.sortedByDescending { it.year }
                    3 -> sortedList.sortedByDescending { it.numberOfSongs }
                    else -> { sortedList.sortedByDescending { it.albumTitle } }
                }
            }
            else -> { sortedList.sortedBy { it.albumTitle } }
        }
        return sortedList
    }

    private fun onSortChange(type: Int, order: Int) {
        var list = _albums.value!!
        list = sortList(list, type, order)
        _albums.value = list
    }

    // Preferences management
    fun setSortData(type: Int, order: Int) {
        viewModelScope.launch {
            sortingRepository.setAlbumSorting(SortingValues(type, order))
            onSortChange(type, order)
        }
    }
}