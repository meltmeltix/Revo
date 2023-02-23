package com.alessiocameroni.revomusicplayer.ui.screens.settings.customization.playerLayout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.data.repository.CustomizationRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerLayoutViewModel @Inject constructor(
    private val customizationRepositoryImpl: CustomizationRepositoryImpl
): ViewModel() {
    private val _playerLayout = MutableLiveData(1)
    val playerLayout = _playerLayout

    fun getPlayerLayout() {
        viewModelScope.launch {
            customizationRepositoryImpl.getPlayerLayout().collect {
                _playerLayout.value = it
            }
        }
    }
}