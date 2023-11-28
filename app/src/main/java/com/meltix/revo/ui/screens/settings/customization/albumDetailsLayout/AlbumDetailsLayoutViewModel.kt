package com.meltix.revo.ui.screens.settings.customization.albumDetailsLayout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meltix.revo.data.classes.album.HeaderLayout
import com.meltix.revo.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumDetailsLayoutViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
): ViewModel() {
    val headerLayout = settingsRepository.getAlbumViewLayout()
        .map { HeaderLayout.values()[it] }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), HeaderLayout.REVO)

    fun setHeaderLayout(selection: HeaderLayout) {
        viewModelScope.launch { settingsRepository.setAlbumViewLayout(HeaderLayout.values().indexOf(selection)) }
    }
}