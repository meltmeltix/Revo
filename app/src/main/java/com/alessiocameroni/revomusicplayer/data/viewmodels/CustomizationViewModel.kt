package com.alessiocameroni.revomusicplayer.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alessiocameroni.revomusicplayer.data.repositories.CustomizationPrefsDataStore

class CustomizationViewModel(
    private val customizationPreferencesRepository: CustomizationPrefsDataStore
) : ViewModel() {

}

class CustomizationViewModelFactory(
    private val customizationPreferencesRepository: CustomizationPrefsDataStore
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CustomizationViewModel::class.java)) {
            return CustomizationViewModel(customizationPreferencesRepository) as T
        }
        throw IllegalStateException()
    }
}