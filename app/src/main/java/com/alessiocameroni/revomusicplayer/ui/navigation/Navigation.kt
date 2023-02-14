package com.alessiocameroni.revomusicplayer.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
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
                    // To screen
                    "main_screen" -> horExitScreenTransition()
                    else -> null
                }
            }
        ) { WelcomeScreen(navController) }

        composable(
            route = Screens.MainScreen.route,
            exitTransition = {
                when(targetState.destination.route) {
                    // To screen
                    "search_screen" -> horExitScreenTransition()
                    "settings_screen" -> horExitScreenTransition()
                    "player_screen" ->horExitPlayerTransition()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    // From screen
                    "welcome_screen" -> horEnterScreenTransition()
                    "search_screen" -> horEnterScreenTransition()
                    "settings_screen" -> horEnterScreenTransition()
                    "player_screen" -> horEnterPlayerTransition()
                    else -> null
                }
            }
        ) { MainScreen(navController = navController) }

        composable(
            route = Screens.PlayerScreen.route,
            enterTransition = {
                when(initialState.destination.route) {
                    // From screen
                    "main_screen" ->
                        slideInVertically(
                            initialOffsetY = { 200 },
                            animationSpec = tween( 240 )
                        ) + fadeIn(animationSpec = tween( 260 ))
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    // To screen
                    "main_screen" ->
                        slideOutVertically (
                            targetOffsetY = { 200 },
                            animationSpec = tween( 240 )
                        ) + fadeOut(animationSpec = tween( 260 ))

                    // From screen
                    "settings_screen" -> horExitScreenTransition()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    // To screen
                    "settings_screen" -> horEnterScreenTransition()
                    else -> null
                }
            }
        ) { PlayerScreen(navController = navController) }

        composable(
            route = Screens.SearchScreen.route,
            enterTransition = {
                when(initialState.destination.route) {
                    "main_screen" ->
                        slideInHorizontally(
                            initialOffsetX = { 100 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    // To screen
                    "main_screen" ->
                        slideOutHorizontally(
                            targetOffsetX = { 100 },
                            animationSpec = tween(210)
                        ) + fadeOut(animationSpec = tween(210))
                    else -> null
                }
            },
            //TODO Add popEnter when actually listing items
        ) { SearchScreen(navController = navController) }

        composable(
            route = Screens.SettingsScreen.route,
            enterTransition = {
                when(initialState.destination.route) {
                    // From screen
                    "main_screen" ->
                        slideInHorizontally(
                            initialOffsetX = { 100 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "player_screen" ->
                        slideInHorizontally(
                            initialOffsetX = { 100 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    // To screen
                    "main_screen" ->
                        slideOutHorizontally(
                            targetOffsetX = { 100 },
                            animationSpec = tween( 210 )
                        ) + fadeOut(animationSpec = tween( 210 ))
                    "player_screen" ->
                        slideOutHorizontally(
                            targetOffsetX = { 100 },
                            animationSpec = tween( 210 )
                        ) + fadeOut(animationSpec = tween( 210 ))

                    // From screen
                    "library_settings_screen" -> horExitScreenTransition()
                    "customization_screen" -> horExitScreenTransition()
                    "other_screen" -> horExitScreenTransition()
                    "about_screen" -> horExitScreenTransition()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    //From screen
                    "library_settings_screen" -> horEnterScreenTransition()
                    "customization_screen" -> horEnterScreenTransition()
                    "other_screen" -> horEnterScreenTransition()
                    "about_screen" -> horEnterScreenTransition()
                    else -> null
                }
            }
        ) { SettingsScreen(navController = navController) }


        // Settings SubScreens
        composable(
            route = SettingsScreens.LibrarySettingsScreen.route,
            enterTransition = {
                when(initialState.destination.route) {
                    //From screen
                    "settings_screen" ->
                        slideInHorizontally(
                            initialOffsetX = { 100 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    //To screen
                    "settings_screen" ->
                        slideOutHorizontally(
                            targetOffsetX = { 100 },
                            animationSpec = tween( 210 )
                        ) + fadeOut(animationSpec = tween( 210 ))
                    else -> null
                }
            },
            //TODO Add popEnter when actually listing items
        ) { LibrarySettingsScreen(navController = navController) }


        composable(
            route = SettingsScreens.CustomizationScreen.route,
            enterTransition = {
                when(initialState.destination.route) {
                    //From screen
                    "settings_screen" ->
                        slideInHorizontally(
                            initialOffsetX = { 100 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    // To screen
                    "settings_screen" ->
                        slideOutHorizontally(
                            targetOffsetX = { 100 },
                            animationSpec = tween( 210 )
                        ) + fadeOut(animationSpec = tween( 210 ))

                    // From screen
                    "player_layout_screen" -> horExitScreenTransition()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    // From screen
                    "player_layout_screen" -> horEnterScreenTransition()
                    else -> null
                }
            }
        ) { CustomizationScreen(navController = navController) }

        composable(
            route = CustomizationSettingsScreens.PlayerLayoutScreen.route,
            enterTransition = {
                when(initialState.destination.route) {
                    // From screen
                    "customization_screen" ->
                        slideInHorizontally(
                            initialOffsetX = { 100 },
                            animationSpec = tween( 200 )
                        ) + fadeIn(animationSpec = tween( 200 ))
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    // To screen
                    "customization_screen" ->
                        slideOutHorizontally(
                            targetOffsetX = { 100 },
                            animationSpec = tween( 210 )
                        ) + fadeOut(animationSpec = tween( 210 ))
                    else -> null
                }
            }
        ) { PlayerLayoutScreen(navController = navController) }


        composable(
            route = SettingsScreens.OtherScreen.route,
            enterTransition = {
                when(initialState.destination.route) {
                    //From screen
                    "settings_screen" ->
                        slideInHorizontally(
                            initialOffsetX = { 100 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    //To screen
                    "settings_screen" ->
                        slideOutHorizontally(
                            targetOffsetX = { 100 },
                            animationSpec = tween( 210 )
                        ) + fadeOut(animationSpec = tween( 210 ))
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    // From screen
                    "app_language_screen" -> horEnterScreenTransition()
                    else -> null
                }
            }
        ) { OtherScreen(navController = navController) }

        composable(
            route = OtherSettingsScreens.AppLanguageScreen.route,
            enterTransition = {
                when(initialState.destination.route) {
                    // From screen
                    "other_screen" ->
                        slideInHorizontally(
                            initialOffsetX = { 100 },
                            animationSpec = tween( 200 )
                        ) + fadeIn(animationSpec = tween( 200 ))
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    // To screen
                    "other_screen" ->
                        slideOutHorizontally(
                            targetOffsetX = { 100 },
                            animationSpec = tween( 210 )
                        ) + fadeOut(animationSpec = tween( 210 ))
                    else -> null
                }
            }
        ) { AppLanguageScreen(navController = navController) }
        

        composable(
            route = SettingsScreens.AboutScreen.route,
            enterTransition = {
                when(initialState.destination.route) {
                    // From screen
                    "settings_screen" ->
                        slideInHorizontally(
                            initialOffsetX = { 100 },
                            animationSpec = tween( 200 )
                        ) + fadeIn(animationSpec = tween( 200 ))
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    // To screen
                    "settings_screen" ->
                        slideOutHorizontally(
                            targetOffsetX = { 100 },
                            animationSpec = tween( 210 )
                        ) + fadeOut(animationSpec = tween( 210 ))
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
                    "songs" -> verEnterFragmentTransition()
                    "albums" -> verEnterFragmentTransition()
                    "artists" -> verEnterFragmentTransition()
                    "playlists" -> verEnterFragmentTransition()
                    "spotify" -> verEnterFragmentTransition()

                    "album_view_screen/{albumId}" -> verEnterFragmentTransition()
                    "artist_view_screen" -> verEnterFragmentTransition()
                    "playlist_view_screen" -> verEnterFragmentTransition()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    "songs" -> fadeOut(animationSpec = tween( 100 ))
                    "albums" -> fadeOut(animationSpec = tween( 100 ))
                    "artists" -> fadeOut(animationSpec = tween( 100 ))
                    "playlists" -> fadeOut(animationSpec = tween( 100 ))
                    "spotify" -> fadeOut(animationSpec = tween( 100 ))
                    "artist_view_screen" -> verExitFragmentTransition()
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
                    "songs" -> verEnterFragmentTransition()
                    "albums" -> verEnterFragmentTransition()
                    "artists" -> verEnterFragmentTransition()
                    "playlists" -> verEnterFragmentTransition()
                    "spotify" -> verEnterFragmentTransition()


                    //From screen
                    "album_view_screen/{albumId}" -> verEnterFragmentTransition()
                    "artist_view_screen" -> verEnterFragmentTransition()
                    "playlist_view_screen" -> verEnterFragmentTransition()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    "songs" -> fadeOut(animationSpec = tween( 100 ))
                    "albums" -> fadeOut(animationSpec = tween( 100 ))
                    "artists" -> fadeOut(animationSpec = tween( 100 ))
                    "playlists" -> fadeOut(animationSpec = tween( 100 ))
                    "spotify" -> fadeOut(animationSpec = tween( 100 ))
                    "album_view_screen/{albumId}" -> verExitFragmentTransition()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    // From screen
                    "album_view_screen/{albumId}" -> verEnterFragmentTransition()
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
                    "albums" -> verEnterFragmentTransition()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    // To screen
                    "songs" ->verExitFragmentTransition()
                    "albums" ->verExitFragmentTransition()
                    "artists" ->verExitFragmentTransition()
                    "playlists" ->verExitFragmentTransition()
                    "spotify" ->verExitFragmentTransition()
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
                    "songs" -> verEnterFragmentTransition()
                    "albums" -> verEnterFragmentTransition()
                    "artists" -> verEnterFragmentTransition()
                    "playlists" -> verEnterFragmentTransition()
                    "spotify" -> verEnterFragmentTransition()


                    //From screen
                    "album_view_screen/{albumId}" -> verEnterFragmentTransition()
                    "artist_view_screen" -> verEnterFragmentTransition()
                    "playlist_view_screen" -> verEnterFragmentTransition()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    "songs" -> fadeOut(animationSpec = tween( 100 ))
                    "albums" -> fadeOut(animationSpec = tween( 100 ))
                    "artists" -> fadeOut(animationSpec = tween( 100 ))
                    "playlists" -> fadeOut(animationSpec = tween( 100 ))
                    "spotify" -> fadeOut(animationSpec = tween( 100 ))
                    "artist_view_screen" -> verExitFragmentTransition()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    // From screen
                    "artist_view_screen" -> verEnterFragmentTransition()
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
                    "songs" -> verEnterFragmentTransition()
                    "artists" -> verEnterFragmentTransition()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    // To screen
                    "songs" ->verExitFragmentTransition()
                    "albums" ->verExitFragmentTransition()
                    "artists" ->verExitFragmentTransition()
                    "playlists" ->verExitFragmentTransition()
                    "spotify" ->verExitFragmentTransition()
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
                    "songs" -> verEnterFragmentTransition()
                    "albums" -> verEnterFragmentTransition()
                    "artists" -> verEnterFragmentTransition()
                    "playlists" -> verEnterFragmentTransition()
                    "spotify" -> verEnterFragmentTransition()

                    //From screen
                    "album_view_screen/{albumId}" -> verEnterFragmentTransition()
                    "artist_view_screen" -> verEnterFragmentTransition()
                    "playlist_view_screen" -> verEnterFragmentTransition()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    // To screen
                    "songs" -> fadeOut(animationSpec = tween( 100 ))
                    "albums" -> fadeOut(animationSpec = tween( 100 ))
                    "artists" -> fadeOut(animationSpec = tween( 100 ))
                    "playlists" -> fadeOut(animationSpec = tween( 100 ))
                    "spotify" -> fadeOut(animationSpec = tween( 100 ))
                    "playlist_view_screen" -> verExitFragmentTransition()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    // From screen
                    "playlist_view_screen" -> verEnterFragmentTransition()
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
                    "playlists" -> verEnterFragmentTransition()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    // To screen
                    "songs" -> verExitFragmentTransition()
                    "albums" -> verExitFragmentTransition()
                    "artists" -> verExitFragmentTransition()
                    "playlists" -> verExitFragmentTransition()
                    "spotify" -> verExitFragmentTransition()
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
                    "songs" -> verEnterFragmentTransition()
                    "albums" -> verEnterFragmentTransition()
                    "artists" -> verEnterFragmentTransition()
                    "playlists" -> verEnterFragmentTransition()
                    "spotify" -> verEnterFragmentTransition()

                    "album_view_screen/{albumId}" -> verEnterFragmentTransition()
                    "artist_view_screen" -> verEnterFragmentTransition()
                    "playlist_view_screen" -> verEnterFragmentTransition()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    "songs" -> fadeOut(animationSpec = tween( 100 ))
                    "albums" -> fadeOut(animationSpec = tween( 100 ))
                    "artists" -> fadeOut(animationSpec = tween( 100 ))
                    "playlists" -> fadeOut(animationSpec = tween( 100 ))
                    "spotify" -> fadeOut(animationSpec = tween( 100 ))
                    else -> null
                }
            }
        ) { SpotifyFavoritesScreen(navController = navControllerApp) }
    }
}