package com.alessiocameroni.revomusicplayer.navigation

open class Screens(val route: String) {
    object MainScreen : Screens("main_screen")
    object PermissionsScreen : Screens("permissions_screen")
    object PlayerScreen : Screens("player_screen")
    object SearchScreen : Screens("search_screen")
    object SettingsScreen : Screens("settings_screen")
}

open class SettingsScreens(val route: String) {
    object LooksScreen : SettingsScreens("looks_screen")
    object AboutScreen : SettingsScreens("about_screen")
}