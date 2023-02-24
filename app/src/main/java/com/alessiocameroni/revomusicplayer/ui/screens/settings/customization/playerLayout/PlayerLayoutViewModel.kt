package com.alessiocameroni.revomusicplayer.ui.screens.settings.customization.playerLayout

import androidx.lifecycle.MutableLiveData
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
    private val _playerLayout = MutableLiveData(1)
    val playerLayout = _playerLayout

    init {
        viewModelScope.launch {
            settingsRepositoryImpl.getPlayerLayout().collect {
                _playerLayout.value = it
            }
        }
    }

    fun saveSelection(selectionIndex: Int) {
        viewModelScope.launch {
            settingsRepositoryImpl.setPlayerLayout(
                selectionIndex
            )
        }
    }
}