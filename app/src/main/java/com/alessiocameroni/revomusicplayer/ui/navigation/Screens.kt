package com.alessiocameroni.revomusicplayer.ui.navigation

open class Screens(val route: String) {
    object WelcomeScreen : Screens("welcome_screen")
    object MainScreen : Screens("main_screen")
    object PlayerScreen : Screens("player_screen")
    object SearchScreen : Screens("search_screen")
    object SettingsScreen : Screens("settings_screen")
}

open class NavigationScreens(val route: String) {
    object SongScreen : NavigationScreens("songs")

    object AlbumScreen : NavigationScreens("albums")
    object AlbumViewScreen : NavigationScreens("album_view_screen")

    object ArtistScreen : NavigationScreens("artists")
    object ArtistViewScreen : NavigationScreens("artist_view_screen")

    object PlaylistScreen : NavigationScreens("playlists")
    object PlaylistViewScreen : NavigationScreens("playlist_view_screen")

    object SpotifyScreen : NavigationScreens("spotify")
}

open class SettingsScreens(val route: String) {
    object LibrarySettingsScreen : SettingsScreens("library_settings_screen")

    object CustomizationScreen : SettingsScreens("customization_screen")
    object PlayerLayoutScreen : SettingsScreens("player_layout_screen")
    object AlbumViewLayoutScreen : SettingsScreens("album_view_layout_screen")

    object OtherScreen : SettingsScreens("other_screen")
    object AppLanguageScreen : SettingsScreens("app_language_screen")

    object AboutScreen : SettingsScreens("about_screen")
}