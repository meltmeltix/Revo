package com.meltix.revo.ui.screens.library.spotifyScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.meltix.revo.ui.components.contentModifier
import com.meltix.revo.ui.components.scrollBehaviorOnWindowSize
import com.meltix.revo.ui.components.surfaceColorOnWindowSize
import com.meltix.revo.util.functions.findActivity

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun SpotifyFavoritesScreen(rootNavController: NavController) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowClass = calculateWindowSizeClass(activity)
    val scrollBehavior = scrollBehaviorOnWindowSize(windowClass)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { SpotifyTopActionBar(rootNavController, scrollBehavior, windowClass) },
        containerColor = surfaceColorOnWindowSize(windowClass),
        content = { padding ->
            LazyColumn(
                modifier = Modifier.contentModifier(windowClass, padding),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                contentPadding = PaddingValues(bottom = 70.dp)
            ){

            }
        }
    )
}