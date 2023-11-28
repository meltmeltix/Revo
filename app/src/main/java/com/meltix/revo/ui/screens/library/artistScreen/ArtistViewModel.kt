package com.meltix.revo.ui.screens.library.artistScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meltix.revo.data.classes.ContentState
import com.meltix.revo.data.classes.artist.Artist
import com.meltix.revo.data.classes.preferences.SortingOrder
import com.meltix.revo.domain.repository.ArtistsRepository
import com.meltix.revo.domain.repository.SortingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val sortingRepository: SortingRepository,
    private val artistsRepository: ArtistsRepository
): ViewModel() {
    private val _contentState: MutableStateFlow<ContentState> = MutableStateFlow(ContentState.LOADING)
    val contentState: StateFlow<ContentState> = _contentState

    val sortingOrder = sortingRepository.getArtistSortOrder()
        .map { SortingOrder.values()[it] }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), SortingOrder.ASCENDING)

    private val _artists: MutableStateFlow<List<Artist>> = MutableStateFlow(emptyList())
    val artists: StateFlow<List<Artist>> =
        combine(_artists, sortingOrder) {
            list, order -> sortList(list, order)
        }
        .flowOn(Dispatchers.Default)
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { _artists.value = artistsRepository.getArtistList() }
            if (_artists.value.isNotEmpty()) { _contentState.value = ContentState.SUCCESS }
            else { _contentState.value = ContentState.FAILURE }
        }
    }
    
    fun onRefresh() {
        viewModelScope.launch {
            _contentState.value = ContentState.LOADING
            withContext(Dispatchers.IO) { _artists.value = artistsRepository.getArtistList() }
            if (_artists.value.isNotEmpty()) { _contentState.value = ContentState.SUCCESS }
            else { _contentState.value = ContentState.FAILURE }
        }
    }

    private fun sortList(
        list: List<Artist>,
        order: SortingOrder,
    ): List<Artist> {
        return when(order) {
            SortingOrder.ASCENDING -> { list.sortedBy { it.artist } }
            SortingOrder.DESCENDING -> { list.sortedByDescending { it.artist } }
        }
    }

    fun setSortOrder(order: SortingOrder) {
        viewModelScope.launch {
            sortingRepository.setArtistOrder(SortingOrder.values().indexOf(order))
            onSort()
        }
    }

    private fun onSort() {
        viewModelScope.launch(Dispatchers.Default) {
            _artists.value = sortList(_artists.value, sortingOrder.value)
        }
    }
}