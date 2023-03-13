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
            withContext(Dispatchers.IO) { list = artistsRepository.fetchArtistList() }
            _artists.value = list
            if (list.isNotEmpty()) { sortList(sortingOrder.value) }
            else { isListEmpty.value = true }
        }
    }

    // List management
    private fun sortList(
        order: Int,
    ) {
        var list = _artists.value!!
        list = when(order) {
            0 -> { list.sortedBy { it.artist } }
            1 -> { list.sortedByDescending { it.artist } }
            else -> { list.sortedBy { it.artist } }
        }
        _artists.value = list
    }

    // Preferences management
    fun setSortData(order: Int) {
        viewModelScope.launch {
            sortingRepository.setArtistSorting(SortingValues(0, order))
            sortList(order)
        }
    }
}