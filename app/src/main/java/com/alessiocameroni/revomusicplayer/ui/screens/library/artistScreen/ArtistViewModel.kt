package com.alessiocameroni.revomusicplayer.ui.screens.library.artistScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.data.classes.Artist
import com.alessiocameroni.revomusicplayer.data.classes.SortingValues
import com.alessiocameroni.revomusicplayer.domain.repository.ArtistsRepository
import com.alessiocameroni.revomusicplayer.domain.repository.SortingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val sortingRepository: SortingRepository,
    private val artistsRepository: ArtistsRepository
): ViewModel() {
    val sortingOrder = mutableStateOf(0)
    val isListEmpty = mutableStateOf(false)
    private var _artists: MutableLiveData<List<Artist>> = MutableLiveData(emptyList())
    val artist = _artists

    init {
        viewModelScope.launch {
            sortingRepository.getArtistSorting().collect {
                sortingOrder.value = it.order
            }
        }
        viewModelScope.launch {
            var list: List<Artist>
            withContext(Dispatchers.IO) { list = artistsRepository.getArtistList() }
            if (list.isNotEmpty()) {
                withContext(Dispatchers.Default) {
                    list = sortList(list, sortingOrder.value)
                }
            }
            else { isListEmpty.value = true }
            _artists.value = list
        }
    }

    // List management
    private fun sortList(
        list: List<Artist>,
        order: Int,
    ): List<Artist> {
        var sortedList = list
        sortedList = when(order) {
            0 -> { sortedList.sortedBy { it.artist } }
            1 -> { sortedList.sortedByDescending { it.artist } }
            else -> { sortedList.sortedBy { it.artist } }
        }
        return sortedList
    }

    private suspend fun onSortChange(order: Int) {
        var list = _artists.value!!
        withContext(Dispatchers.Default) { list = sortList(list, order) }
        _artists.value = list
    }

    // Preferences management
    fun setSortData(order: Int) {
        viewModelScope.launch {
            sortingRepository.setArtistSorting(SortingValues(0, order))
            onSortChange(order)
        }
    }
}