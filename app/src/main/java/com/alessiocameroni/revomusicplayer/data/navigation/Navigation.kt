package com.alessiocameroni.revomusicplayer.data.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.alessiocameroni.revomusicplayer.library.albums.albumview.AlbumViewScreen
import com.alessiocameroni.revomusicplayer.library.albums.libraryalbums.AlbumsScreen
import com.alessiocameroni.revomusicplayer.library.artists.artistview.mainscreen.ArtistViewScreen
import com.alessiocameroni.revomusicplayer.library.artists.libraryartists.ArtistsScreen
import com.alessiocameroni.revomusicplayer.library.playlists.libraryplaylists.PlaylistsScreen
import com.alessiocameroni.revomusicplayer.library.playlists.playlistview.PlaylistViewScreen
import com.alessiocameroni.revomusicplayer.library.songs.SongsScreen
import com.alessiocameroni.revomusicplayer.library.spotify.SpotifyFavoritesScreen
import com.alessiocameroni.revomusicplayer.mainscreen.MainScreen
import com.alessiocameroni.revomusicplayer.player.PlayerScreen
import com.alessiocameroni.revomusicplayer.search.SearchScreen
import com.alessiocameroni.revomusicplayer.settings.about.AboutScreen
import com.alessiocameroni.revomusicplayer.settings.customization.CustomizationScreen
import com.alessiocameroni.revomusicplayer.settings.customization.playerlayout.PlayerLayoutScreen
import com.alessiocameroni.revomusicplayer.settings.library.LibrarySettingsScreen
import com.alessiocameroni.revomusicplayer.settings.mainscreen.SettingsScreen
import com.alessiocameroni.revomusicplayer.settings.other.OtherScreen
import com.alessiocameroni.revomusicplayer.settings.other.applanguage.AppLanguageScreen
import com.alessiocameroni.revomusicplayer.welcome.WelcomeScreen
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
                    "main_screen" ->
                        slideOutHorizontally(
                            targetOffsetX = { -100 },
                            animationSpec = tween( 210 )
                        ) + fadeOut(animationSpec = tween( 210 ))
                    else -> null
                }
            }
        ) { WelcomeScreen(navController) }

        composable(
            route = Screens.MainScreen.route,
            exitTransition = {
                when(targetState.destination.route) {
                    // To screen
                    "search_screen" ->
                        slideOutHorizontally(
                            targetOffsetX = { -100 },
                            animationSpec = tween( 210 )
                        ) + fadeOut(animationSpec = tween( 210 ))
                    "settings_screen" ->
                        slideOutHorizontally(
                            targetOffsetX = { -100 },
                            animationSpec = tween( 210 )
                        ) + fadeOut(animationSpec = tween( 210 ))
                    "player_screen" ->
                        slideOutVertically(
                            targetOffsetY = { -200 },
                            animationSpec = tween( 240 )
                        ) + fadeOut(animationSpec = tween( 260 ))
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    // From screen
                    "welcome_screen" ->
                        slideInHorizontally(
                            initialOffsetX = { -100 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "search_screen" ->
                        slideInHorizontally(
                            initialOffsetX = { -100 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "settings_screen" ->
                        slideInHorizontally(
                            initialOffsetX = { -100 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "player_screen" ->
                        slideInVertically(
                            initialOffsetY = { -200 },
                            animationSpec = tween( 240 )
                        ) + fadeIn(animationSpec = tween( 260 ))
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
                    "settings_screen" ->
                        slideOutHorizontally(
                            targetOffsetX = { -100 },
                            animationSpec = tween( 210 )
                        ) + fadeOut(animationSpec = tween( 210 ))
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    // To screen
                    "settings_screen" ->
                        slideInHorizontally(
                            initialOffsetX = { -100 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
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
                    "library_settings_screen" ->
                        slideOutHorizontally(
                            targetOffsetX = { -100 },
                            animationSpec = tween( 210 )
                        ) + fadeOut(animationSpec = tween( 210 ))
                    "customization_screen" ->
                        slideOutHorizontally(
                            targetOffsetX = { -100 },
                            animationSpec = tween( 210 )
                        ) + fadeOut(animationSpec = tween( 210 ))
                    "other_screen" ->
                        slideOutHorizontally(
                            targetOffsetX = { -100 },
                            animationSpec = tween( 210 )
                        ) + fadeOut(animationSpec = tween( 210 ))
                    "about_screen" ->
                        slideOutHorizontally(
                            targetOffsetX = { -100 },
                            animationSpec = tween( 210 )
                        ) + fadeOut(animationSpec = tween( 210 ))
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    //From screen
                    "library_settings_screen" ->
                        slideInHorizontally(
                            initialOffsetX = { -100 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "customization_screen" ->
                        slideInHorizontally(
                            initialOffsetX = { -100 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "other_screen" ->
                        slideInHorizontally(
                            initialOffsetX = { -100 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "about_screen" ->
                        slideInHorizontally(
                            initialOffsetX = { -100 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
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
                    "player_layout_screen" ->
                        slideOutHorizontally(
                            targetOffsetX = { -100 },
                            animationSpec = tween( 210 )
                        ) + fadeOut(animationSpec = tween( 210 ))
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    // From screen
                    "player_layout_screen" ->
                        slideInHorizontally(
                            initialOffsetX = { -100 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
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
                    "app_language_screen" ->
                        slideInHorizontally(
                            initialOffsetX = { -100 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
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
                    "songs" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "albums" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "artists" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "playlists" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "spotify" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))

                    "album_view_screen" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "artist_view_screen/{artistId}/{artist}" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "playlist_view_screen" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
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
                    "artist_view_screen/{artistId}/{artist}" ->
                        slideOutVertically (
                            targetOffsetY = { -30 },
                            animationSpec = tween( 210 )
                        ) + fadeOut(animationSpec = tween( 210 ))
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
                    "songs" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "albums" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "artists" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "playlists" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "spotify" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))


                    //From screen
                    "album_view_screen" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "artist_view_screen/{artistId}/{artist}" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "playlist_view_screen" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
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
                    "album_view_screen" ->
                        slideOutVertically (
                            targetOffsetY = { -30 },
                            animationSpec = tween( 210 )
                        ) + fadeOut(animationSpec = tween( 210 ))
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    // From screen
                    "album_view_screen" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
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
            route = AlbumsScreens.AlbumViewScreen.route,
            enterTransition = {
                when(initialState.destination.route) {
                    "albums" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    // To screen
                    "songs" ->
                        slideOutVertically(
                            targetOffsetY = { -30 },
                            animationSpec = tween(210)
                        ) + fadeOut(animationSpec = tween(210))
                    "albums" ->
                        slideOutVertically(
                            targetOffsetY = { -30 },
                            animationSpec = tween(210)
                        ) + fadeOut(animationSpec = tween(210))
                    "artists" ->
                        slideOutVertically(
                            targetOffsetY = { -30 },
                            animationSpec = tween(210)
                        ) + fadeOut(animationSpec = tween(210))
                    "playlists" ->
                        slideOutVertically(
                            targetOffsetY = { -30 },
                            animationSpec = tween(210)
                        ) + fadeOut(animationSpec = tween(210))
                    "spotify" ->
                        slideOutVertically(
                            targetOffsetY = { -30 },
                            animationSpec = tween(210)
                        ) + fadeOut(animationSpec = tween(210))
                    else -> null
                }
            }
        ) {
            AlbumViewScreen(
                navController = navControllerApp,
                navControllerBottomBar = navControllerBottomBar
            )
        }


        composable(
            route = "artists",
            enterTransition = {
                when(initialState.destination.route) {
                    "songs" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "albums" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "artists" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "playlists" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "spotify" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))


                    //From screen
                    "album_view_screen" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "artist_view_screen/{artistId}/{artist}" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "playlist_view_screen" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
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
                    "artist_view_screen/{artistId}/{artist}" ->
                        slideOutVertically (
                            targetOffsetY = { -30 },
                            animationSpec = tween( 210 )
                        ) + fadeOut(animationSpec = tween( 210 ))
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    // From screen
                    "artist_view_screen/{artistId}/{artist}" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
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
            route = "artist_view_screen/{artistId}/{artist}",
            enterTransition = {
                when(initialState.destination.route) {
                    "songs" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "artists" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    // To screen
                    "songs" ->
                        slideOutVertically(
                            targetOffsetY = { -30 },
                            animationSpec = tween(210)
                        ) + fadeOut(animationSpec = tween(210))
                    "albums" ->
                        slideOutVertically(
                            targetOffsetY = { -30 },
                            animationSpec = tween(210)
                        ) + fadeOut(animationSpec = tween(210))
                    "artists" ->
                        slideOutVertically(
                            targetOffsetY = { -30 },
                            animationSpec = tween(210)
                        ) + fadeOut(animationSpec = tween(210))
                    "playlists" ->
                        slideOutVertically(
                            targetOffsetY = { -30 },
                            animationSpec = tween(210)
                        ) + fadeOut(animationSpec = tween(210))
                    "spotify" ->
                        slideOutVertically(
                            targetOffsetY = { -30 },
                            animationSpec = tween(210)
                        ) + fadeOut(animationSpec = tween(210))
                    else -> null
                }
            }
        ) { navBackStack ->
            val artistId = navBackStack.arguments?.getString("artistId")?.toLong()
            val artist = navBackStack.arguments?.getString("artist")

            ArtistViewScreen(
                navController = navControllerApp,
                navControllerBottomBar = navControllerBottomBar,
                artistId = artistId,
                artist = artist
            )
        }

        composable(
            route = "playlists",
            enterTransition = {
                when(initialState.destination.route) {
                    // To screen
                    "songs" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "albums" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "artists" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "playlists" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "spotify" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))

                    //From screen
                    "album_view_screen" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "artist_view_screen/{artistId}/{artist}" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "playlist_view_screen" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
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
                    "playlist_view_screen" ->
                        slideOutVertically (
                            targetOffsetY = { -30 },
                            animationSpec = tween( 210 )
                        ) + fadeOut(animationSpec = tween( 210 ))
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    // From screen
                    "playlist_view_screen" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
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
            route = PlaylistsScreens.PlaylistViewScreen.route,
            enterTransition = {
                when(initialState.destination.route) {
                    "playlists" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    // To screen
                    "songs" ->
                        slideOutVertically(
                            targetOffsetY = { -30 },
                            animationSpec = tween(210)
                        ) + fadeOut(animationSpec = tween(210))
                    "albums" ->
                        slideOutVertically(
                            targetOffsetY = { -30 },
                            animationSpec = tween(210)
                        ) + fadeOut(animationSpec = tween(210))
                    "artists" ->
                        slideOutVertically(
                            targetOffsetY = { -30 },
                            animationSpec = tween(210)
                        ) + fadeOut(animationSpec = tween(210))
                    "playlists" ->
                        slideOutVertically(
                            targetOffsetY = { -30 },
                            animationSpec = tween(210)
                        ) + fadeOut(animationSpec = tween(210))
                    "spotify" ->
                        slideOutVertically(
                            targetOffsetY = { -30 },
                            animationSpec = tween(210)
                        ) + fadeOut(animationSpec = tween(210))
                    else -> null
                }
            }
        ) { PlaylistViewScreen(
                navController = navControllerApp,
                navControllerBottomBar = navControllerBottomBar
            )
        }


        composable(
            route = "spotify",
            enterTransition = {
                when(initialState.destination.route) {
                    "songs" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "albums" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "artists" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "playlists" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "spotify" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))

                    "album_view_screen" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "artist_view_screen/{artistId}/{artist}" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "playlist_view_screen" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
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