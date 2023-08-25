package com.meltix.revo.ui.screens.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meltix.revo.data.classes.player.PlayerLayout
import com.meltix.revo.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    settingsRepository: SettingsRepository
): ViewModel() {
    val playerLayout = settingsRepository.getPlayerLayout()
        .map { PlayerLayout.values()[it] }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PlayerLayout.CENTER)
}