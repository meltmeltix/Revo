package com.alessiocameroni.revomusicplayer.ui.screens.settings.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibrarySettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
): ViewModel() {
    val spotifyEnabledState = settingsRepository.getSpotifyEnabledState()
        .map { it }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    fun setSpotifyVisibility(visibility: Boolean) {
        viewModelScope.launch { settingsRepository.setSpotifyEnabledState(visibility) }
    }
}