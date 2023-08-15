package com.meltix.revo.ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meltix.revo.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private var settingsRepository: SettingsRepository
): ViewModel() {
    val spotifyEnabledState = mutableStateOf(true)

    init {
        viewModelScope.launch {
            settingsRepository.getSpotifyEnabledState().collect {
                spotifyEnabledState.value = it
            }
        }
    }
}