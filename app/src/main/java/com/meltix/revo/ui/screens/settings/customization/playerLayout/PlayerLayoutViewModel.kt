package com.meltix.revo.ui.screens.settings.customization.playerLayout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meltix.revo.data.classes.player.PlayerButtonsLayout
import com.meltix.revo.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerLayoutViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
): ViewModel() {
    val playerButtonsLayout = settingsRepository.getPlayerLayout()
        .map { PlayerButtonsLayout.values()[it] }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PlayerButtonsLayout.CENTER)

    fun setLayout(selection: PlayerButtonsLayout) {
        viewModelScope.launch { settingsRepository.setPlayerLayout(PlayerButtonsLayout.values().indexOf(selection)) }
    }
}