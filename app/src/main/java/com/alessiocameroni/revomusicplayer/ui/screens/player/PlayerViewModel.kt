package com.alessiocameroni.revomusicplayer.ui.screens.player

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
): ViewModel() {
    val playerLayout = mutableStateOf(1)

    init {
        viewModelScope.launch {
            settingsRepository.getPlayerLayout().collect {
                playerLayout.value = it
            }
        }
    }
}