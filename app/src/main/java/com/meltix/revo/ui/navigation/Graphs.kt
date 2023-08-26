package com.meltix.revo.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.meltix.revo.ui.screens.library.albumScreen.albumDetailsScreen.AlbumViewScreen
import com.meltix.revo.ui.screens.library.artistScreen.artistViewScreen.ArtistViewScreen
import com.meltix.revo.ui.screens.library.playlistScreen.playlistViewScreen.PlaylistViewScreen
import com.meltix.revo.ui.screens.settings.SettingsScreen
import com.meltix.revo.ui.screens.settings.about.AboutScreen
import com.meltix.revo.ui.screens.settings.customization.CustomizationScreen
import com.meltix.revo.ui.screens.settings.customization.albumDetailsLayout.AlbumViewLayoutScreen
import com.meltix.revo.ui.screens.settings.customization.playerLayout.PlayerLayoutScreen
import com.meltix.revo.ui.screens.settings.library.LibrarySettingsScreen
import com.meltix.revo.ui.screens.settings.other.OtherScreen
import com.meltix.revo.ui.screens.settings.other.appLanguage.AppLanguageScreen

fun NavGraphBuilder.settingsGraph(navController: NavController) {
    navigation(
        startDestination = SettingsScreens.MainSettings.route,
        route = RootScreens.SettingsGraph.route
    ) {
        composable(
            route = SettingsScreens.MainSettings.route,
            enterTransition = {
                when (initialState.destination.route) {
                    RootScreens.Main.route -> horSlideEnterFromScreen()
                    RootScreens.Player.route -> horSlideEnterFromScreen()
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    RootScreens.Main.route -> horSlidePopExitToScreen()
                    RootScreens.Player.route -> horSlidePopExitToScreen()
                    SettingsScreens.Library.route -> horSlideExitToScreen()
                    SettingsScreens.Customization.route -> horSlideExitToScreen()
                    SettingsScreens.Other.route -> horSlideExitToScreen()
                    SettingsScreens.About.route -> horSlideExitToScreen()
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    SettingsScreens.Library.route -> horSlidePopEnterFromScreen()
                    SettingsScreens.Customization.route -> horSlidePopEnterFromScreen()
                    SettingsScreens.Other.route -> horSlidePopEnterFromScreen()
                    SettingsScreens.About.route -> horSlidePopEnterFromScreen()
                    else -> null
                }
            }
        ) { SettingsScreen(navController = navController) }

        composable(
            route = SettingsScreens.Library.route,
            enterTransition = {
                when (initialState.destination.route) {
                    SettingsScreens.MainSettings.route -> horSlideEnterFromScreen()
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    SettingsScreens.MainSettings.route -> horSlidePopExitToScreen()
                    else -> null
                }
            },
            //TODO Add popEnter when actually listing items
        ) { LibrarySettingsScreen(navController = navController) }


        composable(
            route = SettingsScreens.Customization.route,
            enterTransition = {
                when (initialState.destination.route) {
                    SettingsScreens.MainSettings.route -> horSlideEnterFromScreen()
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    SettingsScreens.MainSettings.route -> horSlidePopExitToScreen()
                    SettingsScreens.AlbumViewLayout.route -> horSlideExitToScreen()
                    SettingsScreens.PlayerLayout.route -> horSlideExitToScreen()
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    SettingsScreens.AlbumViewLayout.route -> horSlidePopEnterFromScreen()
                    SettingsScreens.PlayerLayout.route -> horSlidePopEnterFromScreen()
                    else -> null
                }
            }
        ) { CustomizationScreen(navController = navController) }

        composable(
            route = SettingsScreens.PlayerLayout.route,
            enterTransition = {
                when (initialState.destination.route) {
                    SettingsScreens.Customization.route -> horSlideEnterFromScreen()
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    SettingsScreens.Customization.route -> horSlidePopExitToScreen()
                    else -> null
                }
            }
        ) { PlayerLayoutScreen(navController = navController) }

        composable(
            route = SettingsScreens.AlbumViewLayout.route,
            enterTransition = {
                when (initialState.destination.route) {
                    SettingsScreens.Customization.route -> horSlideEnterFromScreen()
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    SettingsScreens.Customization.route -> horSlidePopExitToScreen()
                    else -> null
                }
            }
        ) { AlbumViewLayoutScreen(navController = navController) }

        composable(
            route = SettingsScreens.Other.route,
            enterTransition = {
                when (initialState.destination.route) {
                    SettingsScreens.MainSettings.route -> horSlideEnterFromScreen()
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    SettingsScreens.MainSettings.route -> horSlidePopExitToScreen()
                    SettingsScreens.AppLanguage.route -> horSlideExitToScreen()
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    SettingsScreens.AppLanguage.route -> horSlidePopEnterFromScreen()
                    else -> null
                }
            }
        ) { OtherScreen(navController = navController) }

        composable(
            route = SettingsScreens.AppLanguage.route,
            enterTransition = {
                when (initialState.destination.route) {
                    SettingsScreens.Other.route -> horSlideEnterFromScreen()
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    SettingsScreens.Other.route -> horSlidePopExitToScreen()
                    else -> null
                }
            }
        ) { AppLanguageScreen(navController = navController) }


        composable(
            route = SettingsScreens.About.route,
            enterTransition = {
                when (initialState.destination.route) {
                    SettingsScreens.MainSettings.route -> horSlideEnterFromScreen()
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    SettingsScreens.MainSettings.route -> horSlidePopExitToScreen()
                    else -> null
                }
            }
            //TODO Add popEnter when actually listing items
        ) { AboutScreen(navController = navController) }
    }
}

fun NavGraphBuilder.detailsGraph(
    libraryNavController: NavController,
    rootNavController: NavController
) {
    navigation(
        startDestination = DetailsScreens.AlbumDetails.route,
        route = LibraryScreens.DetailsGraph.route
    ) {
        composable(
            route = DetailsScreens.AlbumDetails.route + "/{albumId}",
            enterTransition = {
                when(initialState.destination.route) {
                    LibraryScreens.Songs.route -> verSlideEnterFromFragment()
                    LibraryScreens.Albums.route -> verSlideEnterFromFragment()
                    LibraryScreens.Artists.route -> verSlideEnterFromFragment()
                    LibraryScreens.Playlists.route -> verSlideEnterFromFragment()
                    LibraryScreens.Spotify.route -> verSlideEnterFromFragment()

                    DetailsScreens.ArtistDetails.route + "/{artistId}" -> horSlidePopEnterFromScreen()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    LibraryScreens.Songs.route -> verSlideExitFromSubFragment()
                    LibraryScreens.Albums.route -> verSlideExitFromSubFragment()
                    LibraryScreens.Artists.route -> verSlideExitFromSubFragment()
                    LibraryScreens.Playlists.route -> verSlideExitFromSubFragment()
                    LibraryScreens.Spotify.route -> verSlideExitFromSubFragment()

                    DetailsScreens.ArtistDetails.route + "/{artistId}" -> horSlideExitToScreen()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    DetailsScreens.ArtistDetails.route + "/{artistId}" -> horSlidePopEnterFromScreen()
                    else -> null
                }
            }
        ) {backStackEntry ->
            val albumId = backStackEntry.arguments?.getString("albumId")?.toLong()

            if (albumId != null) {
                AlbumViewScreen(
                    albumId = albumId,
                    rootNavController = rootNavController,
                    libraryNavController = libraryNavController
                )
            }
        }

        composable(
            route = DetailsScreens.ArtistDetails.route + "/{artistId}",
            enterTransition = {
                when(initialState.destination.route) {
                    LibraryScreens.Songs.route -> verSlideEnterFromFragment()
                    LibraryScreens.Artists.route -> verSlideEnterFromFragment()

                    DetailsScreens.AlbumDetails.route + "/{albumId}" -> horSlideEnterFromScreen()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    LibraryScreens.Songs.route -> verSlideExitFromSubFragment()
                    LibraryScreens.Albums.route -> verSlideExitFromSubFragment()
                    LibraryScreens.Artists.route -> verSlideExitFromSubFragment()
                    LibraryScreens.Playlists.route -> verSlideExitFromSubFragment()
                    LibraryScreens.Spotify.route -> verSlideExitFromSubFragment()

                    DetailsScreens.AlbumDetails.route + "/{albumId}" -> horSlidePopExitToScreen()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    DetailsScreens.AlbumDetails.route + "/{albumId}" -> horSlideEnterFromScreen()
                    else -> null
                }
            }
        ) {backStackEntry ->
            val artistId = backStackEntry.arguments?.getString("artistId")?.toLong()

            if (artistId != null) {
                ArtistViewScreen(
                    artistId = artistId,
                    rootNavController = rootNavController,
                    libraryNavController = libraryNavController
                )
            }
        }

        // Playlist SubScreens
        composable(
            route = DetailsScreens.PlaylistDetails.route,
            enterTransition = {
                when(initialState.destination.route) {
                    LibraryScreens.Playlists.route -> verSlideEnterFromFragment()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    LibraryScreens.Songs.route -> verSlideExitFromSubFragment()
                    LibraryScreens.Albums.route -> verSlideExitFromSubFragment()
                    LibraryScreens.Artists.route -> verSlideExitFromSubFragment()
                    LibraryScreens.Playlists.route -> verSlideExitFromSubFragment()
                    LibraryScreens.Spotify.route -> verSlideExitFromSubFragment()
                    else -> null
                }
            }
        ) {
            PlaylistViewScreen(
                rootNavController = rootNavController,
                libraryNavController = libraryNavController
            )
        }
    }
}