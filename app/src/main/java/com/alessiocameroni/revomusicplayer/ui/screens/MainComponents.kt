package com.alessiocameroni.revomusicplayer.ui.screens

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.data.classes.MainNavigationItem
import com.alessiocameroni.revomusicplayer.ui.navigation.NavigationScreens.*
import com.alessiocameroni.revomusicplayer.ui.navigation.Screens

@Composable
fun BottomContent(
    navController: NavController,
    navControllerBottomBar: NavHostController,
    spotifyVisibilityState: Boolean
) {
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    val contentExpanded = remember { mutableStateOf(true) }
    val transition = updateTransition(targetState = contentExpanded, label = "")
    val columnOffset by transition.animateDp(label = "") {
        when(it.value) {
            true -> 0.dp
            false -> 70.dp
        }
    }
    val navBarOffset by transition.animateDp(label = "") {
        when(it.value) {
            true -> 0.dp
            false -> systemBarsPadding.calculateBottomPadding() + 50.dp
        }
    }

    Column(
        modifier = Modifier
            .offset(y = columnOffset)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
    ) {
        BottomMiniPlayer(
            navController = navController,
            modifier = Modifier,
            songNameString = "SongName",
            artistNameString = "ArtistName"
        )

        BottomNavigationBar(
            modifier = Modifier,
            items = listOf(
                MainNavigationItem(
                    name = stringResource(id = R.string.str_songs),
                    route = SongScreen.route,
                    iconOutlined = painterResource(id = R.drawable.ic_baseline_music_note_24),
                    iconFilled = painterResource(id = R.drawable.ic_baseline_music_note_24)
                ),
                MainNavigationItem(
                    name = stringResource(id = R.string.str_albums),
                    route = AlbumScreen.route,
                    iconOutlined = painterResource(id = R.drawable.ic_outlined_album_24),
                    iconFilled = painterResource(id = R.drawable.ic_filled_album_24)
                ),
                MainNavigationItem(
                    name = stringResource(id = R.string.str_artists),
                    route = ArtistScreen.route,
                    iconOutlined = painterResource(id = R.drawable.ic_outlined_groups_24),
                    iconFilled = painterResource(id = R.drawable.ic_filled_groups_24)
                ),
                MainNavigationItem(
                    name = stringResource(id = R.string.str_playlists),
                    route = PlaylistScreen.route,
                    iconOutlined = painterResource(id = R.drawable.ic_baseline_playlist_play_24),
                    iconFilled = painterResource(id = R.drawable.ic_baseline_playlist_play_24)
                ),
            ),
            spotifyVisibilityState = spotifyVisibilityState,
            navController = navControllerBottomBar,
            onItemClick = {
                navControllerBottomBar.navigate(it.route) {
                    popUpTo(navControllerBottomBar.graph.findStartDestination().id) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            contentExpanded = contentExpanded,
            offset = navBarOffset
        )
    }
}

@Composable
fun BottomMiniPlayer(
    modifier: Modifier,
    navController: NavController,
    songNameString: String,
    artistNameString: String
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                navController.navigate(Screens.PlayerScreen.route)
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(40.dp)
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_keyboard_arrow_up_24),
                    contentDescription = stringResource(id = R.string.str_openPlayer)
                )
            }


            Text(
                modifier = Modifier.weight(1f),
                text = "$songNameString · $artistNameString",
                maxLines = 1,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )

            IconButton(
                onClick = {  },
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_play_arrow_24),
                    contentDescription = stringResource(id = R.string.str_play),
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
        ) {
            LinearProgressIndicator(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium),
                progress = 0.3f
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    items: List<MainNavigationItem>,
    spotifyVisibilityState: Boolean,
    navController: NavController,
    onItemClick: (MainNavigationItem) -> Unit,
    contentExpanded: MutableState<Boolean>,
    offset: Dp,
) {
    val backStackEntry = navController.currentBackStackEntryAsState()

    when(backStackEntry.value?.destination?.route) {
        SongScreen.route -> contentExpanded.value = true
        AlbumScreen.route -> contentExpanded.value = true
        ArtistScreen.route -> contentExpanded.value = true
        PlaylistScreen.route -> contentExpanded.value = true
        SpotifyScreen.route -> contentExpanded.value = true
        else -> contentExpanded.value = false
    }

    NavigationBar( modifier = modifier.offset(y = offset) ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route

            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(item) },
                icon = {
                    Icon(
                        if (selected) item.iconFilled else item.iconOutlined,
                        contentDescription = item.name
                    )
                },
                label = { Text(text = item.name) },
            )
        }

        if(spotifyVisibilityState) {
            val selected = SpotifyScreen.route == backStackEntry.value?.destination?.route

            NavigationBarItem(
                selected =  selected,
                onClick = {
                    navController.navigate(SpotifyScreen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter =
                        if(selected) {
                            painterResource(id = R.drawable.ic_filled_spotify_24)
                        } else {
                            painterResource(id = R.drawable.ic_outlined_spotify_24)
                        },
                        contentDescription = stringResource(id = R.string.str_songs)
                    )

                },
                label = { Text(text = stringResource(id = R.string.str_spotify)) }
            )
        }
    }
}