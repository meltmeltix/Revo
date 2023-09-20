package com.meltix.revo.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.meltix.revo.R
import com.meltix.revo.data.classes.FabType
import com.meltix.revo.data.classes.MainNavigationItem
import com.meltix.revo.data.classes.WindowType
import com.meltix.revo.ui.components.SmallImageContainer
import com.meltix.revo.ui.navigation.DetailsScreens
import com.meltix.revo.ui.navigation.LibraryScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout(
    windowType: WindowType,
    currentDestinationRoute: String,
    latestDestination: String,
    onNavigationItemSelected: (String) -> Unit,
    fabOnShuffleClick: () -> Unit,
    fabOnNewPlaylistClick: () -> Unit,
    fabOnAddTrackClick: () -> Unit,
    bottomSheetState: SheetState,
    openBottomSheet: Boolean,
    onMiniPlayerClick: () -> Unit,
    onPlayPauseClick: () -> Unit,
    onNextSkipClick: () -> Unit,
    onSheetDismissRequest: () -> Unit,
    bottomSheetContent: @Composable () -> Unit,
    content: @Composable () -> Unit
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
        WindowType.COMPACT_WINDOW, WindowType.COMPACT_PORTRAIT -> {
            CompactLayout(
                destinationList = destinationList,
                currentDestinationRoute = currentDestinationRoute,
                latestDestination = latestDestination,
                onNavigationItemSelected = { onNavigationItemSelected(it) },
                miniPlayer = {
                    MiniPlayerLayout(
                        windowType = windowType,
                        onMiniPlayerClick = { onMiniPlayerClick() },
                        onPlayPauseClick = { onPlayPauseClick() },
                        onNextSkipClick = { onNextSkipClick() }
                    )
                },
                fab = { elevation ->
                    FabConfiguration(
                        currentDestinationRoute = currentDestinationRoute,
                        shuffleOnClick = { fabOnShuffleClick() },
                        newPlaylistOnClick = { fabOnNewPlaylistClick() },
                        addTrackOnClick = { fabOnAddTrackClick() },
                        elevation = elevation
                    )
                },
                bottomSheetState = bottomSheetState,
                openBottomSheet = openBottomSheet,
                onSheetDismissRequest = { onSheetDismissRequest() },
                bottomSheetContent = { bottomSheetContent() }
            ) { _ -> content() }
        }
        else -> {
            ExpandedLayout(
                destinationList = destinationList,
                currentDestinationRoute = currentDestinationRoute,
                latestDestination = latestDestination,
                onNavigationItemSelected = { onNavigationItemSelected(it) },
                miniPlayer = {
                    MiniPlayerLayout(
                        windowType = windowType,
                        onMiniPlayerClick = { onMiniPlayerClick() },
                        onPlayPauseClick = { onPlayPauseClick() },
                        onNextSkipClick = { onNextSkipClick() }
                    )
                },
                fab = { elevation ->
                    FabConfiguration(
                        currentDestinationRoute = currentDestinationRoute,
                        shuffleOnClick = { fabOnShuffleClick() },
                        newPlaylistOnClick = { fabOnNewPlaylistClick() },
                        addTrackOnClick = { fabOnAddTrackClick() },
                        elevation = elevation
                    )
                },
                bottomSheetState = bottomSheetState,
                openBottomSheet = openBottomSheet,
                onSheetDismissRequest = { onSheetDismissRequest() },
                bottomSheetContent = { bottomSheetContent() }
            ) { _ -> content() }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CompactLayout(
    destinationList: List<MainNavigationItem>,
    currentDestinationRoute: String,
    latestDestination: String,
    onNavigationItemSelected: (String) -> Unit,
    miniPlayer: @Composable () -> Unit,
    fab: @Composable (FloatingActionButtonElevation) -> Unit,
    bottomSheetState: SheetState,
    openBottomSheet: Boolean,
    onSheetDismissRequest: () -> Unit,
    bottomSheetContent: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Scaffold(
            bottomBar = {
                Column {
                    miniPlayer()
                    NavigationBar {
                        destinationList.forEach { item ->
                            val selected =
                                item.route == currentDestinationRoute ||
                                item.route == latestDestination
            
                            NavigationBarItem(
                                selected = selected,
                                onClick = { onNavigationItemSelected(item.route) },
                                icon = {
                                    Icon(
                                        painter = if (selected) item.selectedIcon else item.unselectedIcon,
                                        contentDescription = item.name
                                    )
                                },
                                label = { Text(item.name) }
                            )
                        }
                    }
                }
            },
            floatingActionButton = { fab(FloatingActionButtonDefaults.elevation()) }
        ) { innerPadding -> content(innerPadding) }
        
        if(openBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { onSheetDismissRequest() },
                sheetState = bottomSheetState,
            ) { bottomSheetContent() }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExpandedLayout(
    destinationList: List<MainNavigationItem>,
    currentDestinationRoute: String,
    latestDestination: String,
    onNavigationItemSelected: (String) -> Unit,
    miniPlayer: @Composable () -> Unit,
    fab: @Composable (FloatingActionButtonElevation) -> Unit,
    bottomSheetState: SheetState,
    openBottomSheet: Boolean,
    onSheetDismissRequest: () -> Unit,
    bottomSheetContent: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
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
                end = systemCutoutPadding.calculateEndPadding(LayoutDirection.Ltr) + 24.dp,
            )
        ) {
            NavigationRail(
                containerColor = MaterialTheme.colorScheme.inverseOnSurface,
                header = { fab(FloatingActionButtonDefaults.bottomAppBarFabElevation()) },
                windowInsets = WindowInsets(0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = systemBarsPadding.calculateBottomPadding()),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    destinationList.forEach { item ->
                        val selected =
                            item.route == currentDestinationRoute ||
                            item.route == latestDestination
                        
                        NavigationRailItem(
                            selected = selected,
                            onClick = { onNavigationItemSelected(item.route) },
                            icon = {
                                Icon(
                                    painter = if (selected) item.selectedIcon else item.unselectedIcon,
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
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                bottomBar = { miniPlayer() }
            ) { innerPadding -> content(innerPadding) }
        }
        
        if (openBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { onSheetDismissRequest() },
                sheetState = bottomSheetState,
            ) { bottomSheetContent() }
        }
    }
}

@Composable
private fun MiniPlayerLayout(
    windowType: WindowType,
    onMiniPlayerClick: () -> Unit,
    onPlayPauseClick: () -> Unit,
    onNextSkipClick: () -> Unit
) {
    when(windowType) {
        WindowType.COMPACT_PORTRAIT -> {
            CompactMiniPlayer(
                onMiniPlayerClick = { onMiniPlayerClick() },
                onPlayPauseClick = { onPlayPauseClick() },
                onNextSkipClick = { onNextSkipClick() }
            )
        }
        else -> {
            ExpandedMiniPlayer(
                onMiniPlayerClick = { onMiniPlayerClick() },
                onPlayPauseClick = { onPlayPauseClick() },
                onNextSkipClick = { onNextSkipClick() }
            )
        }
    }
}

@Composable
private fun CompactMiniPlayer(
    onMiniPlayerClick: () -> Unit,
    onPlayPauseClick: () -> Unit,
    onNextSkipClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    
    Surface(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .clickable(interactionSource, null) { onMiniPlayerClick() },
        color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 2.dp)) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SmallImageContainer(
                    modifier = Modifier.size(52.dp),
                    painterPlaceholder = painterResource(id = R.drawable.ic_baseline_music_note_24)
                ) {  }
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Song Name",
                        color = MaterialTheme.colorScheme.onSurface,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = MaterialTheme.typography.titleMedium
                    )
                    
                    Text(
                        text = "Artist Name",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                
                Row(
                    modifier = Modifier.fillMaxHeight(),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    FilledIconButton(
                        onClick = { onPlayPauseClick() },
                        modifier = Modifier
                            .size(52.dp)
                            .aspectRatio(1f),
                        shape = MaterialTheme.shapes.large
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_baseline_play_arrow_24),
                            contentDescription = stringResource(R.string.str_play),
                        )
                    }
                    
                    FilledTonalIconButton(
                        onClick = { onNextSkipClick() },
                        modifier = Modifier.size(36.dp, 52.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_filled_skip_next_24),
                            contentDescription = stringResource(R.string.str_next)
                        )
                    }
                }
            }
            
            LinearProgressIndicator(
                progress = 0.25f,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.extraLarge),
            )
        }
    }
}

@Composable
private fun ExpandedMiniPlayer(
    onMiniPlayerClick: () -> Unit,
    onPlayPauseClick: () -> Unit,
    onNextSkipClick: () -> Unit
) {
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    val interactionSource = remember { MutableInteractionSource() }
    
    Surface(
        modifier = Modifier
            .height(systemBarsPadding.calculateBottomPadding() + 72.dp)
            .fillMaxWidth()
            .clickable(interactionSource, null) { onMiniPlayerClick() },
        color = MaterialTheme.colorScheme.inverseOnSurface
    ) {
        Row(
            modifier = Modifier
                .height(72.dp)
                .padding(
                    start = 5.dp,
                    top = 10.dp,
                    end = 5.dp,
                    bottom = systemBarsPadding.calculateBottomPadding() + 5.dp
                ),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SmallImageContainer(
                modifier = Modifier.size(55.dp),
                painterPlaceholder = painterResource(id = R.drawable.ic_baseline_music_note_24)
            ) {  }
    
            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(55.dp)
            ) {
                Text(
                    text = "Song Name",
                    color = MaterialTheme.colorScheme.onSurface,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium
                )
        
                Text(
                    text = "Artist Name",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium
                )
                
                Spacer(modifier = Modifier.weight(1f))
    
                LinearProgressIndicator(
                    progress = 0.25f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.extraLarge),
                )
            }
    
            Row(
                modifier = Modifier.height(55.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                FilledIconButton(
                    onClick = { onPlayPauseClick() },
                    modifier = Modifier
                        .size(55.dp)
                        .aspectRatio(1f),
                    shape = MaterialTheme.shapes.large
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_baseline_play_arrow_24),
                        contentDescription = stringResource(R.string.str_play),
                    )
                }
        
                FilledTonalIconButton(
                    onClick = { onNextSkipClick() },
                    modifier = Modifier.size(36.dp, 55.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_filled_skip_next_24),
                        contentDescription = stringResource(R.string.str_next)
                    )
                }
            }
        }
    }
}

@Composable
private fun FabConfiguration(
    currentDestinationRoute: String,
    shuffleOnClick: () -> Unit,
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
                    onClick = shuffleOnClick,
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