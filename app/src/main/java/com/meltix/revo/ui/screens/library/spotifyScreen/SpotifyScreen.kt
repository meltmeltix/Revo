package com.meltix.revo.ui.screens.library.spotifyScreen

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.meltix.revo.util.functions.findActivity

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun SpotifyFavoritesScreen(rootNavController: NavController) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowClass = calculateWindowSizeClass(activity)
    
    SpotifyLayout(
        windowClass = windowClass,
        onOpenSpotifyClick = {
        
        },
        onRefresh = { /*TODO*/ },
        onNavigate = { rootNavController.navigate(it) }
    ) {  }
}