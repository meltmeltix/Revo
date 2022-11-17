package com.alessiocameroni.revomusicplayer.data.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alessiocameroni.revomusicplayer.data.repositories.DataStoreCustomization
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class CustomizationViewModel(
    context: Context
): ViewModel() {
    private lateinit var dataStoreCustomization: DataStoreCustomization /* = DataStoreCustomization(context)*/
    private var initialized = false
    var playerLayout by Delegates.notNull<Int>()

    init {
        viewModelScope.launch {
            if (!initialized) {
                dataStoreCustomization = DataStoreCustomization(context)
                initialized = true
            }

            val _playerLayout = dataStoreCustomization.getPlayerLayout.stateIn(
                viewModelScope,
                SharingStarted.Lazily,
                0
            )

            playerLayout = _playerLayout.value
        }
    }

    suspend fun savePlayerLayout(newPlayerLayout: Int) {
        dataStoreCustomization.setPlayerLayout(newPlayerLayout)
        playerLayout = newPlayerLayout
    }
}