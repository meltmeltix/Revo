package com.meltix.revo.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.meltix.revo.R
import com.meltix.revo.data.classes.MainNavigationItem
import com.meltix.revo.ui.components.navigationPadding
import com.meltix.revo.ui.navigation.LibraryScreens.Albums
import com.meltix.revo.ui.navigation.LibraryScreens.Artists
import com.meltix.revo.ui.navigation.LibraryScreens.Playlists
import com.meltix.revo.ui.navigation.LibraryScreens.Songs
import com.meltix.revo.ui.navigation.LibraryScreens.Spotify

@Composable
fun MainScaffold(
    content: @Composable ((PaddingValues) -> Unit)
) {
    Scaffold(
        content = content
    )
}

@Composable
fun BoxScope.MainNavigation(
    windowClass: WindowSizeClass,
    viewModel: MainViewModel,
    spotifyItemState: Boolean,
    navController: NavHostController
) {
    val destinationList = listOf(
        MainNavigationItem(
            name = stringResource(id = R.string.str_songs),
            route = Songs.route,
            iconOutlined = painterResource(id = R.drawable.ic_baseline_music_note_24),
            iconFilled = painterResource(id = R.drawable.ic_baseline_music_note_24)
        ),
        MainNavigationItem(
            name = stringResource(id = R.string.str_albums),
            route = Albums.route,
            iconOutlined = painterResource(id = R.drawable.ic_outlined_album_24),
            iconFilled = painterResource(id = R.drawable.ic_filled_album_24)
        ),
        MainNavigationItem(
            name = stringResource(id = R.string.str_artists),
            route = Artists.route,
            iconOutlined = painterResource(id = R.drawable.ic_outlined_groups_24),
            iconFilled = painterResource(id = R.drawable.ic_filled_groups_24)
        ),
        MainNavigationItem(
            name = stringResource(id = R.string.str_playlists),
            route = Playlists.route,
            iconOutlined = painterResource(id = R.drawable.ic_baseline_playlist_play_24),
            iconFilled = painterResource(id = R.drawable.ic_baseline_playlist_play_24)
        ),
    )

    when(windowClass.widthSizeClass) {
        WindowWidthSizeClass.Medium, WindowWidthSizeClass.Expanded ->
            MainNavigationRail(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .navigationPadding(windowClass),
                viewModel = viewModel,
                items = destinationList,
                spotifyItemState = spotifyItemState,
                navController = navController,
                onItemClick = {
                    viewModel.latestDestination = it.route
                    navController.navigate(it.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        else ->
            MainNavigationBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationPadding(windowClass),
                viewModel = viewModel,
                items = destinationList,
                spotifyItemState = spotifyItemState,
                navController = navController,
                onItemClick = {
                    viewModel.latestDestination = it.route
                    navController.navigate(it.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
    }
}

@Composable
fun MainNavigationRail(
    modifier: Modifier,
    items: List<MainNavigationItem>,
    viewModel: MainViewModel,
    spotifyItemState: Boolean,
    navController: NavHostController,
    onItemClick: (MainNavigationItem) -> Unit,
) {
    val backStackEntry = navController.currentBackStackEntryAsState()

    NavigationRail(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom
        ) {
            items.forEach { item ->
                val currentEntry =
                    if (backStackEntry.value?.destination?.route == null) ""
                    else backStackEntry.value!!.destination.route
                val selected = item.route == currentEntry || item.route == viewModel.latestDestination
                when(currentEntry) { Songs.route -> viewModel.latestDestination = Songs.route }

                NavigationRailItem(
                    selected = selected,
                    onClick = { onItemClick(item) },
                    icon = { Icon(
                        if (selected) item.iconFilled else item.iconOutlined,
                        contentDescription = item.name
                    ) },
                    label = { Text(text = item.name) },
                )
            }

            if(spotifyItemState) {
                val currentEntry =
                    if (backStackEntry.value?.destination?.route == null) ""
                    else backStackEntry.value!!.destination.route
                val selected = Spotify.route == currentEntry || Spotify.route == viewModel.latestDestination
                when(currentEntry) { Songs.route -> viewModel.latestDestination = Songs.route }

                NavigationRailItem(
                    selected = selected,
                    onClick = {
                        viewModel.latestDestination = Spotify.route
                        navController.navigate(Spotify.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(
                        painter = if(selected) {
                            painterResource(id = R.drawable.ic_filled_spotify_24)
                        } else {
                            painterResource(id = R.drawable.ic_outlined_spotify_24)
                        },
                        contentDescription = stringResource(id = R.string.str_spotify)
                    ) },
                    label = { Text(text = stringResource(id = R.string.str_spotify)) },
                )
            }
        }
    }
}

@Composable
fun MainNavigationBar(
    modifier: Modifier,
    viewModel: MainViewModel,
    spotifyItemState: Boolean,
    navController: NavHostController,
    onItemClick: (MainNavigationItem) -> Unit,
    items: List<MainNavigationItem>,
) {
    val backStackEntry = navController.currentBackStackEntryAsState()

    NavigationBar(
        modifier = modifier
    ) {
        items.forEach { item ->
            val currentEntry =
                if (backStackEntry.value?.destination?.route == null) ""
                else backStackEntry.value!!.destination.route
            val selected = item.route == currentEntry || item.route == viewModel.latestDestination
            when(currentEntry) { Songs.route -> viewModel.latestDestination = Songs.route }

            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(item) },
                icon = { Icon(
                    if (selected) item.iconFilled else item.iconOutlined,
                    contentDescription = item.name
                ) },
                label = { Text(text = item.name) },
            )
        }

        if(spotifyItemState) {
            val currentEntry =
                if (backStackEntry.value?.destination?.route == null) ""
                else backStackEntry.value!!.destination.route
            val selected = Spotify.route == currentEntry || Spotify.route == viewModel.latestDestination
            when(currentEntry) { Songs.route -> viewModel.latestDestination = Songs.route }

            NavigationBarItem(
                selected = selected,
                onClick = {
                    viewModel.latestDestination = Spotify.route
                    navController.navigate(Spotify.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(
                    painter = if(selected) {
                        painterResource(id = R.drawable.ic_filled_spotify_24)
                    } else {
                        painterResource(id = R.drawable.ic_outlined_spotify_24)
                    },
                    contentDescription = stringResource(id = R.string.str_spotify)
                ) },
                label = { Text(text = stringResource(id = R.string.str_spotify)) },
            )
        }
    }
}