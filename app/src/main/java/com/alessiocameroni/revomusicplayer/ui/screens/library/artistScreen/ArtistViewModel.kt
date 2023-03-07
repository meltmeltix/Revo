package com.alessiocameroni.revomusicplayer.ui.screens.library.artistScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.data.classes.ArtistEntity
import com.alessiocameroni.revomusicplayer.data.classes.SortingValues
import com.alessiocameroni.revomusicplayer.data.repository.ArtistsRepositoryImpl
import com.alessiocameroni.revomusicplayer.data.repository.SortingRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val sortingRepositoryImpl: SortingRepositoryImpl,
    private val artistsRepositoryImpl: ArtistsRepositoryImpl
): ViewModel() {
    val sortingOrder = mutableStateOf(0)
    var libraryArtists = mutableStateListOf<ArtistEntity>()

    init {
        viewModelScope.launch {
            sortingRepositoryImpl.getArtistSorting().collect {
                sortingOrder.value = it.order
            }
        }
        viewModelScope.launch {
            artistsRepositoryImpl.fetchArtistList().collect {
                libraryArtists = it
            }
        }
    }

    // Preferences management
    fun setSortData(order: Int) {
        viewModelScope.launch {
            sortingRepositoryImpl.setArtistSorting(SortingValues(0, order))
        }
    }
}