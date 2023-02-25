package com.alessiocameroni.revomusicplayer.ui.screens.settings.library

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.data.repository.SettingsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibrarySettingsViewModel @Inject constructor(
    private val settingsRepositoryImpl: SettingsRepositoryImpl
): ViewModel() {
    val spotifyEnabledState = mutableStateOf(true)

    init {
        viewModelScope.launch {
            settingsRepositoryImpl.getSpotifyEnabledState().collect {
                spotifyEnabledState.value = it
            }
        }
    }

    fun saveSpotifyVisibility(visibilityValue: Boolean) {
        viewModelScope.launch {
            settingsRepositoryImpl.setSpotifyEnabledState(
                visibilityValue
            )
        }
    }
}