package com.alessiocameroni.revomusicplayer.ui.screens.settings.customization.playerLayout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.data.repositories.CustomizationPrefsRepository
import kotlinx.coroutines.launch

class PlayerLayoutViewModel(
    private val customizationPrefsRepository: CustomizationPrefsRepository
): ViewModel() {
    private var dataStoreInitialized = false

    private val _playerLayout = MutableLiveData(1)
    val playerLayout = _playerLayout

    fun initializeDataStore() {
        if (dataStoreInitialized) return

        viewModelScope.launch {
            customizationPrefsRepository.getPlayerLayout.collect{
                _playerLayout.value = it
            }
        }

        dataStoreInitialized = true
    }
}

class PlayerLayoutViewModelFactory(
    private val customizationPrefsRepository: CustomizationPrefsRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PlayerLayoutViewModel::class.java)) {
            return PlayerLayoutViewModel(customizationPrefsRepository) as T
        }
        throw IllegalStateException()
    }
}