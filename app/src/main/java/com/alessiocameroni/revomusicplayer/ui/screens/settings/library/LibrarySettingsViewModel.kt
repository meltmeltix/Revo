package com.alessiocameroni.revomusicplayer.ui.screens.settings.library

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibrarySettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
): ViewModel() {
    val spotifyEnabledState = mutableStateOf(true)

    init {
        viewModelScope.launch {
            settingsRepository.getSpotifyEnabledState().collect {
                spotifyEnabledState.value = it
            }
        }
    }

    fun setSpotifyVisibility(visibility: Boolean) {
        viewModelScope.launch { settingsRepository.setSpotifyEnabledState(visibility) }
    }
}