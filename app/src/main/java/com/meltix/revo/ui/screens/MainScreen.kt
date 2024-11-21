package com.meltix.revo.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.meltix.revo.data.classes.Position
import com.meltix.revo.data.classes.library.LibraryNavigationItem
import com.meltix.revo.ui.navigation.LibraryNavigation
import com.meltix.revo.ui.theme.RevoTheme
import com.meltix.revo.util.functions.WindowType
import com.meltix.revo.util.functions.defineWindowType
import com.meltix.revo.util.functions.findActivity

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MainScreen(
    destinationsList: List<LibraryNavigationItem>,
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowType = defineWindowType(calculateWindowSizeClass(activity))
    
    val fabPosition by viewModel.fabPosition.collectAsStateWithLifecycle(Position.END)
    
    val libraryNavController = rememberNavController()
    val backStackEntry = libraryNavController.currentBackStackEntryAsState().value
    val currentDestinationRoute =
        if (backStackEntry?.destination?.route == null) ""
        else backStackEntry.destination.route!!
    
    MainScreen(
        windowType = windowType,
        fabPosition = fabPosition,
        destinationsList = destinationsList,
        currentDestinationRoute = currentDestinationRoute,
        onNavigationItemSelected = {
            libraryNavController.navigate(it) {
                popUpTo(libraryNavController.graph.findStartDestination().id) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        },
        miniPlayer = { modifier, color -> MiniPlayer(modifier, color, windowType) }
    ) { contentPadding ->
        LibraryNavigation(
            startDestination = destinationsList[0].route,
            rootNavController = navController,
            libraryNavController = libraryNavController,
            contentPadding = contentPadding
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreen(
    windowType: WindowType,
    fabPosition: Position,
    destinationsList: List<LibraryNavigationItem>,
    currentDestinationRoute: String,
    onNavigationItemSelected: (String) -> Unit,
    miniPlayer: @Composable (Modifier, Color) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()
    
    RevoTheme {
        MainLayout(
            windowType = windowType,
            fabPosition = fabPosition,
            destinationsList = destinationsList,
            currentDestinationRoute = currentDestinationRoute,
            onNavigationItemSelected = onNavigationItemSelected,
            miniPlayer = miniPlayer,
            content = content
        )
    }
}