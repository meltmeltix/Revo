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
import com.meltix.revo.ui.components.navigationPaddingOnWindow
import com.meltix.revo.ui.navigation.NavigationScreens

@Composable
fun MainScaffold(
    content: @Composable ((PaddingValues) -> Unit)
) {
    Scaffold(
        content = content
    )
}

@Composable
fun BoxScope.MainNavigation(windowClass: WindowSizeClass, navController: NavHostController) {
    val destinationList = listOf(
        MainNavigationItem(
            name = stringResource(id = R.string.str_songs),
            route = NavigationScreens.SongScreen.route,
            iconOutlined = painterResource(id = R.drawable.ic_baseline_music_note_24),
            iconFilled = painterResource(id = R.drawable.ic_baseline_music_note_24)
        ),
        MainNavigationItem(
            name = stringResource(id = R.string.str_albums),
            route = NavigationScreens.AlbumScreen.route,
            iconOutlined = painterResource(id = R.drawable.ic_outlined_album_24),
            iconFilled = painterResource(id = R.drawable.ic_filled_album_24)
        ),
        MainNavigationItem(
            name = stringResource(id = R.string.str_artists),
            route = NavigationScreens.ArtistScreen.route,
            iconOutlined = painterResource(id = R.drawable.ic_outlined_groups_24),
            iconFilled = painterResource(id = R.drawable.ic_filled_groups_24)
        ),
        MainNavigationItem(
            name = stringResource(id = R.string.str_playlists),
            route = NavigationScreens.PlaylistScreen.route,
            iconOutlined = painterResource(id = R.drawable.ic_baseline_playlist_play_24),
            iconFilled = painterResource(id = R.drawable.ic_baseline_playlist_play_24)
        ),
    )

    when(windowClass.widthSizeClass) {
        WindowWidthSizeClass.Medium, WindowWidthSizeClass.Expanded ->
            MainNavigationRail(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .navigationPaddingOnWindow(windowClass),
                items = destinationList,
                navController = navController,
                onItemClick = {
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
                    .navigationPaddingOnWindow(windowClass),
                items = destinationList,
                navController = navController,
                onItemClick = {
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
    navController: NavHostController,
    onItemClick: (MainNavigationItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()

    NavigationRail(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom
        ) {
            items.forEach { item ->
                val selected = item.route == backStackEntry.value?.destination?.route

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
        }
    }
}

@Composable
fun MainNavigationBar(
    modifier: Modifier,
    items: List<MainNavigationItem>,
    navController: NavHostController,
    onItemClick: (MainNavigationItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()

    NavigationBar(
        modifier = modifier
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route

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
    }
}