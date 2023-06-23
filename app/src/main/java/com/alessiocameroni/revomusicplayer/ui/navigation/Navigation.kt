package com.alessiocameroni.revomusicplayer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alessiocameroni.revomusicplayer.ui.screens.MainScreen
import com.alessiocameroni.revomusicplayer.ui.screens.library.albumScreen.AlbumsScreen
import com.alessiocameroni.revomusicplayer.ui.screens.library.albumScreen.albumViewScreen.AlbumViewScreen
import com.alessiocameroni.revomusicplayer.ui.screens.library.artistScreen.ArtistsScreen
import com.alessiocameroni.revomusicplayer.ui.screens.library.artistScreen.artistViewScreen.ArtistViewScreen
import com.alessiocameroni.revomusicplayer.ui.screens.library.playlistScreen.PlaylistsScreen
import com.alessiocameroni.revomusicplayer.ui.screens.library.playlistScreen.playlistViewScreen.PlaylistViewScreen
import com.alessiocameroni.revomusicplayer.ui.screens.library.songScreen.SongsScreen
import com.alessiocameroni.revomusicplayer.ui.screens.library.spotifyScreen.SpotifyFavoritesScreen
import com.alessiocameroni.revomusicplayer.ui.screens.player.PlayerScreen
import com.alessiocameroni.revomusicplayer.ui.screens.search.SearchScreen
import com.alessiocameroni.revomusicplayer.ui.screens.settings.SettingsScreen
import com.alessiocameroni.revomusicplayer.ui.screens.settings.about.AboutScreen
import com.alessiocameroni.revomusicplayer.ui.screens.settings.customization.CustomizationScreen
import com.alessiocameroni.revomusicplayer.ui.screens.settings.customization.albumViewLayout.AlbumViewLayoutScreen
import com.alessiocameroni.revomusicplayer.ui.screens.settings.customization.playerLayout.PlayerLayoutScreen
import com.alessiocameroni.revomusicplayer.ui.screens.settings.library.LibrarySettingsScreen
import com.alessiocameroni.revomusicplayer.ui.screens.settings.other.OtherScreen
import com.alessiocameroni.revomusicplayer.ui.screens.settings.other.appLanguage.AppLanguageScreen
import com.alessiocameroni.revomusicplayer.ui.screens.welcome.WelcomeScreen

@Composable
fun Navigation(startDestination: String) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        // Welcome screen
        composable(
            route = Screens.WelcomeScreen.route,
            exitTransition = {
                when(targetState.destination.route) {
                    "main_screen" -> horSlideExitToScreen()
                    else -> null
                }
            }
        ) { WelcomeScreen(navController) }

        // Main screen
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

        // Player screen
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

        // Search screen
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

        // Settings screen
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


        // Settings sub-screens
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
                    "album_view_layout_screen" -> horSlideExitToScreen()
                    "player_layout_screen" -> horSlideExitToScreen()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    "album_view_layout_screen" -> horSlidePopEnterFromScreen()
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
            route = SettingsScreens.AlbumViewLayoutScreen.route,
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
        ) { AlbumViewLayoutScreen(navController = navController) }

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

@Composable
fun NavigationBottomNavBar(
    navControllerBottomBar: NavHostController,
    navControllerApp: NavController
) {
    NavHost(navController = navControllerBottomBar, startDestination = NavigationScreens.SongScreen.route) {
        composable(
            route = NavigationScreens.SongScreen.route,
            enterTransition = {
                when(initialState.destination.route) {
                    NavigationScreens.SongScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.AlbumScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.ArtistScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.PlaylistScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.SpotifyScreen.route -> verSlideEnterFromFragment()

                    "album_view_screen/{albumId}" -> verSlideEnterFromFragment()
                    "artist_view_screen/{artistId}" -> verSlideEnterFromFragment()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    NavigationScreens.SongScreen.route -> fadeExitToFragment()
                    NavigationScreens.AlbumScreen.route -> fadeExitToFragment()
                    NavigationScreens.ArtistScreen.route -> fadeExitToFragment()
                    NavigationScreens.PlaylistScreen.route -> fadeExitToFragment()
                    NavigationScreens.SpotifyScreen.route -> fadeExitToFragment()

                    "album_view_screen/{albumId}" -> verSlideExitFromSubFragment()
                    "artist_view_screen/{artistId}" -> verSlideExitFromSubFragment()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    NavigationScreens.SongScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.AlbumScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.ArtistScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.PlaylistScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.SpotifyScreen.route -> verSlideEnterFromFragment()

                    "album_view_screen/{albumId}" -> verSlideEnterFromFragment()
                    "artist_view_screen/{artistId}" -> verSlideEnterFromFragment()
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
            route = NavigationScreens.AlbumScreen.route,
            enterTransition = {
                when(initialState.destination.route) {
                    NavigationScreens.SongScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.AlbumScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.ArtistScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.PlaylistScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.SpotifyScreen.route -> verSlideEnterFromFragment()

                    "album_view_screen/{albumId}" -> verSlideEnterFromFragment()
                    "artist_view_screen/{artistId}" -> verSlideEnterFromFragment()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    NavigationScreens.SongScreen.route -> fadeExitToFragment()
                    NavigationScreens.AlbumScreen.route -> fadeExitToFragment()
                    NavigationScreens.ArtistScreen.route -> fadeExitToFragment()
                    NavigationScreens.PlaylistScreen.route -> fadeExitToFragment()
                    NavigationScreens.SpotifyScreen.route -> fadeExitToFragment()

                    "album_view_screen/{albumId}" -> verSlideExitFromSubFragment()
                    "artist_view_screen/{artistId}" -> verSlideExitFromSubFragment()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    NavigationScreens.SongScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.AlbumScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.ArtistScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.PlaylistScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.SpotifyScreen.route -> verSlideEnterFromFragment()

                    "album_view_screen/{albumId}" -> verSlideEnterFromFragment()
                    "artist_view_screen/{artistId}" -> verSlideEnterFromFragment()
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
                    NavigationScreens.SongScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.AlbumScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.ArtistScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.PlaylistScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.SpotifyScreen.route -> verSlideEnterFromFragment()

                    "artist_view_screen/{artistId}" -> horSlidePopEnterFromScreen()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    NavigationScreens.SongScreen.route -> verSlideExitFromSubFragment()
                    NavigationScreens.AlbumScreen.route -> verSlideExitFromSubFragment()
                    NavigationScreens.ArtistScreen.route -> verSlideExitFromSubFragment()
                    NavigationScreens.PlaylistScreen.route -> verSlideExitFromSubFragment()
                    NavigationScreens.SpotifyScreen.route -> verSlideExitFromSubFragment()

                    "artist_view_screen/{artistId}" -> horSlideExitToScreen()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    "artist_view_screen/{artistId}" -> horSlidePopEnterFromScreen()
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
            route = NavigationScreens.ArtistScreen.route,
            enterTransition = {
                when(initialState.destination.route) {
                    NavigationScreens.SongScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.AlbumScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.ArtistScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.PlaylistScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.SpotifyScreen.route -> verSlideEnterFromFragment()

                    "playlist_view_screen" -> verSlideEnterFromFragment()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    NavigationScreens.SongScreen.route -> fadeExitToFragment()
                    NavigationScreens.AlbumScreen.route -> fadeExitToFragment()
                    NavigationScreens.ArtistScreen.route -> fadeExitToFragment()
                    NavigationScreens.PlaylistScreen.route -> fadeExitToFragment()
                    NavigationScreens.SpotifyScreen.route -> fadeExitToFragment()

                    "artist_view_screen/{artistId}" -> verSlideExitFromSubFragment()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    NavigationScreens.SongScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.AlbumScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.ArtistScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.PlaylistScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.SpotifyScreen.route -> verSlideEnterFromFragment()

                    "artist_view_screen/{artistId}" -> verSlideEnterFromFragment()
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
            route = "artist_view_screen/{artistId}",
            enterTransition = {
                when(initialState.destination.route) {
                    NavigationScreens.SongScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.ArtistScreen.route -> verSlideEnterFromFragment()

                    "album_view_screen/{albumId}" -> horSlideEnterFromScreen()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    NavigationScreens.SongScreen.route ->verSlideExitFromSubFragment()
                    NavigationScreens.AlbumScreen.route ->verSlideExitFromSubFragment()
                    NavigationScreens.ArtistScreen.route ->verSlideExitFromSubFragment()
                    NavigationScreens.PlaylistScreen.route ->verSlideExitFromSubFragment()
                    NavigationScreens.SpotifyScreen.route ->verSlideExitFromSubFragment()

                    "album_view_screen/{albumId}" -> horSlidePopExitToScreen()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    "album_view_screen/{albumId}" -> horSlideEnterFromScreen()
                    else -> null
                }
            }
        ) {backStackEntry ->
            val artistId = backStackEntry.arguments?.getString("artistId")?.toLong()

            if (artistId != null) {
                ArtistViewScreen(
                    artistId = artistId,
                    navController = navControllerApp,
                    navControllerBottomBar = navControllerBottomBar
                )
            }
        }

        composable(
            route = NavigationScreens.PlaylistScreen.route,
            enterTransition = {
                when(initialState.destination.route) {
                    NavigationScreens.SongScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.AlbumScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.ArtistScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.PlaylistScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.SpotifyScreen.route -> verSlideEnterFromFragment()

                    "album_view_screen/{albumId}" -> verSlideEnterFromFragment()
                    "artist_view_screen/{artistId}" -> verSlideEnterFromFragment()
                    "playlist_view_screen" -> verSlideEnterFromFragment()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    NavigationScreens.SongScreen.route -> fadeExitToFragment()
                    NavigationScreens.AlbumScreen.route -> fadeExitToFragment()
                    NavigationScreens.ArtistScreen.route -> fadeExitToFragment()
                    NavigationScreens.PlaylistScreen.route -> fadeExitToFragment()
                    NavigationScreens.SpotifyScreen.route -> fadeExitToFragment()
                    "playlist_view_screen" -> verSlideExitFromSubFragment()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    NavigationScreens.SongScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.AlbumScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.ArtistScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.PlaylistScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.SpotifyScreen.route -> verSlideEnterFromFragment()

                    "playlist_view_screen" -> verSlideEnterFromFragment()
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
                    NavigationScreens.PlaylistScreen.route -> verSlideEnterFromFragment()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    NavigationScreens.SongScreen.route -> verSlideExitFromSubFragment()
                    NavigationScreens.AlbumScreen.route -> verSlideExitFromSubFragment()
                    NavigationScreens.ArtistScreen.route -> verSlideExitFromSubFragment()
                    NavigationScreens.PlaylistScreen.route -> verSlideExitFromSubFragment()
                    NavigationScreens.SpotifyScreen.route -> verSlideExitFromSubFragment()
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
            route = NavigationScreens.SpotifyScreen.route,
            enterTransition = {
                when(initialState.destination.route) {
                    NavigationScreens.SongScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.AlbumScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.ArtistScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.PlaylistScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.SpotifyScreen.route -> verSlideEnterFromFragment()

                    "album_view_screen/{albumId}" -> verSlideEnterFromFragment()
                    "artist_view_screen/{artistId}" -> verSlideEnterFromFragment()
                    "playlist_view_screen" -> verSlideEnterFromFragment()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    NavigationScreens.SongScreen.route -> fadeExitToFragment()
                    NavigationScreens.AlbumScreen.route -> fadeExitToFragment()
                    NavigationScreens.ArtistScreen.route -> fadeExitToFragment()
                    NavigationScreens.PlaylistScreen.route -> fadeExitToFragment()
                    NavigationScreens.SpotifyScreen.route -> fadeExitToFragment()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    NavigationScreens.SongScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.AlbumScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.ArtistScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.PlaylistScreen.route -> verSlideEnterFromFragment()
                    NavigationScreens.SpotifyScreen.route -> verSlideEnterFromFragment()
                    else -> null
                }
            }
        ) { SpotifyFavoritesScreen(navController = navControllerApp) }
    }
}