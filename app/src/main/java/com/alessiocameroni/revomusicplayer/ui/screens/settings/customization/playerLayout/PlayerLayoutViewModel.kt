package com.alessiocameroni.revomusicplayer.ui.screens.settings.customization.playerLayout

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.data.repository.SettingsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerLayoutViewModel @Inject constructor(
    private val settingsRepositoryImpl: SettingsRepositoryImpl
): ViewModel() {
    val playerLayout = mutableStateOf(1)

    init {
        viewModelScope.launch {
            settingsRepositoryImpl.getPlayerLayout().collect {
                playerLayout.value = it
            }
        }
    }

    fun setLayout(selectionIndex: Int) {
        viewModelScope.launch {
            settingsRepositoryImpl.setPlayerLayout(
                selectionIndex
            )
        }
    }
}