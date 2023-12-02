package com.meltix.revo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meltix.revo.R
import com.meltix.revo.data.classes.library.Destinations
import com.meltix.revo.data.classes.library.LibraryNavigationItem
import com.meltix.revo.data.classes.UiState
import com.meltix.revo.domain.repository.SettingsRepository
import com.meltix.revo.ui.navigation.LibraryScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    settingsRepository: SettingsRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.LOADING)
    val uiState: StateFlow<UiState> = _uiState
    
    private val destinationsList = settingsRepository.getDestinationsOrder()
        .map { it.toList() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            listOf("SONGS", "ARTISTS", "ALBUMS", "PLAYLISTS")
        )
    
    private var _destinations: MutableStateFlow<List<LibraryNavigationItem>> = MutableStateFlow(emptyList())
    val destinations: StateFlow<List<LibraryNavigationItem>> = _destinations
    
    init {
        viewModelScope.launch {
            _destinations.value = orderDestinations(destinationsList.value)
            _uiState.value = UiState.SUCCESS
        }
    }
    
    private fun orderDestinations(destinationsList: List<String>): List<LibraryNavigationItem> {
        val orderedDestinations = mutableListOf<LibraryNavigationItem>()
        
        destinationsList.forEach {
            when(Destinations.valueOf(it)) {
                Destinations.SONGS -> orderedDestinations.add(
                    LibraryNavigationItem(
                        name = R.string.songs,
                        route = LibraryScreens.Songs.route,
                        unselectedIcon = R.drawable.ic_baseline_music_note_24,
                        selectedIcon = R.drawable.ic_baseline_music_note_24,
                    )
                )
                Destinations.ALBUMS -> orderedDestinations.add(
                    LibraryNavigationItem(
                        name = R.string.albums,
                        route = LibraryScreens.Albums.route,
                        unselectedIcon = R.drawable.ic_outlined_album_24,
                        selectedIcon = R.drawable.ic_filled_album_24,
                    )
                )
                Destinations.ARTISTS -> orderedDestinations.add(
                    LibraryNavigationItem(
                        name = R.string.artists,
                        route = LibraryScreens.Artists.route,
                        unselectedIcon = R.drawable.ic_outlined_groups_24,
                        selectedIcon = R.drawable.ic_filled_groups_24,
                    )
                )
                Destinations.PLAYLISTS -> orderedDestinations.add(
                    LibraryNavigationItem(
                        name = R.string.playlists,
                        route = LibraryScreens.Playlists.route,
                        unselectedIcon = R.drawable.ic_baseline_playlist_play_24,
                        selectedIcon = R.drawable.ic_baseline_playlist_play_24,
                    )
                )
            }
        }
        
        return orderedDestinations
    }
}