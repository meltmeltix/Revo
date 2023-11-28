package com.meltix.revo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.meltix.revo.data.classes.LibraryNavigationItem
import com.meltix.revo.ui.screens.MainScreen
import com.meltix.revo.ui.screens.library.albumScreen.AlbumsScreen
import com.meltix.revo.ui.screens.library.artistScreen.ArtistsScreen
import com.meltix.revo.ui.screens.library.playlistScreen.PlaylistsScreen
import com.meltix.revo.ui.screens.library.songScreen.SongsScreen

@Composable
fun RootNavigation(
    destinationsList: List<LibraryNavigationItem>,
    startDestination: String,
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = startDestination) {
        // Main screen
        composable(
            route = RootScreens.Main.route,
        ) {
            MainScreen(
                destinationsList = destinationsList,
                navController = navController
            )
        }
    }
}

@Composable
fun LibraryNavigation(
    startDestination: String,
    rootNavController: NavController,
    libraryNavController: NavHostController
) {
    NavHost(navController = libraryNavController, startDestination = startDestination) {
        composable(
            route = LibraryScreens.Songs.route,
        ) {
            SongsScreen(
                rootNavController = rootNavController,
                libraryNavController = libraryNavController
            )
        }
    
        composable(
            route = LibraryScreens.Albums.route,
        ) {
            AlbumsScreen(
                rootNavController = rootNavController,
                libraryNavController = libraryNavController
            )
        }
    
        composable(
            route = LibraryScreens.Artists.route,
        ) {
            ArtistsScreen(
                rootNavController = rootNavController,
                libraryNavController = libraryNavController
            )
        }
    
        composable(
            route = LibraryScreens.Playlists.route,
        ) {
            PlaylistsScreen(
                rootNavController = rootNavController,
                libraryNavController = libraryNavController
            )
        }
    }
}