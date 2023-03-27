package com.alessiocameroni.revomusicplayer.ui.screens.library.artistScreen.artistViewScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.data.classes.ContentState
import com.alessiocameroni.revomusicplayer.data.classes.artist.ArtistAlbum
import com.alessiocameroni.revomusicplayer.data.classes.artist.ArtistDetails
import com.alessiocameroni.revomusicplayer.data.classes.artist.ArtistSong
import com.alessiocameroni.revomusicplayer.data.classes.preferences.SortingOrder
import com.alessiocameroni.revomusicplayer.data.classes.preferences.SortingType
import com.alessiocameroni.revomusicplayer.domain.repository.ArtistViewRepository
import com.alessiocameroni.revomusicplayer.domain.repository.SortingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ArtistViewViewModel @Inject constructor(
    private val sortingRepository: SortingRepository,
    private val artistViewRepository: ArtistViewRepository
): ViewModel() {
    val sortTypeList = listOf(
        SortingType.TITLE,
        SortingType.TRACK,
        SortingType.DURATION,
        SortingType.ALBUM
    )

    private val _contentState: MutableStateFlow<ContentState> = MutableStateFlow(ContentState.LOADING)
    val contentState: StateFlow<ContentState> = _contentState

    val sortingType = sortingRepository.getArtistSongsSortType()
        .map { sortTypeList[it] }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), SortingType.TITLE)

    val sortingOrder = sortingRepository.getArtistSortOrder()
        .map { SortingOrder.values()[it] }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), SortingOrder.ASCENDING)

    private val _artistDetails: MutableStateFlow<ArtistDetails> = MutableStateFlow(
        ArtistDetails("Unknown Artist", 0, 0)
    )
    val artistDetails: StateFlow<ArtistDetails> = _artistDetails

    private val _albums: MutableStateFlow<List<ArtistAlbum>> = MutableStateFlow(emptyList())
    val albums: StateFlow<List<ArtistAlbum>> = _albums

    val artistCover = _albums
        .filter { list -> list.isNotEmpty() }
        .map { albums -> albums[0].albumCoverUri }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    private val _songs: MutableStateFlow<List<ArtistSong>> = MutableStateFlow(emptyList())
    val songs: StateFlow<List<ArtistSong>> =
        combine(_songs, sortingOrder, sortingType) {
            list, order, type -> sortList(list, order, type)
        }
        .flowOn(Dispatchers.Default)
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun initializeArtistData(artistId: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _artistDetails.value = artistViewRepository.getArtistDetails(artistId)
                _albums.value = artistViewRepository.getAlbumList(artistId)
                _songs.value = artistViewRepository.getSongList(artistId)
            }
            if (_songs.value.isNotEmpty()) { _contentState.value = ContentState.SUCCESS }
            else { _contentState.value = ContentState.FAILURE }
        }
    }

    private fun sortList(
        list: List<ArtistSong>,
        order: SortingOrder,
        type: SortingType
    ): List<ArtistSong> {
        return when(order) {
            SortingOrder.ASCENDING -> {
                when(type) {
                    SortingType.TITLE -> list.sortedBy { it.songTitle }
                    SortingType.TRACK -> list.sortedBy { it.track }
                    SortingType.DURATION -> list.sortedBy { it.duration }
                    SortingType.ALBUM -> list.sortedBy { it.album }
                    else -> { list.sortedBy { it.songTitle } }
                }
            }
            SortingOrder.DESCENDING -> {
                when(type) {
                    SortingType.TITLE -> list.sortedByDescending { it.songTitle}
                    SortingType.TRACK -> list.sortedByDescending { it.track }
                    SortingType.DURATION -> list.sortedByDescending { it.duration }
                    SortingType.ALBUM -> list.sortedByDescending { it.album }
                    else -> { list.sortedByDescending { it.songTitle } }
                }
            }
        }
    }

    fun setSortType(type: SortingType) {
        viewModelScope.launch {
            sortingRepository.setArtistSongsSortType(sortTypeList.indexOf(type))
            onSort()
        }
    }

    fun setSortOrder(order: SortingOrder) {
        viewModelScope.launch {
            sortingRepository.setArtistSongsSortOrder(SortingOrder.values().indexOf(order))
            onSort()
        }
    }

    private fun onSort() {
        viewModelScope.launch(Dispatchers.Default) {
            _songs.value = sortList(_songs.value, sortingOrder.value, sortingType.value)
        }
    }
}