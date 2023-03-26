package com.alessiocameroni.revomusicplayer.ui.screens.library.albumScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.data.classes.ContentState
import com.alessiocameroni.revomusicplayer.data.classes.album.Album
import com.alessiocameroni.revomusicplayer.data.classes.preferences.SortingOrder
import com.alessiocameroni.revomusicplayer.data.classes.preferences.SortingType
import com.alessiocameroni.revomusicplayer.domain.repository.AlbumsRepository
import com.alessiocameroni.revomusicplayer.domain.repository.SortingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val sortingRepository: SortingRepository,
    private val albumsRepository: AlbumsRepository
): ViewModel() {
    val sortTypeList = listOf(
        SortingType.TITLE,
        SortingType.ARTIST,
        SortingType.YEAR,
        SortingType.NUMBER_OF_TRACKS
    )

    private val _contentState: MutableStateFlow<ContentState> = MutableStateFlow(ContentState.LOADING)
    val contentState: StateFlow<ContentState> = _contentState

    val sortingType = sortingRepository.getAlbumSortType()
        .map { sortTypeList[it] }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), SortingType.TITLE)

    val sortingOrder = sortingRepository.getAlbumSortOrder()
        .map { SortingOrder.values()[it] }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), SortingOrder.ASCENDING)

    private val _albums: MutableStateFlow<List<Album>> = MutableStateFlow(emptyList())
    val albums: StateFlow<List<Album>> = combine(_albums, sortingOrder, sortingType) {
            list, order, type -> sortList(list, order, type)
        }
        .flowOn(Dispatchers.Default)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { _albums.value = albumsRepository.getAlbumList() }
            if (_albums.value.isNotEmpty()) { _contentState.value = ContentState.SUCCESS }
            else { _contentState.value = ContentState.FAILURE }
        }
    }

    private fun sortList(
        list: List<Album>,
        order: SortingOrder,
        type: SortingType,
    ): List<Album> {
        return when(order) {
            SortingOrder.ASCENDING -> {
                when(type) {
                    SortingType.TITLE -> list.sortedBy { it.albumTitle }
                    SortingType.ARTIST -> list.sortedBy { it.artist }
                    SortingType.YEAR -> list.sortedBy { it.year }
                    SortingType.NUMBER_OF_TRACKS -> list.sortedBy { it.numberOfSongs }
                    else -> { list.sortedBy { it.albumTitle } }
                }
            }
            SortingOrder.DESCENDING -> {
                when(type) {
                    SortingType.TITLE -> list.sortedByDescending { it.albumTitle }
                    SortingType.ARTIST -> list.sortedByDescending { it.artist }
                    SortingType.YEAR -> list.sortedByDescending { it.year }
                    SortingType.NUMBER_OF_TRACKS -> list.sortedByDescending { it.numberOfSongs }
                    else -> { list.sortedByDescending { it.albumTitle } }
                }
            }
        }
    }

    fun setSortType(type: SortingType) {
        viewModelScope.launch {
            sortingRepository.setAlbumSortType(sortTypeList.indexOf(type))
            onSort()
        }
    }

    fun setSortOrder(order: SortingOrder) {
        viewModelScope.launch {
            sortingRepository.setAlbumSortOrder(SortingOrder.values().indexOf(order))
            onSort()
        }
    }

    private fun onSort() {
        viewModelScope.launch(Dispatchers.Default) {
            _albums.value = sortList(_albums.value, sortingOrder.value, sortingType.value)
        }
    }
}