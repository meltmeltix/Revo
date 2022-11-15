package com.alessiocameroni.revomusicplayer.data.navigation

open class Screens(val route: String) {
    object MainScreen : Screens("main_screen")
    object PlayerScreen : Screens("player_screen")
    object SearchScreen : Screens("search_screen")
    object SettingsScreen : Screens("settings_screen")
}

open class AlbumsScreens(val route: String) {
    object AlbumViewScreen : AlbumsScreens("album_view_screen")
}

open class ArtistsScreens(val route: String) {
    object ArtistViewScreen : ArtistsScreens("artist_view_screen")
}

open class PlaylistsScreens(val route: String) {
    object PlaylistViewScreen : PlaylistsScreens("playlist_view_screen")
}

open class SettingsScreens(val route: String) {
    object LibrarySettingsScreen : SettingsScreens("library_settings_screen")
    object CustomizationScreen : SettingsScreens("customization_screen")
    object AboutScreen : SettingsScreens("about_screen")
}