package com.alessiocameroni.revomusicplayer.ui.screens.library.artistScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.data.classes.Artist
import com.alessiocameroni.revomusicplayer.data.classes.SortingValues
import com.alessiocameroni.revomusicplayer.domain.repository.ArtistsRepository
import com.alessiocameroni.revomusicplayer.domain.repository.SortingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val sortingRepository: SortingRepository,
    private val artistsRepository: ArtistsRepository
): ViewModel() {
    val sortingOrder = mutableStateOf(0)
    var libraryArtists = mutableStateListOf<Artist>()

    init {
        viewModelScope.launch {
            sortingRepository.getArtistSorting().collect {
                sortingOrder.value = it.order
            }
        }
        viewModelScope.launch {
            artistsRepository.fetchArtistList().collect {
                libraryArtists = it
            }
        }
    }

    // Preferences management
    fun setSortData(order: Int) {
        viewModelScope.launch {
            sortingRepository.setArtistSorting(SortingValues(0, order))
        }
    }
}