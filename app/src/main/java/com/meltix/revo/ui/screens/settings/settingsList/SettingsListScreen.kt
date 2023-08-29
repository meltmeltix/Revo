package com.meltix.revo.ui.screens.settings.settingsList

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.meltix.revo.ui.navigation.SettingsScreens
import com.meltix.revo.ui.theme.RevoTheme
import com.meltix.revo.util.functions.findActivity

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun SettingsListScreen(
    navController: NavController,
    settingsNavController: NavHostController? = null,
    viewModel: SettingsListViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowClass = calculateWindowSizeClass(activity)
    val settingsBackStackEntry = settingsNavController?.currentBackStackEntryAsState()?.value
    val settingsCurrentDestinationRoute =
        if (settingsBackStackEntry == null || settingsBackStackEntry.destination.route == null) ""
        else settingsBackStackEntry.destination.route
    
    RevoTheme {
        SettingsListLayout(
            windowClass = windowClass,
            viewModel = viewModel,
            onBackButtonClick = { navController.navigateUp() },
            onCompactItemClick = { route ->
                navController.navigate(route) {
                    popUpTo(SettingsScreens.MainSettings.route) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            onExpandedItemClick = { route ->
                if(settingsNavController != null) {
                    viewModel.latestDestination = route
                    settingsNavController.navigate(route) {
                        popUpTo(SettingsScreens.Library.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            currentDestinationRoute = settingsCurrentDestinationRoute
        )
    }
}