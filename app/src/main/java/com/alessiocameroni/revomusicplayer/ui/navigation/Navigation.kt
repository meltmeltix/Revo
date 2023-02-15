package com.alessiocameroni.revomusicplayer.ui.navigation

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.alessiocameroni.revomusicplayer.ui.screens.library.albumScreen.albumViewScreen.AlbumViewScreen
import com.alessiocameroni.revomusicplayer.ui.screens.library.albumScreen.AlbumsScreen
import com.alessiocameroni.revomusicplayer.ui.screens.library.artistScreen.artistViewScreen.ArtistViewScreen
import com.alessiocameroni.revomusicplayer.ui.screens.library.artistScreen.ArtistsScreen
import com.alessiocameroni.revomusicplayer.ui.screens.library.playlistScreen.PlaylistsScreen
import com.alessiocameroni.revomusicplayer.ui.screens.library.playlistScreen.playlistViewScreen.PlaylistViewScreen
import com.alessiocameroni.revomusicplayer.ui.screens.library.songScreen.SongsScreen
import com.alessiocameroni.revomusicplayer.ui.screens.library.spotifyScreen.SpotifyFavoritesScreen
import com.alessiocameroni.revomusicplayer.ui.screens.MainScreen
import com.alessiocameroni.revomusicplayer.ui.screens.player.PlayerScreen
import com.alessiocameroni.revomusicplayer.ui.screens.search.SearchScreen
import com.alessiocameroni.revomusicplayer.ui.screens.settings.about.AboutScreen
import com.alessiocameroni.revomusicplayer.ui.screens.settings.customization.CustomizationScreen
import com.alessiocameroni.revomusicplayer.ui.screens.settings.customization.playerLayout.PlayerLayoutScreen
import com.alessiocameroni.revomusicplayer.ui.screens.settings.library.LibrarySettingsScreen
import com.alessiocameroni.revomusicplayer.ui.screens.settings.SettingsScreen
import com.alessiocameroni.revomusicplayer.ui.screens.settings.other.OtherScreen
import com.alessiocameroni.revomusicplayer.ui.screens.settings.other.appLanguage.AppLanguageScreen
import com.alessiocameroni.revomusicplayer.ui.screens.welcome.WelcomeScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(startDestination: String) {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController = navController, startDestination = startDestination) {
        composable(
            route = Screens.WelcomeScreen.route,
            exitTransition = {
                when(targetState.destination.route) {
                    "main_screen" -> horSlideExitToScreen()
                    else -> null
                }
            }
        ) { WelcomeScreen(navController) }

        composable(
            route = Screens.MainScreen.route,
            enterTransition = {
                when(initialState.destination.route) {
                    "welcome_screen" -> horSlideEnterFromScreen()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    "search_screen" -> horSlideExitToScreen()
                    "settings_screen" -> horSlideExitToScreen()
                    "player_screen" -> verSlidePopExitFromPlayer()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    "search_screen" -> horSlidePopEnterFromScreen()
                    "settings_screen" -> horSlidePopEnterFromScreen()
                    "player_screen" -> verSlidePopEnterFromPlayer()
                    else -> null
                }
            }
        ) { MainScreen(navController = navController) }

        composable(
            route = Screens.PlayerScreen.route,
            enterTransition = {
                when(initialState.destination.route) {
                    "main_screen" -> verSlideEnterToPlayer()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    "main_screen" -> verSlideExitFromPlayer()
                    "settings_screen" -> horSlideExitToScreen()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    "main_screen" -> verSlideEnterToPlayer()
                    "settings_screen" -> horSlidePopEnterFromScreen()
                    else -> null
                }
            }
        ) { PlayerScreen(navController = navController) }

        composable(
            route = Screens.SearchScreen.route,
            enterTransition = {
                when(initialState.destination.route) {
                    "main_screen" -> horSlideEnterFromScreen()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    "main_screen" -> horSlidePopExitToScreen()
                    else -> null
                }
            },
            //TODO Add popEnter when actually listing items
        ) { SearchScreen(navController = navController) }

        composable(
            route = Screens.SettingsScreen.route,
            enterTransition = {
                when(initialState.destination.route) {
                    "main_screen" -> horSlideEnterFromScreen()
                    "player_screen" -> horSlideEnterFromScreen()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    "main_screen" -> horSlidePopExitToScreen()
                    "player_screen" -> horSlidePopExitToScreen()
                    "library_settings_screen" -> horSlideExitToScreen()
                    "customization_screen" -> horSlideExitToScreen()
                    "other_screen" -> horSlideExitToScreen()
                    "about_screen" -> horSlideExitToScreen()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    "library_settings_screen" -> horSlidePopEnterFromScreen()
                    "customization_screen" -> horSlidePopEnterFromScreen()
                    "other_screen" -> horSlidePopEnterFromScreen()
                    "about_screen" -> horSlidePopEnterFromScreen()
                    else -> null
                }
            }
        ) { SettingsScreen(navController = navController) }


        // Settings SubScreens
        composable(
            route = SettingsScreens.LibrarySettingsScreen.route,
            enterTransition = {
                when(initialState.destination.route) {
                    "settings_screen" -> horSlideEnterFromScreen()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    "settings_screen" -> horSlidePopExitToScreen()
                    else -> null
                }
            },
            //TODO Add popEnter when actually listing items
        ) { LibrarySettingsScreen(navController = navController) }


        composable(
            route = SettingsScreens.CustomizationScreen.route,
            enterTransition = {
                when(initialState.destination.route) {
                    "settings_screen" -> horSlideEnterFromScreen()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    "settings_screen" -> horSlidePopExitToScreen()
                    "player_layout_screen" -> horSlideExitToScreen()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    "player_layout_screen" -> horSlidePopEnterFromScreen()
                    else -> null
                }
            }
        ) { CustomizationScreen(navController = navController) }

        composable(
            route = SettingsScreens.PlayerLayoutScreen.route,
            enterTransition = {
                when(initialState.destination.route) {
                    "customization_screen" -> horSlideEnterFromScreen()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    "customization_screen" -> horSlidePopExitToScreen()
                    else -> null
                }
            }
        ) { PlayerLayoutScreen(navController = navController) }


        composable(
            route = SettingsScreens.OtherScreen.route,
            enterTransition = {
                when(initialState.destination.route) {
                    "settings_screen" -> horSlideEnterFromScreen()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    "settings_screen" -> horSlidePopExitToScreen()
                    "app_language_screen"-> horSlideExitToScreen()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    "app_language_screen" -> horSlidePopEnterFromScreen()
                    else -> null
                }
            }
        ) { OtherScreen(navController = navController) }

        composable(
            route = SettingsScreens.AppLanguageScreen.route,
            enterTransition = {
                when(initialState.destination.route) {
                    "other_screen" -> horSlideEnterFromScreen()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    "other_screen" -> horSlidePopExitToScreen()
                    else -> null
                }
            }
        ) { AppLanguageScreen(navController = navController) }


        composable(
            route = SettingsScreens.AboutScreen.route,
            enterTransition = {
                when(initialState.destination.route) {
                    "settings_screen" -> horSlideEnterFromScreen()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    "settings_screen" -> horSlidePopExitToScreen()
                    else -> null
                }
            }
            //TODO Add popEnter when actually listing items
        ) { AboutScreen(navController = navController) }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationBottomNavBar(
    navControllerBottomBar: NavHostController,
    navControllerApp: NavController
) {
    AnimatedNavHost(navController = navControllerBottomBar, startDestination = "songs") {
        composable(
            route = "songs",
            enterTransition = {
                when(initialState.destination.route) {
                    "songs" -> verSlideEnterFromFragmentTransition()
                    "albums" -> verSlideEnterFromFragmentTransition()
                    "artists" -> verSlideEnterFromFragmentTransition()
                    "playlists" -> verSlideEnterFromFragmentTransition()
                    "spotify" -> verSlideEnterFromFragmentTransition()

                    "album_view_screen/{albumId}" -> verSlideEnterFromFragmentTransition()
                    "artist_view_screen" -> verSlideEnterFromFragmentTransition()
                    "playlist_view_screen" -> verSlideEnterFromFragmentTransition()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    "songs" -> fadeExitToFragmentTransition()
                    "albums" -> fadeExitToFragmentTransition()
                    "artists" -> fadeExitToFragmentTransition()
                    "playlists" -> fadeExitToFragmentTransition()
                    "spotify" -> fadeExitToFragmentTransition()
                    "artist_view_screen" -> verSlideExitFromSubFragmentTransition()
                    else -> null
                }
            }
        ) {
            SongsScreen(
                navController = navControllerApp,
                navControllerBottomBar = navControllerBottomBar,
            )
        }

        composable(
            route = "albums",
            enterTransition = {
                when(initialState.destination.route) {
                    "songs" -> verSlideEnterFromFragmentTransition()
                    "albums" -> verSlideEnterFromFragmentTransition()
                    "artists" -> verSlideEnterFromFragmentTransition()
                    "playlists" -> verSlideEnterFromFragmentTransition()
                    "spotify" -> verSlideEnterFromFragmentTransition()


                    //From screen
                    "album_view_screen/{albumId}" -> verSlideEnterFromFragmentTransition()
                    "artist_view_screen" -> verSlideEnterFromFragmentTransition()
                    "playlist_view_screen" -> verSlideEnterFromFragmentTransition()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    "songs" -> fadeExitToFragmentTransition()
                    "albums" -> fadeExitToFragmentTransition()
                    "artists" -> fadeExitToFragmentTransition()
                    "playlists" -> fadeExitToFragmentTransition()
                    "spotify" -> fadeExitToFragmentTransition()
                    "album_view_screen/{albumId}" -> verSlideExitFromSubFragmentTransition()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    // From screen
                    "album_view_screen/{albumId}" -> verSlideEnterFromFragmentTransition()
                    else -> null
                }
            }
        ) {
            AlbumsScreen(
                navController = navControllerApp,
                navControllerBottomBar = navControllerBottomBar
            )
        }

        // Album SubScreens
        composable(
            route = "album_view_screen/{albumId}",
            enterTransition = {
                when(initialState.destination.route) {
                    "albums" -> verSlideEnterFromFragmentTransition()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    // To screen
                    "songs" ->verSlideExitFromSubFragmentTransition()
                    "albums" ->verSlideExitFromSubFragmentTransition()
                    "artists" ->verSlideExitFromSubFragmentTransition()
                    "playlists" ->verSlideExitFromSubFragmentTransition()
                    "spotify" ->verSlideExitFromSubFragmentTransition()
                    else -> null
                }
            }
        ) {backStackEntry ->
            val albumId = backStackEntry.arguments?.getString("albumId")?.toLong()

            if (albumId != null) {
                AlbumViewScreen(
                    albumId = albumId,
                    navController = navControllerApp,
                    navControllerBottomBar = navControllerBottomBar
                )
            }
        }


        composable(
            route = "artists",
            enterTransition = {
                when(initialState.destination.route) {
                    "songs" -> verSlideEnterFromFragmentTransition()
                    "albums" -> verSlideEnterFromFragmentTransition()
                    "artists" -> verSlideEnterFromFragmentTransition()
                    "playlists" -> verSlideEnterFromFragmentTransition()
                    "spotify" -> verSlideEnterFromFragmentTransition()


                    //From screen
                    "album_view_screen/{albumId}" -> verSlideEnterFromFragmentTransition()
                    "artist_view_screen" -> verSlideEnterFromFragmentTransition()
                    "playlist_view_screen" -> verSlideEnterFromFragmentTransition()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    "songs" -> fadeExitToFragmentTransition()
                    "albums" -> fadeExitToFragmentTransition()
                    "artists" -> fadeExitToFragmentTransition()
                    "playlists" -> fadeExitToFragmentTransition()
                    "spotify" -> fadeExitToFragmentTransition()
                    "artist_view_screen" -> verSlideExitFromSubFragmentTransition()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    // From screen
                    "artist_view_screen" -> verSlideEnterFromFragmentTransition()
                    else -> null
                }
            }
        ) {
            ArtistsScreen(
                navController = navControllerApp,
                navControllerBottomBar = navControllerBottomBar
            )
        }

        // Artist SubScreens
        composable(
            route = "artist_view_screen",
            enterTransition = {
                when(initialState.destination.route) {
                    "songs" -> verSlideEnterFromFragmentTransition()
                    "artists" -> verSlideEnterFromFragmentTransition()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    // To screen
                    "songs" ->verSlideExitFromSubFragmentTransition()
                    "albums" ->verSlideExitFromSubFragmentTransition()
                    "artists" ->verSlideExitFromSubFragmentTransition()
                    "playlists" ->verSlideExitFromSubFragmentTransition()
                    "spotify" ->verSlideExitFromSubFragmentTransition()
                    else -> null
                }
            }
        ) {
            ArtistViewScreen(
                navController = navControllerApp,
                navControllerBottomBar = navControllerBottomBar
            )
        }

        composable(
            route = "playlists",
            enterTransition = {
                when(initialState.destination.route) {
                    // To screen
                    "songs" -> verSlideEnterFromFragmentTransition()
                    "albums" -> verSlideEnterFromFragmentTransition()
                    "artists" -> verSlideEnterFromFragmentTransition()
                    "playlists" -> verSlideEnterFromFragmentTransition()
                    "spotify" -> verSlideEnterFromFragmentTransition()

                    //From screen
                    "album_view_screen/{albumId}" -> verSlideEnterFromFragmentTransition()
                    "artist_view_screen" -> verSlideEnterFromFragmentTransition()
                    "playlist_view_screen" -> verSlideEnterFromFragmentTransition()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    // To screen
                    "songs" -> fadeExitToFragmentTransition()
                    "albums" -> fadeExitToFragmentTransition()
                    "artists" -> fadeExitToFragmentTransition()
                    "playlists" -> fadeExitToFragmentTransition()
                    "spotify" -> fadeExitToFragmentTransition()
                    "playlist_view_screen" -> verSlideExitFromSubFragmentTransition()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    // From screen
                    "playlist_view_screen" -> verSlideEnterFromFragmentTransition()
                    else -> null
                }
            }
        ) {
            PlaylistsScreen(
                navController = navControllerApp,
                navControllerBottomBar = navControllerBottomBar
            )
        }

        // Playlist SubScreens
        composable(
            route = "playlist_view_screen",
            enterTransition = {
                when(initialState.destination.route) {
                    "playlists" -> verSlideEnterFromFragmentTransition()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    // To screen
                    "songs" -> verSlideExitFromSubFragmentTransition()
                    "albums" -> verSlideExitFromSubFragmentTransition()
                    "artists" -> verSlideExitFromSubFragmentTransition()
                    "playlists" -> verSlideExitFromSubFragmentTransition()
                    "spotify" -> verSlideExitFromSubFragmentTransition()
                    else -> null
                }
            }
        ) {
            PlaylistViewScreen(
                navController = navControllerApp,
                navControllerBottomBar = navControllerBottomBar
            )
        }


        composable(
            route = "spotify",
            enterTransition = {
                when(initialState.destination.route) {
                    "songs" -> verSlideEnterFromFragmentTransition()
                    "albums" -> verSlideEnterFromFragmentTransition()
                    "artists" -> verSlideEnterFromFragmentTransition()
                    "playlists" -> verSlideEnterFromFragmentTransition()
                    "spotify" -> verSlideEnterFromFragmentTransition()

                    "album_view_screen/{albumId}" -> verSlideEnterFromFragmentTransition()
                    "artist_view_screen" -> verSlideEnterFromFragmentTransition()
                    "playlist_view_screen" -> verSlideEnterFromFragmentTransition()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    "songs" -> fadeExitToFragmentTransition()
                    "albums" -> fadeExitToFragmentTransition()
                    "artists" -> fadeExitToFragmentTransition()
                    "playlists" -> fadeExitToFragmentTransition()
                    "spotify" -> fadeExitToFragmentTransition()
                    else -> null
                }
            }
        ) { SpotifyFavoritesScreen(navController = navControllerApp) }
    }
}