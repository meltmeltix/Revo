package com.meltix.revo.ui.screens.library.albumsScreen

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
fun AlbumsScreen(
    rootNavController: NavController,
    libraryNavController: NavController,
    viewModel: AlbumsViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowType = defineWindowType(calculateWindowSizeClass(activity))
    
    RevoTheme {
    
    }
}