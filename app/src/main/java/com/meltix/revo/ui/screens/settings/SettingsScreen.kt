package com.meltix.revo.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.*
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.meltix.revo.ui.navigation.RootNavigation
import com.meltix.revo.ui.navigation.RootScreens
import com.meltix.revo.ui.navigation.SettingsScreens
import com.meltix.revo.ui.theme.RevoTheme
import com.meltix.revo.util.functions.findActivity

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowClass = calculateWindowSizeClass(activity)
    val settingsNavController = rememberNavController()
    val backStackEntry = settingsNavController.currentBackStackEntryAsState().value
    val currentDestinationRoute = if(backStackEntry?.destination?.route == null) "" else backStackEntry.destination.route

    RevoTheme {
        MainSettingsLayout(
            windowWidthSizeClass = windowClass.widthSizeClass,
            onBackButtonClick = { navController.navigateUp() },
            onCompactItemClick = { navController.navigate(it) },
            currentDestinationRoute = currentDestinationRoute,
            onExpandedItemClick = {
                viewModel.latestDestination = it
                settingsNavController.navigate(it) {
                    popUpTo(SettingsScreens.Library.route) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            viewModel = viewModel,
        ) {
            RootNavigation(
                startDestination = RootScreens.SettingsGraph.route,
                nestedGraphStartDestination = SettingsScreens.Library.route,
                navController = settingsNavController
            )
        }
    }
}