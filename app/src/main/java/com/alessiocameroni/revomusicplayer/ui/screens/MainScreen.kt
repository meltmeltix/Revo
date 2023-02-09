package com.alessiocameroni.revomusicplayer.ui.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.layoutId
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.ui.navigation.NavigationBottomNavBar
import com.alessiocameroni.revomusicplayer.ui.navigation.Screens
import com.alessiocameroni.revomusicplayer.ui.theme.RevoMusicPlayerTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen(navController: NavController) {
    val navControllerBottomBar = rememberAnimatedNavController()
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()

    RevoMusicPlayerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                bottomBar = {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        BottomMiniPlayer(
                            navController = navController,
                            modifier = Modifier,
                            songNameString = "SongName",
                            artistNameString = "ArtistName"
                        )

                        BottomNavigationBar(
                            modifier = Modifier,
                            items = listOf(
                                MainScreenNavigationItemData(
                                    name = stringResource(id = R.string.str_songs),
                                    route = "songs",
                                    iconOutlined = painterResource(id = R.drawable.ic_baseline_music_note_24),
                                    iconFilled = painterResource(id = R.drawable.ic_baseline_music_note_24)
                                ),
                                MainScreenNavigationItemData(
                                    name = stringResource(id = R.string.str_albums),
                                    route = "albums",
                                    iconOutlined = painterResource(id = R.drawable.ic_outlined_album_24),
                                    iconFilled = painterResource(id = R.drawable.ic_filled_album_24)
                                ),
                                MainScreenNavigationItemData(
                                    name = stringResource(id = R.string.str_artists),
                                    route = "artists",
                                    iconOutlined = painterResource(id = R.drawable.ic_outlined_groups_24),
                                    iconFilled = painterResource(id = R.drawable.ic_filled_groups_24)
                                ),
                                MainScreenNavigationItemData(
                                    name = stringResource(id = R.string.str_playlists),
                                    route = "playlists",
                                    iconOutlined = painterResource(id = R.drawable.ic_baseline_playlist_play_24),
                                    iconFilled = painterResource(id = R.drawable.ic_baseline_playlist_play_24)
                                ),
                                MainScreenNavigationItemData(
                                    name = stringResource(id = R.string.str_spotify),
                                    route = "spotify",
                                    iconOutlined = painterResource(id = R.drawable.ic_outlined_spotify_24),
                                    painterResource(id = R.drawable.ic_filled_spotify_24)
                                ),
                            ),
                            navController = navControllerBottomBar,
                            onItemClick = { navControllerBottomBar.navigate(it.route) }
                        )
                    }
                },
                content = { padding ->
                    Column(modifier = Modifier
                        .padding(
                            bottom = padding.calculateBottomPadding() - systemBarsPadding.calculateBottomPadding()
                        )
                    ){
                        NavigationBottomNavBar(
                            navControllerApp = navController,
                            navControllerBottomBar = navControllerBottomBar,
                        )
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
private fun Preview() {
    val navController = rememberAnimatedNavController()

    BottomMiniPlayer(
        navController = navController,
        modifier = Modifier
            .layoutId("MiniPlayer")
            .fillMaxWidth()
            .height(64.dp)
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)),
        songNameString = "SongName",
        artistNameString = "ArtistName"
    )
}

// UI Elements
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
                    contentDescription = stringResource(id = R.string.desc_openMusic)
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
                    contentDescription = stringResource(id = R.string.desc_openMusic),
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
    items: List<MainScreenNavigationItemData>,
    navController: NavController,
    onItemClick: (MainScreenNavigationItemData) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()

    NavigationBar(
        modifier = modifier,
    ) {
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
                label = { Text(text = item.name) }
            )
        }
    }
}
