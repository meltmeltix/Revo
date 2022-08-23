package com.alessiocameroni.revomusicplayer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alessiocameroni.revomusicplayer.MainScreen
import com.alessiocameroni.revomusicplayer.albums.AlbumsScreen
import com.alessiocameroni.revomusicplayer.home.HomeScreen
import com.alessiocameroni.revomusicplayer.player.PlayerScreen
import com.alessiocameroni.revomusicplayer.playlists.PlaylistsScreen
import com.alessiocameroni.revomusicplayer.search.SearchScreen
import com.alessiocameroni.revomusicplayer.settings.about.AboutScreen
import com.alessiocameroni.revomusicplayer.settings.customization.LooksScreen
import com.alessiocameroni.revomusicplayer.settings.main.SettingsScreen
import com.alessiocameroni.revomusicplayer.spotify.SpotifyFavoritesScreen
import com.alessiocameroni.revomusicplayer.tracks.TracksScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.MainScreen.route) {
        composable(route = Screens.MainScreen.route) { MainScreen(navController = navController) }
        composable(route = Screens.PlayerScreen.route) { PlayerScreen(navController = navController) }
        composable(route = Screens.SearchScreen.route) { SearchScreen(navController = navController) }
        composable(route = Screens.SettingsScreen.route) { SettingsScreen(navController = navController) }

        composable(route = SettingsScreens.LooksScreen.route) { LooksScreen(navController = navController) }
        composable(route = SettingsScreens.AboutScreen.route) { AboutScreen(navController = navController) }
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