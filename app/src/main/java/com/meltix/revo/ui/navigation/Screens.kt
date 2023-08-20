package com.meltix.revo.ui.navigation

open class RootScreens(val route: String) {
    object Welcome : RootScreens("welcome")
    object Main : RootScreens("main")
    object Player : RootScreens("player")
    object Search : RootScreens("search")
    object SettingsGraph: RootScreens("settings_graph")
}

open class LibraryScreens(val route: String) {
    object Songs : LibraryScreens("songs")

    object Albums : LibraryScreens("albums")
    object AlbumView : LibraryScreens("album_view")

    object Artists : LibraryScreens("artists")
    object ArtistView : LibraryScreens("artist_view")

    object Playlists : LibraryScreens("playlists")
    object PlaylistView : LibraryScreens("playlist_view")

    object Spotify : LibraryScreens("spotify")
}

open class SettingsScreens(val route: String) {
    object MainSettings : RootScreens("main_settings")

    object Library : SettingsScreens("library_settings")

    object Customization : SettingsScreens("customization")
    object PlayerLayout : SettingsScreens("player_layout")
    object AlbumViewLayout : SettingsScreens("album_view_layout")

    object Other : SettingsScreens("other")
    object AppLanguage : SettingsScreens("app_language")

    object About : SettingsScreens("about")
}