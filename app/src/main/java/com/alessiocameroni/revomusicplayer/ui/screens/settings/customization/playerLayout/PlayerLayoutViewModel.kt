package com.alessiocameroni.revomusicplayer.ui.screens.settings.customization.playerLayout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayerLayoutViewModel: ViewModel() {
    private val _playerLayout = MutableLiveData(1)
    val playerLayout = _playerLayout
}