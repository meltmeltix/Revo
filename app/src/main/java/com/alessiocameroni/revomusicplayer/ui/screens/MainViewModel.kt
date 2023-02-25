package com.alessiocameroni.revomusicplayer.ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.data.repository.SettingsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private var settingsRepositoryImpl: SettingsRepositoryImpl
): ViewModel() {
    val spotifyEnabledState = mutableStateOf(true)

    init {
        viewModelScope.launch {
            settingsRepositoryImpl.getSpotifyEnabledState().collect {
                spotifyEnabledState.value = it
            }
        }
    }
}