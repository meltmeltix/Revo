package com.alessiocameroni.revomusicplayer.ui.screens.settings.customization.playerLayout

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerLayoutViewModel @Inject constructor(
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

    fun setLayout(selectionIndex: Int) {
        viewModelScope.launch {
            settingsRepository.setPlayerLayout(
                selectionIndex
            )
        }
    }
}