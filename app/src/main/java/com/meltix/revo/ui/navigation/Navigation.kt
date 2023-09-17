package com.meltix.revo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.meltix.revo.ui.screens.MainScreen
import com.meltix.revo.ui.screens.library.albumScreen.AlbumsScreen
import com.meltix.revo.ui.screens.library.artistScreen.ArtistsScreen
import com.meltix.revo.ui.screens.library.playlistScreen.PlaylistsScreen
import com.meltix.revo.ui.screens.library.songScreen.SongsScreen
import com.meltix.revo.ui.screens.player.PlayerScreen
import com.meltix.revo.ui.screens.welcome.WelcomeScreen

@Composable
fun RootNavigation(
    startDestination: String,
    nestedGraphStartDestination: String? = null,
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = startDestination) {
        // Welcome screen
        composable(
            route = RootScreens.Welcome.route,
            exitTransition = {
                when (targetState.destination.route) {
                    RootScreens.Main.route -> horSlideExitToScreen()
                    else -> null
                }
            }
        ) { WelcomeScreen(navController) }

        // Main screen
        composable(
            route = RootScreens.Main.route,
            enterTransition = {
                when (initialState.destination.route) {
                    RootScreens.Welcome.route -> horSlideEnterFromScreen()
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    RootScreens.Search.route -> horSlideExitToScreen()
                    SettingsScreens.MainSettings.route -> horSlideExitToScreen()
                    RootScreens.Player.route -> verSlidePopExitFromPlayer()
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    RootScreens.Search.route -> horSlidePopEnterFromScreen()
                    SettingsScreens.MainSettings.route -> horSlidePopEnterFromScreen()
                    RootScreens.Player.route -> verSlidePopEnterFromPlayer()
                    else -> null
                }
            }
        ) { MainScreen(navController = navController) }

        // Player screen
        composable(
            route = RootScreens.Player.route,
            enterTransition = {
                when (initialState.destination.route) {
                    RootScreens.Main.route -> verSlideEnterToPlayer()
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    RootScreens.Main.route -> verSlideExitFromPlayer()
                    RootScreens.SettingsGraph.route -> horSlideExitToScreen()
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    RootScreens.Main.route -> verSlideEnterToPlayer()
                    RootScreens.SettingsGraph.route -> horSlidePopEnterFromScreen()
                    else -> null
                }
            }
        ) { PlayerScreen(navController = navController) }

        settingsGraph(navController, nestedGraphStartDestination)
    }
}

@Composable
fun LibraryNavigation(
    libraryNavController: NavHostController,
    rootNavController: NavController
) {
    NavHost(navController = libraryNavController, startDestination = LibraryScreens.Songs.route) {
        composable(
            route = LibraryScreens.Songs.route,
            enterTransition = {
                when(initialState.destination.route) {
                    LibraryScreens.Songs.route -> verSlideEnterFromFragment()
                    LibraryScreens.Albums.route -> verSlideEnterFromFragment()
                    LibraryScreens.Artists.route -> verSlideEnterFromFragment()
                    LibraryScreens.Playlists.route -> verSlideEnterFromFragment()

                    DetailsScreens.AlbumDetails.route + "/{albumId}" -> verSlideEnterFromFragment()
                    DetailsScreens.ArtistDetails.route + "/{artistId}" -> verSlideEnterFromFragment()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    LibraryScreens.Songs.route -> fadeExitToFragment()
                    LibraryScreens.Albums.route -> fadeExitToFragment()
                    LibraryScreens.Artists.route -> fadeExitToFragment()
                    LibraryScreens.Playlists.route -> fadeExitToFragment()

                    DetailsScreens.AlbumDetails.route + "/{albumId}" -> verSlideExitFromSubFragment()
                    DetailsScreens.ArtistDetails.route + "/{artistId}" -> verSlideExitFromSubFragment()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    LibraryScreens.Songs.route -> verSlideEnterFromFragment()
                    LibraryScreens.Albums.route -> verSlideEnterFromFragment()
                    LibraryScreens.Artists.route -> verSlideEnterFromFragment()
                    LibraryScreens.Playlists.route -> verSlideEnterFromFragment()

                    DetailsScreens.AlbumDetails.route + "/{albumId}" -> verSlideEnterFromFragment()
                    DetailsScreens.ArtistDetails.route + "/{artistId}" -> verSlideEnterFromFragment()
                    else -> null
                }
            }
        ) {
            SongsScreen(
                rootNavController = rootNavController,
                libraryNavController = libraryNavController,
            )
        }

        composable(
            route = LibraryScreens.Albums.route,
            enterTransition = {
                when(initialState.destination.route) {
                    LibraryScreens.Songs.route -> verSlideEnterFromFragment()
                    LibraryScreens.Albums.route -> verSlideEnterFromFragment()
                    LibraryScreens.Artists.route -> verSlideEnterFromFragment()
                    LibraryScreens.Playlists.route -> verSlideEnterFromFragment()

                    DetailsScreens.AlbumDetails.route + "/{albumId}" -> verSlideEnterFromFragment()
                    DetailsScreens.ArtistDetails.route + "/{artistId}" -> verSlideEnterFromFragment()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    LibraryScreens.Songs.route -> fadeExitToFragment()
                    LibraryScreens.Albums.route -> fadeExitToFragment()
                    LibraryScreens.Artists.route -> fadeExitToFragment()
                    LibraryScreens.Playlists.route -> fadeExitToFragment()

                    DetailsScreens.AlbumDetails.route + "/{albumId}" -> verSlideExitFromSubFragment()
                    DetailsScreens.ArtistDetails.route + "/{artistId}" -> verSlideExitFromSubFragment()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    LibraryScreens.Songs.route -> verSlideEnterFromFragment()
                    LibraryScreens.Albums.route -> verSlideEnterFromFragment()
                    LibraryScreens.Artists.route -> verSlideEnterFromFragment()
                    LibraryScreens.Playlists.route -> verSlideEnterFromFragment()

                    DetailsScreens.AlbumDetails.route + "/{albumId}" -> verSlideEnterFromFragment()
                    DetailsScreens.ArtistDetails.route + "/{artistId}" -> verSlideEnterFromFragment()
                    else -> null
                }
            }
        ) {
            AlbumsScreen(
                rootNavController = rootNavController,
                libraryNavController = libraryNavController
            )
        }

        composable(
            route = LibraryScreens.Artists.route,
            enterTransition = {
                when(initialState.destination.route) {
                    LibraryScreens.Songs.route -> verSlideEnterFromFragment()
                    LibraryScreens.Albums.route -> verSlideEnterFromFragment()
                    LibraryScreens.Artists.route -> verSlideEnterFromFragment()
                    LibraryScreens.Playlists.route -> verSlideEnterFromFragment()

                    DetailsScreens.PlaylistDetails.route -> verSlideEnterFromFragment()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    LibraryScreens.Songs.route -> fadeExitToFragment()
                    LibraryScreens.Albums.route -> fadeExitToFragment()
                    LibraryScreens.Artists.route -> fadeExitToFragment()
                    LibraryScreens.Playlists.route -> fadeExitToFragment()

                    DetailsScreens.ArtistDetails.route + "/{artistId}" -> verSlideExitFromSubFragment()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    LibraryScreens.Songs.route -> verSlideEnterFromFragment()
                    LibraryScreens.Albums.route -> verSlideEnterFromFragment()
                    LibraryScreens.Artists.route -> verSlideEnterFromFragment()
                    LibraryScreens.Playlists.route -> verSlideEnterFromFragment()

                    DetailsScreens.ArtistDetails.route + "/{artistId}" -> verSlideEnterFromFragment()
                    else -> null
                }
            }
        ) {
            ArtistsScreen(
                rootNavController = rootNavController,
                libraryNavController = libraryNavController
            )
        }

        composable(
            route = LibraryScreens.Playlists.route,
            enterTransition = {
                when(initialState.destination.route) {
                    LibraryScreens.Songs.route -> verSlideEnterFromFragment()
                    LibraryScreens.Albums.route -> verSlideEnterFromFragment()
                    LibraryScreens.Artists.route -> verSlideEnterFromFragment()
                    LibraryScreens.Playlists.route -> verSlideEnterFromFragment()

                    DetailsScreens.AlbumDetails.route + "/{albumId}" -> verSlideEnterFromFragment()
                    DetailsScreens.ArtistDetails.route + "/{albumId}" -> verSlideEnterFromFragment()
                    DetailsScreens.PlaylistDetails.route -> verSlideEnterFromFragment()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    LibraryScreens.Songs.route -> fadeExitToFragment()
                    LibraryScreens.Albums.route -> fadeExitToFragment()
                    LibraryScreens.Artists.route -> fadeExitToFragment()
                    LibraryScreens.Playlists.route -> fadeExitToFragment()

                    DetailsScreens.PlaylistDetails.route -> verSlideExitFromSubFragment()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    LibraryScreens.Songs.route -> verSlideEnterFromFragment()
                    LibraryScreens.Albums.route -> verSlideEnterFromFragment()
                    LibraryScreens.Artists.route -> verSlideEnterFromFragment()
                    LibraryScreens.Playlists.route -> verSlideEnterFromFragment()

                    DetailsScreens.PlaylistDetails.route -> verSlideEnterFromFragment()
                    else -> null
                }
            }
        ) {
            PlaylistsScreen(
                rootNavController = rootNavController,
                libraryNavController = libraryNavController
            )
        }

        detailsGraph(libraryNavController, rootNavController)
    }
}