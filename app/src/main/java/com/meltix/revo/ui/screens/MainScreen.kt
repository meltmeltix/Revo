package com.meltix.revo.ui.screens

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.meltix.revo.data.classes.player.PlayerLayout
import com.meltix.revo.ui.navigation.LibraryNavigation
import com.meltix.revo.ui.theme.RevoTheme
import com.meltix.revo.util.functions.defineWindowType
import com.meltix.revo.util.functions.findActivity

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
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
    
    val playerLayout by viewModel.playerLayout.collectAsStateWithLifecycle(PlayerLayout.CENTER)

    RevoTheme {
        MainLayout(
            windowType = defineWindowType(windowSizeClass),
            currentDestinationRoute = currentDestinationRoute,
            onNavigationItemSelected = {
                viewModel.latestDestination = it
                libraryNavController.navigate(it) {
                    popUpTo(libraryNavController.graph.findStartDestination().id) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            viewModel = viewModel,
            playAllOnClick = {  },
            newPlaylistOnClick = {  },
            addTrackOnClick = {  },
        ) {
            LibraryNavigation(
                rootNavController = navController,
                libraryNavController = libraryNavController
            )
        }
    }
}
