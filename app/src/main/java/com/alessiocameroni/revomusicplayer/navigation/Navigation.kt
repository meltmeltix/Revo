package com.alessiocameroni.revomusicplayer.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.alessiocameroni.revomusicplayer.main.MainScreen
import com.alessiocameroni.revomusicplayer.main.albums.AlbumsScreen
import com.alessiocameroni.revomusicplayer.main.home.HomeScreen
import com.alessiocameroni.revomusicplayer.main.playlists.PlaylistsScreen
import com.alessiocameroni.revomusicplayer.main.spotify.SpotifyFavoritesScreen
import com.alessiocameroni.revomusicplayer.main.tracks.TracksScreen
import com.alessiocameroni.revomusicplayer.player.PlayerScreen
import com.alessiocameroni.revomusicplayer.search.SearchScreen
import com.alessiocameroni.revomusicplayer.settings.about.AboutScreen
import com.alessiocameroni.revomusicplayer.settings.customization.LooksScreen
import com.alessiocameroni.revomusicplayer.settings.main.SettingsScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController = navController, startDestination = Screens.MainScreen.route) {
        composable(
            route = Screens.MainScreen.route,

        ) { MainScreen(navController = navController) }
        composable(
            route = Screens.PlayerScreen.route,

        ) { PlayerScreen(navController = navController) }
        composable(
            route = Screens.SearchScreen.route,

        ) { SearchScreen(navController = navController) }
        composable(
            route = Screens.SettingsScreen.route,

        ) { SettingsScreen(navController = navController) }

        composable(
            route = SettingsScreens.LooksScreen.route
        ) { LooksScreen(navController = navController) }
        composable(
            route = SettingsScreens.AboutScreen.route
        ) { AboutScreen(navController = navController) }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationBottomNavBar(
    navControllerBottomBar: NavHostController,
    navControllerApp: NavController
) {
    AnimatedNavHost(navController = navControllerBottomBar, startDestination = "home") {
        composable(
            "home"
        ) { HomeScreen(navController = navControllerApp) }
        composable(
            "tracks"
        ) { TracksScreen(navController = navControllerApp) }
        composable(
            "albums"
        ) { AlbumsScreen(navController = navControllerApp) }
        composable(
            "playlists"
        ) { PlaylistsScreen(navController = navControllerApp) }
        composable(
            "spotify"
        ) { SpotifyFavoritesScreen(navController = navControllerApp) }
    }
}