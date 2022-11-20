package com.alessiocameroni.revomusicplayer.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alessiocameroni.revomusicplayer.data.repositories.DataStoreCustomization

class CustomizationViewModel(
    private val dataStoreCustomization: DataStoreCustomization
) : ViewModel() {

}

class CustomizationViewModelFactory(
    private val dataStoreCustomization: DataStoreCustomization
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CustomizationViewModel::class.java)) {
            return CustomizationViewModel(dataStoreCustomization) as T
        }
        throw IllegalStateException()
    }
}