package com.meltix.revo.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.meltix.revo.ui.navigation.LibraryNavigation
import com.meltix.revo.ui.screens.player.MainPlayerScreen
import com.meltix.revo.ui.theme.RevoTheme
import com.meltix.revo.util.functions.defineWindowType
import com.meltix.revo.util.functions.findActivity

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowSizeClass = calculateWindowSizeClass(activity)
    val libraryNavController = rememberNavController()
    val backStackEntry = libraryNavController.currentBackStackEntryAsState().value
    val currentDestinationRoute = if (backStackEntry?.destination?.route == null) "" else backStackEntry.destination.route!!
    
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    
    //val playerButtonsLayout by viewModel.playerButtonsLayout.collectAsStateWithLifecycle(PlayerButtonsLayout.CENTER)
    
    RevoTheme {
        MainLayout(
            windowType = defineWindowType(windowSizeClass),
            currentDestinationRoute = currentDestinationRoute,
            latestDestination = viewModel.latestDestination,
            onNavigationItemSelected = {
                viewModel.latestDestination = it
                libraryNavController.navigate(it) {
                    popUpTo(libraryNavController.graph.findStartDestination().id) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            fabOnShuffleClick = {  },
            fabOnNewPlaylistClick = {  },
            fabOnAddTrackClick = {  },
            bottomSheetState = bottomSheetState,
            openBottomSheet = openBottomSheet,
            onMiniPlayerClick = { openBottomSheet = !openBottomSheet },
            onPlayPauseClick = {  },
            onNextSkipClick = {  },
            onSheetDismissRequest = { openBottomSheet = false },
            bottomSheetContent = {
                MainPlayerScreen()
            },
        ) {
            LibraryNavigation(
                rootNavController = navController,
                libraryNavController = libraryNavController
            )
        }
    }
}
