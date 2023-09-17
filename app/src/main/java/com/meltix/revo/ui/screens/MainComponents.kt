package com.meltix.revo.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.meltix.revo.R
import com.meltix.revo.data.classes.FabType
import com.meltix.revo.data.classes.MainNavigationItem
import com.meltix.revo.data.classes.WindowType
import com.meltix.revo.ui.navigation.DetailsScreens
import com.meltix.revo.ui.navigation.LibraryScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout(
    windowType: Any,
    currentDestinationRoute: String,
    onNavigationItemSelected: (String) -> Unit,
    viewModel: MainViewModel,
    playAllOnClick: () -> Unit,
    newPlaylistOnClick: () -> Unit,
    addTrackOnClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    val destinationList = listOf(
        MainNavigationItem(
            name = stringResource(id = R.string.str_songs),
            route = LibraryScreens.Songs.route,
            unselectedIcon = painterResource(id = R.drawable.ic_baseline_music_note_24),
            selectedIcon = painterResource(id = R.drawable.ic_baseline_music_note_24)
        ),
        MainNavigationItem(
            name = stringResource(id = R.string.str_albums),
            route = LibraryScreens.Albums.route,
            unselectedIcon = painterResource(id = R.drawable.ic_outlined_album_24),
            selectedIcon = painterResource(id = R.drawable.ic_filled_album_24)
        ),
        MainNavigationItem(
            name = stringResource(id = R.string.str_artists),
            route = LibraryScreens.Artists.route,
            unselectedIcon = painterResource(id = R.drawable.ic_outlined_groups_24),
            selectedIcon = painterResource(id = R.drawable.ic_filled_groups_24)
        ),
        MainNavigationItem(
            name = stringResource(id = R.string.str_playlists),
            route = LibraryScreens.Playlists.route,
            unselectedIcon = painterResource(id = R.drawable.ic_baseline_playlist_play_24),
            selectedIcon = painterResource(id = R.drawable.ic_baseline_playlist_play_24)
        ),
    )
    
    when(windowType) {
        WindowType.COMPACT_PORTRAIT -> {
            CompactLayout(
                viewModel = viewModel,
                destinationList = destinationList,
                currentDestinationRoute = currentDestinationRoute,
                onNavigationItemSelected = { onNavigationItemSelected(it) },
                fab = {
                    FabConfiguration(
                        currentDestinationRoute = currentDestinationRoute,
                        playAllOnClick = { playAllOnClick() },
                        newPlaylistOnClick = { newPlaylistOnClick() },
                        addTrackOnClick = { addTrackOnClick() },
                        elevation = it
                    )
                }
            ) { _ -> content() }
        }
        else -> {
            ExpandedLayout(
                viewModel = viewModel,
                destinationList = destinationList,
                currentDestinationRoute = currentDestinationRoute,
                onNavigationItemSelected = { onNavigationItemSelected(it) },
                fab = {
                    FabConfiguration(
                        currentDestinationRoute = currentDestinationRoute,
                        playAllOnClick = { playAllOnClick() },
                        newPlaylistOnClick = { newPlaylistOnClick() },
                        addTrackOnClick = { addTrackOnClick() },
                        elevation = it
                    )
                },
            ) { _ -> content() }
        }
    }
}

@Composable
private fun CompactLayout(
    viewModel: MainViewModel,
    destinationList: List<MainNavigationItem>,
    currentDestinationRoute: String?,
    onNavigationItemSelected: (String) -> Unit,
    fab: @Composable (FloatingActionButtonElevation) -> Unit,
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
                        if(currentDestinationRoute == LibraryScreens.Songs.route) {
                            viewModel.latestDestination = LibraryScreens.Songs.route
                        }

                        NavigationBarItem(
                            selected = selected,
                            onClick = { onNavigationItemSelected(item.route) },
                            icon = {
                                Icon(
                                    painter =
                                        if (selected) item.selectedIcon
                                        else item.unselectedIcon,
                                    contentDescription = item.name
                                )
                            },
                            label = { Text(item.name) }
                        )
                    }
                }
            },
            floatingActionButton = { fab(FloatingActionButtonDefaults.elevation()) }
        ) { innerPadding -> content(innerPadding) }
    }
}

@Composable
private fun ExpandedLayout(
    viewModel: MainViewModel,
    destinationList: List<MainNavigationItem>,
    currentDestinationRoute: String?,
    onNavigationItemSelected: (String) -> Unit,
    fab: @Composable (FloatingActionButtonElevation) -> Unit,
    content: @Composable (innerPadding: PaddingValues) -> Unit,
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
                containerColor = MaterialTheme.colorScheme.inverseOnSurface,
                header = { fab(FloatingActionButtonDefaults.bottomAppBarFabElevation()) },
                windowInsets = WindowInsets(0.dp)
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
                        if(currentDestinationRoute == LibraryScreens.Songs.route) {
                            viewModel.latestDestination = LibraryScreens.Songs.route
                        }

                        NavigationRailItem(
                            selected = selected,
                            onClick = { onNavigationItemSelected(item.route) },
                            icon = {
                                Icon(
                                    painter =
                                        if (selected) item.selectedIcon
                                        else item.unselectedIcon,
                                    contentDescription = item.name
                                )
                            },
                            label = { Text(item.name) }
                        )
                    }
                }
            }

            Scaffold(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                containerColor = MaterialTheme.colorScheme.inverseOnSurface
            ) { padding -> content(padding) }
        }
    }
}

@Composable
private fun FabConfiguration(
    currentDestinationRoute: String,
    playAllOnClick: () -> Unit,
    newPlaylistOnClick: () -> Unit,
    addTrackOnClick: () -> Unit,
    elevation: FloatingActionButtonElevation,
) {
    val screenVisibility = when {
        currentDestinationRoute.contains(DetailsScreens.AlbumDetails.route) -> false
        currentDestinationRoute.contains(DetailsScreens.ArtistDetails.route) -> false
        else -> true
    }
    val fabType = when(currentDestinationRoute) {
        LibraryScreens.Playlists.route -> FabType.NEW_PLAYLIST
        DetailsScreens.PlaylistDetails.route -> FabType.ADD_TRACKS
        else -> FabType.SHUFFLE_ALL
    }
    
    when(fabType) {
        FabType.SHUFFLE_ALL -> {
            AnimatedVisibility(
                visible = screenVisibility
            ) {
                FloatingActionButton(
                    onClick = playAllOnClick,
                    elevation = elevation
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_shuffle_24),
                        contentDescription = stringResource(id = R.string.str_shuffle)
                    )
                }
            }
        }
        FabType.NEW_PLAYLIST -> {
            AnimatedVisibility(
                visible = screenVisibility
            ) {
                FloatingActionButton(
                    onClick = newPlaylistOnClick,
                    elevation = elevation
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_playlist_add_24),
                        contentDescription = stringResource(id = R.string.str_newPlaylist)
                    )
                }
            }
        }
        FabType.ADD_TRACKS -> {
            AnimatedVisibility(
                visible = screenVisibility
            ) {
                FloatingActionButton(
                    onClick = addTrackOnClick,
                    elevation = elevation
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_add_24),
                        contentDescription = stringResource(id = R.string.str_addToPlaylist)
                    )
                }
            }
        }
    }
}