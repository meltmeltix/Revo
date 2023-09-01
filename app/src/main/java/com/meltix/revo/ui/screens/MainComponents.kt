package com.meltix.revo.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.meltix.revo.R
import com.meltix.revo.data.classes.MainNavigationItem
import com.meltix.revo.ui.navigation.LibraryScreens
import com.meltix.revo.ui.navigation.LibraryScreens.Albums
import com.meltix.revo.ui.navigation.LibraryScreens.Artists
import com.meltix.revo.ui.navigation.LibraryScreens.Playlists
import com.meltix.revo.ui.navigation.LibraryScreens.Songs

@Composable
fun MainLayout(
    windowWidthSize: WindowWidthSizeClass,
    currentDestinationRoute: String?,
    onNavigationItemSelected: (String) -> Unit,
    viewModel: MainViewModel,
    spotifyItemState: Boolean,
    content: @Composable () -> Unit,
) {
    val destinationList = listOf(
        MainNavigationItem(
            name = stringResource(id = R.string.str_songs),
            route = Songs.route,
            unselectedIcon = painterResource(id = R.drawable.ic_baseline_music_note_24),
            selectedIcon = painterResource(id = R.drawable.ic_baseline_music_note_24)
        ),
        MainNavigationItem(
            name = stringResource(id = R.string.str_albums),
            route = Albums.route,
            unselectedIcon = painterResource(id = R.drawable.ic_outlined_album_24),
            selectedIcon = painterResource(id = R.drawable.ic_filled_album_24)
        ),
        MainNavigationItem(
            name = stringResource(id = R.string.str_artists),
            route = Artists.route,
            unselectedIcon = painterResource(id = R.drawable.ic_outlined_groups_24),
            selectedIcon = painterResource(id = R.drawable.ic_filled_groups_24)
        ),
        MainNavigationItem(
            name = stringResource(id = R.string.str_playlists),
            route = Playlists.route,
            unselectedIcon = painterResource(id = R.drawable.ic_baseline_playlist_play_24),
            selectedIcon = painterResource(id = R.drawable.ic_baseline_playlist_play_24)
        ),
    )

    when(windowWidthSize) {
        WindowWidthSizeClass.Compact -> CompactLayout(
            viewModel = viewModel,
            destinationList = destinationList,
            spotifyItemState = spotifyItemState,
            currentDestinationRoute = currentDestinationRoute,
            onNavigationItemSelected = { onNavigationItemSelected(it) }
        ) { _ -> content() }
        else -> ExpandedLayout(
            viewModel = viewModel,
            destinationList = destinationList,
            spotifyItemState = spotifyItemState,
            currentDestinationRoute = currentDestinationRoute,
            onNavigationItemSelected = { onNavigationItemSelected(it) }
        ) { content() }
    }
}

@Composable
private fun CompactLayout(
    viewModel: MainViewModel,
    destinationList: List<MainNavigationItem>,
    spotifyItemState: Boolean,
    currentDestinationRoute: String?,
    onNavigationItemSelected: (String) -> Unit,
    content: @Composable (innerPadding: PaddingValues) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    destinationList.forEach { item ->
                        val selected =
                            item.route == currentDestinationRoute ||
                            item.route == viewModel.latestDestination
                        if(currentDestinationRoute == Songs.route) {
                            viewModel.latestDestination = Songs.route
                        }

                        NavigationBarItem(
                            selected = selected,
                            onClick = { onNavigationItemSelected(item.route) },
                            icon = { Icon(
                                        painter =
                                            if (selected) item.selectedIcon
                                            else item.unselectedIcon,
                                        contentDescription = item.name
                                    ) },
                            label = { Text(item.name) }
                        )
                    }
                    
                    if(spotifyItemState) {
                        val item = LibraryScreens.Spotify
                        val selected =
                            item.route == currentDestinationRoute ||
                            item.route == viewModel.latestDestination
                        if(currentDestinationRoute == Songs.route) {
                            viewModel.latestDestination = Songs.route
                        }
                        
                        NavigationBarItem(
                            selected = selected,
                            onClick = { onNavigationItemSelected(item.route) },
                            icon = { Icon(
                                painter =
                                    if (selected) painterResource(R.drawable.ic_filled_spotify_24)
                                    else painterResource(R.drawable.ic_outlined_spotify_24),
                                contentDescription = stringResource(R.string.str_spotify)
                            ) },
                            label = { Text(stringResource(R.string.str_spotify)) }
                        )
                    }
                }
            }
        ) { innerPadding -> content(innerPadding) }
    }
}

@Composable
private fun ExpandedLayout(
    viewModel: MainViewModel,
    destinationList: List<MainNavigationItem>,
    spotifyItemState: Boolean,
    currentDestinationRoute: String?,
    onNavigationItemSelected: (String) -> Unit,
    content: @Composable () -> Unit,
) {
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    val systemCutoutPadding = WindowInsets.displayCutout.asPaddingValues()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.inverseOnSurface
    ) {
        Row(
            modifier = Modifier.padding(
                start = systemCutoutPadding.calculateStartPadding(LayoutDirection.Ltr),
                top = systemBarsPadding.calculateTopPadding(),
                end = systemCutoutPadding.calculateEndPadding(LayoutDirection.Ltr) + 24.dp
            )
        ) {
            NavigationRail(
                containerColor = MaterialTheme.colorScheme.inverseOnSurface
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.8f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    destinationList.forEach { item ->
                        val selected =
                            item.route == currentDestinationRoute ||
                            item.route == viewModel.latestDestination
                        if(currentDestinationRoute == Songs.route) {
                            viewModel.latestDestination = Songs.route
                        }

                        NavigationRailItem(
                            selected = selected,
                            onClick = { onNavigationItemSelected(item.route) },
                            icon = { Icon(
                                painter =
                                    if (selected) item.selectedIcon
                                    else item.unselectedIcon,
                                contentDescription = item.name
                            ) },
                            label = { Text(item.name) }
                        )
                    }
                    
                    if(spotifyItemState) {
                        val item = LibraryScreens.Spotify
                        val selected =
                            item.route == currentDestinationRoute ||
                            item.route == viewModel.latestDestination
                        if(currentDestinationRoute == Songs.route) {
                            viewModel.latestDestination = Songs.route
                        }
                        
                        NavigationRailItem(
                            selected = selected,
                            onClick = { onNavigationItemSelected(item.route) },
                            icon = { Icon(
                                painter =
                                    if (selected) painterResource(R.drawable.ic_filled_spotify_24)
                                    else painterResource(R.drawable.ic_outlined_spotify_24),
                                contentDescription = stringResource(R.string.str_spotify)
                            ) },
                            label = { Text(stringResource(R.string.str_spotify)) }
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip( RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp) )
            ) { content() }
        }
    }
}