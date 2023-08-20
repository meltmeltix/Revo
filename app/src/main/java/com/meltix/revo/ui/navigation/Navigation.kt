package com.meltix.revo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.meltix.revo.ui.screens.MainScreen
import com.meltix.revo.ui.screens.library.albumScreen.AlbumsScreen
import com.meltix.revo.ui.screens.library.albumScreen.albumViewScreen.AlbumViewScreen
import com.meltix.revo.ui.screens.library.artistScreen.ArtistsScreen
import com.meltix.revo.ui.screens.library.artistScreen.artistViewScreen.ArtistViewScreen
import com.meltix.revo.ui.screens.library.playlistScreen.PlaylistsScreen
import com.meltix.revo.ui.screens.library.playlistScreen.playlistViewScreen.PlaylistViewScreen
import com.meltix.revo.ui.screens.library.songScreen.SongsScreen
import com.meltix.revo.ui.screens.library.spotifyScreen.SpotifyFavoritesScreen
import com.meltix.revo.ui.screens.player.PlayerScreen
import com.meltix.revo.ui.screens.search.SearchScreen
import com.meltix.revo.ui.screens.settings.SettingsScreen
import com.meltix.revo.ui.screens.settings.about.AboutScreen
import com.meltix.revo.ui.screens.settings.customization.CustomizationScreen
import com.meltix.revo.ui.screens.settings.customization.albumViewLayout.AlbumViewLayoutScreen
import com.meltix.revo.ui.screens.settings.customization.playerLayout.PlayerLayoutScreen
import com.meltix.revo.ui.screens.settings.library.LibrarySettingsScreen
import com.meltix.revo.ui.screens.settings.other.OtherScreen
import com.meltix.revo.ui.screens.settings.other.appLanguage.AppLanguageScreen
import com.meltix.revo.ui.screens.welcome.WelcomeScreen

@Composable
fun RootNavigation(startDestination: String) {
    val navController = rememberNavController()
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

        // Search screen
        composable(
            route = RootScreens.Search.route,
            enterTransition = {
                when (initialState.destination.route) {
                    RootScreens.Main.route -> horSlideEnterFromScreen()
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    RootScreens.Main.route -> horSlidePopExitToScreen()
                    else -> null
                }
            },
            //TODO Add popEnter when actually listing items
        ) { SearchScreen(navController = navController) }

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
}

@Composable
fun LibraryNavigation(
    navControllerMain: NavHostController,
    navControllerApp: NavController
) {
    NavHost(navController = navControllerMain, startDestination = LibraryScreens.Songs.route) {
        composable(
            route = LibraryScreens.Songs.route,
            enterTransition = {
                when(initialState.destination.route) {
                    LibraryScreens.Songs.route -> verSlideEnterFromFragment()
                    LibraryScreens.Albums.route -> verSlideEnterFromFragment()
                    LibraryScreens.Artists.route -> verSlideEnterFromFragment()
                    LibraryScreens.Playlists.route -> verSlideEnterFromFragment()
                    LibraryScreens.Spotify.route -> verSlideEnterFromFragment()

                    LibraryScreens.AlbumView.route + "/{albumId}" -> verSlideEnterFromFragment()
                    LibraryScreens.ArtistView.route + "/{artistId}" -> verSlideEnterFromFragment()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    LibraryScreens.Songs.route -> fadeExitToFragment()
                    LibraryScreens.Albums.route -> fadeExitToFragment()
                    LibraryScreens.Artists.route -> fadeExitToFragment()
                    LibraryScreens.Playlists.route -> fadeExitToFragment()
                    LibraryScreens.Spotify.route -> fadeExitToFragment()

                    LibraryScreens.AlbumView.route + "/{albumId}" -> verSlideExitFromSubFragment()
                    LibraryScreens.ArtistView.route + "/{artistId}" -> verSlideExitFromSubFragment()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    LibraryScreens.Songs.route -> verSlideEnterFromFragment()
                    LibraryScreens.Albums.route -> verSlideEnterFromFragment()
                    LibraryScreens.Artists.route -> verSlideEnterFromFragment()
                    LibraryScreens.Playlists.route -> verSlideEnterFromFragment()
                    LibraryScreens.Spotify.route -> verSlideEnterFromFragment()

                    LibraryScreens.AlbumView.route + "/{albumId}" -> verSlideEnterFromFragment()
                    LibraryScreens.ArtistView.route + "/{artistId}" -> verSlideEnterFromFragment()
                    else -> null
                }
            }
        ) {
            SongsScreen(
                navControllerApp = navControllerApp,
                navControllerMain = navControllerMain,
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
                    LibraryScreens.Spotify.route -> verSlideEnterFromFragment()

                    LibraryScreens.AlbumView.route + "/{albumId}" -> verSlideEnterFromFragment()
                    LibraryScreens.ArtistView.route + "/{artistId}" -> verSlideEnterFromFragment()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    LibraryScreens.Songs.route -> fadeExitToFragment()
                    LibraryScreens.Albums.route -> fadeExitToFragment()
                    LibraryScreens.Artists.route -> fadeExitToFragment()
                    LibraryScreens.Playlists.route -> fadeExitToFragment()
                    LibraryScreens.Spotify.route -> fadeExitToFragment()

                    LibraryScreens.AlbumView.route + "/{albumId}" -> verSlideExitFromSubFragment()
                    LibraryScreens.ArtistView.route + "/{artistId}" -> verSlideExitFromSubFragment()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    LibraryScreens.Songs.route -> verSlideEnterFromFragment()
                    LibraryScreens.Albums.route -> verSlideEnterFromFragment()
                    LibraryScreens.Artists.route -> verSlideEnterFromFragment()
                    LibraryScreens.Playlists.route -> verSlideEnterFromFragment()
                    LibraryScreens.Spotify.route -> verSlideEnterFromFragment()

                    LibraryScreens.AlbumView.route + "/{albumId}" -> verSlideEnterFromFragment()
                    LibraryScreens.ArtistView.route + "/{artistId}" -> verSlideEnterFromFragment()
                    else -> null
                }
            }
        ) {
            AlbumsScreen(
                navControllerApp = navControllerApp,
                navControllerMain = navControllerMain
            )
        }

        // Album SubScreens
        composable(
            route = LibraryScreens.AlbumView.route + "/{albumId}",
            enterTransition = {
                when(initialState.destination.route) {
                    LibraryScreens.Songs.route -> verSlideEnterFromFragment()
                    LibraryScreens.Albums.route -> verSlideEnterFromFragment()
                    LibraryScreens.Artists.route -> verSlideEnterFromFragment()
                    LibraryScreens.Playlists.route -> verSlideEnterFromFragment()
                    LibraryScreens.Spotify.route -> verSlideEnterFromFragment()

                    LibraryScreens.ArtistView.route + "/{artistId}" -> horSlidePopEnterFromScreen()
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

                    LibraryScreens.ArtistView.route + "/{artistId}" -> horSlideExitToScreen()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    LibraryScreens.ArtistView.route + "/{artistId}" -> horSlidePopEnterFromScreen()
                    else -> null
                }
            }
        ) {backStackEntry ->
            val albumId = backStackEntry.arguments?.getString("albumId")?.toLong()

            if (albumId != null) {
                AlbumViewScreen(
                    albumId = albumId,
                    navController = navControllerApp,
                    navControllerBottomBar = navControllerMain
                )
            }
        }


        composable(
            route = LibraryScreens.Artists.route,
            enterTransition = {
                when(initialState.destination.route) {
                    LibraryScreens.Songs.route -> verSlideEnterFromFragment()
                    LibraryScreens.Albums.route -> verSlideEnterFromFragment()
                    LibraryScreens.Artists.route -> verSlideEnterFromFragment()
                    LibraryScreens.Playlists.route -> verSlideEnterFromFragment()
                    LibraryScreens.Spotify.route -> verSlideEnterFromFragment()

                    LibraryScreens.PlaylistView.route -> verSlideEnterFromFragment()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    LibraryScreens.Songs.route -> fadeExitToFragment()
                    LibraryScreens.Albums.route -> fadeExitToFragment()
                    LibraryScreens.Artists.route -> fadeExitToFragment()
                    LibraryScreens.Playlists.route -> fadeExitToFragment()
                    LibraryScreens.Spotify.route -> fadeExitToFragment()

                    LibraryScreens.ArtistView.route + "/{artistId}" -> verSlideExitFromSubFragment()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    LibraryScreens.Songs.route -> verSlideEnterFromFragment()
                    LibraryScreens.Albums.route -> verSlideEnterFromFragment()
                    LibraryScreens.Artists.route -> verSlideEnterFromFragment()
                    LibraryScreens.Playlists.route -> verSlideEnterFromFragment()
                    LibraryScreens.Spotify.route -> verSlideEnterFromFragment()

                    LibraryScreens.ArtistView.route + "/{artistId}" -> verSlideEnterFromFragment()
                    else -> null
                }
            }
        ) {
            ArtistsScreen(
                navControllerApp = navControllerApp,
                navControllerMain = navControllerMain
            )
        }

        // Artist SubScreens
        composable(
            route = LibraryScreens.ArtistView.route + "/{artistId}",
            enterTransition = {
                when(initialState.destination.route) {
                    LibraryScreens.Songs.route -> verSlideEnterFromFragment()
                    LibraryScreens.Artists.route -> verSlideEnterFromFragment()

                    LibraryScreens.AlbumView.route + "/{albumId}" -> horSlideEnterFromScreen()
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

                    LibraryScreens.AlbumView.route + "/{albumId}" -> horSlidePopExitToScreen()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    LibraryScreens.AlbumView.route + "/{albumId}" -> horSlideEnterFromScreen()
                    else -> null
                }
            }
        ) {backStackEntry ->
            val artistId = backStackEntry.arguments?.getString("artistId")?.toLong()

            if (artistId != null) {
                ArtistViewScreen(
                    artistId = artistId,
                    navController = navControllerApp,
                    navControllerBottomBar = navControllerMain
                )
            }
        }

        composable(
            route = LibraryScreens.Playlists.route,
            enterTransition = {
                when(initialState.destination.route) {
                    LibraryScreens.Songs.route -> verSlideEnterFromFragment()
                    LibraryScreens.Albums.route -> verSlideEnterFromFragment()
                    LibraryScreens.Artists.route -> verSlideEnterFromFragment()
                    LibraryScreens.Playlists.route -> verSlideEnterFromFragment()
                    LibraryScreens.Spotify.route -> verSlideEnterFromFragment()

                    LibraryScreens.AlbumView.route + "/{albumId}" -> verSlideEnterFromFragment()
                    LibraryScreens.ArtistView.route + "/{albumId}" -> verSlideEnterFromFragment()
                    LibraryScreens.PlaylistView.route -> verSlideEnterFromFragment()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    LibraryScreens.Songs.route -> fadeExitToFragment()
                    LibraryScreens.Albums.route -> fadeExitToFragment()
                    LibraryScreens.Artists.route -> fadeExitToFragment()
                    LibraryScreens.Playlists.route -> fadeExitToFragment()
                    LibraryScreens.Spotify.route -> fadeExitToFragment()

                    LibraryScreens.PlaylistView.route -> verSlideExitFromSubFragment()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    LibraryScreens.Songs.route -> verSlideEnterFromFragment()
                    LibraryScreens.Albums.route -> verSlideEnterFromFragment()
                    LibraryScreens.Artists.route -> verSlideEnterFromFragment()
                    LibraryScreens.Playlists.route -> verSlideEnterFromFragment()
                    LibraryScreens.Spotify.route -> verSlideEnterFromFragment()

                    LibraryScreens.PlaylistView.route -> verSlideEnterFromFragment()
                    else -> null
                }
            }
        ) {
            PlaylistsScreen(
                navControllerApp = navControllerApp,
                navControllerMain = navControllerMain
            )
        }

        // Playlist SubScreens
        composable(
            route = LibraryScreens.PlaylistView.route,
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
                navController = navControllerApp,
                navControllerBottomBar = navControllerMain
            )
        }


        composable(
            route = LibraryScreens.Spotify.route,
            enterTransition = {
                when(initialState.destination.route) {
                    LibraryScreens.Songs.route -> verSlideEnterFromFragment()
                    LibraryScreens.Albums.route -> verSlideEnterFromFragment()
                    LibraryScreens.Artists.route -> verSlideEnterFromFragment()
                    LibraryScreens.Playlists.route -> verSlideEnterFromFragment()
                    LibraryScreens.Spotify.route -> verSlideEnterFromFragment()

                    LibraryScreens.AlbumView.route + "/{albumId}" -> verSlideEnterFromFragment()
                    LibraryScreens.ArtistView.route + "/{albumId}" -> verSlideEnterFromFragment()
                    LibraryScreens.PlaylistView.route -> verSlideEnterFromFragment()
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    LibraryScreens.Songs.route -> fadeExitToFragment()
                    LibraryScreens.Albums.route -> fadeExitToFragment()
                    LibraryScreens.Artists.route -> fadeExitToFragment()
                    LibraryScreens.Playlists.route -> fadeExitToFragment()
                    LibraryScreens.Spotify.route -> fadeExitToFragment()
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    LibraryScreens.Songs.route -> verSlideEnterFromFragment()
                    LibraryScreens.Albums.route -> verSlideEnterFromFragment()
                    LibraryScreens.Artists.route -> verSlideEnterFromFragment()
                    LibraryScreens.Playlists.route -> verSlideEnterFromFragment()
                    LibraryScreens.Spotify.route -> verSlideEnterFromFragment()
                    else -> null
                }
            }
        ) {
            SpotifyFavoritesScreen(
                navControllerApp = navControllerApp
            )
        }
    }
}