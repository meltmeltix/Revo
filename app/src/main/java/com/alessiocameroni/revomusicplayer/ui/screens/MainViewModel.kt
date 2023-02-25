package com.alessiocameroni.revomusicplayer.ui.screens

import androidx.lifecycle.ViewModel
import com.alessiocameroni.revomusicplayer.data.repository.SettingsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private var settingsRepositoryImpl: SettingsRepositoryImpl
): ViewModel() {

}