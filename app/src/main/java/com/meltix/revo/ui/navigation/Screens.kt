package com.meltix.revo.ui.navigation

open class RootScreens(val route: String) {
    object Main : RootScreens("main")
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