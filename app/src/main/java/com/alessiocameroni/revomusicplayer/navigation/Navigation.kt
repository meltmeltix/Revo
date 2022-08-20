package com.alessiocameroni.revomusicplayer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alessiocameroni.revomusicplayer.screens.MainScreen
import com.alessiocameroni.revomusicplayer.screens.PlayerScreen
import com.alessiocameroni.revomusicplayer.screens.main.*
import com.alessiocameroni.revomusicplayer.screens.other.PermissionsScreen
import com.alessiocameroni.revomusicplayer.screens.search.SearchScreen
import com.alessiocameroni.revomusicplayer.screens.settings.SettingsScreen

open class Screens(val route: String) {
    object MainScreen : Screens("main_screen")
    object PermissionsScreen : Screens("permissions_screen")
    object PlayerScreen : Screens("player_screen")
    object SearchScreen : Screens("search_screen")
    object SettingsScreen : Screens("settings_screen")
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.MainScreen.route) {
        composable(route = Screens.MainScreen.route) { MainScreen(navController = navController) }
        composable(route = Screens.PermissionsScreen.route) { PermissionsScreen(navController = navController) }
        composable(route = Screens.PlayerScreen.route) { PlayerScreen(navController = navController) }
        composable(route = Screens.SearchScreen.route) { SearchScreen(navController = navController) }
        composable(route = Screens.SettingsScreen.route) { SettingsScreen(navController = navController) }
    }
}

@Composable
fun NavigationBottomNavBar(
    navControllerBottomBar: NavHostController,
    navControllerApp: NavController
) {
    NavHost(navController = navControllerBottomBar, startDestination = "home") {
        composable("home") { HomeScreen(navController = navControllerApp) }
        composable("tracks") { TracksScreen(navController = navControllerApp) }
        composable("albums") { AlbumsScreen(navController = navControllerApp) }
        composable("playlists") { PlaylistsScreen(navController = navControllerApp) }
        composable("spotify") { SpotifyFavoritesScreen(navController = navControllerApp) }
    }
}