package com.meltix.revo.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.*
import androidx.navigation.NavController
import com.meltix.revo.ui.navigation.RootNavigation
import com.meltix.revo.ui.navigation.RootScreens
import com.meltix.revo.ui.screens.settings.settingsList.SettingsListScreen
import com.meltix.revo.ui.theme.RevoTheme
import com.meltix.revo.util.functions.findActivity

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MainSettingsScreen(navController: NavController) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowClass = calculateWindowSizeClass(activity)

    RevoTheme {
        MainSettingsLayout(
            windowClass = windowClass,
            leftPaneContent = { secondaryNavController ->
                SettingsListScreen(navController, secondaryNavController)
            },
            rightPaneContent = { startDestination, navController ->
                // TODO fix the back button for the compact screen as it doesn't go back on click
                RootNavigation(
                    startDestination = RootScreens.SettingsGraph.route,
                    nestedGraphStartDestination = startDestination,
                    navController = navController
                )
            }
        )
    }
}