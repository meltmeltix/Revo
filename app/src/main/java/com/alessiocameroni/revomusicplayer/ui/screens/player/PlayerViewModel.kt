package com.alessiocameroni.revomusicplayer.ui.screens.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.data.classes.playlist.PlayerLayout
import com.alessiocameroni.revomusicplayer.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
): ViewModel() {
    val playerLayout = settingsRepository.getPlayerLayout()
        .map { PlayerLayout.values()[it] }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PlayerLayout.CENTER)
}