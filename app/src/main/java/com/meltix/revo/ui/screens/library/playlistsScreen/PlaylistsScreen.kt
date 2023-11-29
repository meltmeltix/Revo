package com.meltix.revo.ui.screens.library.playlistsScreen

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.meltix.revo.ui.theme.RevoTheme
import com.meltix.revo.util.functions.defineWindowType
import com.meltix.revo.util.functions.findActivity

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun PlaylistsScreen(
    rootNavController: NavController,
    libraryNavController: NavController,
    viewModel: PlaylistsViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowType = defineWindowType(calculateWindowSizeClass(activity))
    
    RevoTheme {
    
    }
}