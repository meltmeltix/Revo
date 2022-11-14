package com.alessiocameroni.revomusicplayer.data.viewmodels

import androidx.lifecycle.*
import com.alessiocameroni.revomusicplayer.data.repositories.DataStoreCustomization
import kotlinx.coroutines.launch

class CustomizationViewModel(
    private val dataStoreCustomization: DataStoreCustomization
): ViewModel() {
    private val _playerLayout = MutableLiveData(0)
    val playerLayout: LiveData<Int> = _playerLayout

    init {
        viewModelScope.launch {
            dataStoreCustomization.getPlayerLayout.collect{
                _playerLayout.value = it
            }
        }
    }

    suspend fun savePlayerLayout(playerLayout: Int) {
        dataStoreCustomization.setPlayerLayout(playerLayout)
    }
}

class CustomizationViewModelFactory(private val dataStoreCustomization: DataStoreCustomization):
    ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CustomizationViewModel::class.java)) {
                return CustomizationViewModel(dataStoreCustomization) as T
            }
            throw IllegalStateException()
        }
    }
