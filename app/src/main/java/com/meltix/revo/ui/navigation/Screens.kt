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
    object Artists : LibraryScreens("artists")
    object Playlists : LibraryScreens("playlists")
    object DetailsGraph : LibraryScreens("details_graph")
}

open class DetailsScreens(val route: String) {
    object AlbumDetails : DetailsScreens("album_view")
    object ArtistDetails : DetailsScreens("artist_view")
    object PlaylistDetails : DetailsScreens("playlist_view")
}

open class SettingsScreens(val route: String) {
    object MainSettings : SettingsScreens("main_settings")
    object SettingsList : SettingsScreens("settings_list")

    object Library : SettingsScreens("library_settings")

    object Customization : SettingsScreens("customization")
    object PlayerLayout : SettingsScreens("player_layout")
    object AlbumViewLayout : SettingsScreens("album_view_layout")

    object Other : SettingsScreens("other")
    object AppLanguage : SettingsScreens("app_language")

    object About : SettingsScreens("about")
}