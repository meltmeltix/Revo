package com.alessiocameroni.revomusicplayer.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.main.data.bottomnav.BottomNavigationItemData
import com.alessiocameroni.revomusicplayer.navigation.NavigationBottomNavBar
import com.alessiocameroni.revomusicplayer.navigation.Screens
import com.alessiocameroni.revomusicplayer.ui.theme.RevoMusicPlayerTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen(navController: NavController) {
    val navControllerBottomBar = rememberAnimatedNavController()

    RevoMusicPlayerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                bottomBar = {
                    val constraints = ConstraintSet {
                        val bottomNavigationBar = createRefFor("BottomNavBar")
                        val miniPlayer = createRefFor("MiniPlayer")

                        constrain(bottomNavigationBar) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }

                        constrain(miniPlayer) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(bottomNavigationBar.top)
                        }
                    }

                    ConstraintLayout(constraints, Modifier.fillMaxWidth()) {
                        BottomMiniPlayer(
                            navController = navController,
                            modifier = Modifier
                                .layoutId("MiniPlayer")
                                .fillMaxWidth()
                                .height(64.dp)
                                .background(
                                    MaterialTheme.colorScheme.surfaceColorAtElevation(
                                        3.dp
                                    )
                                ),
                            songNameString = "SongName",
                            artistNameString = "ArtistName"
                        )

                        BottomNavigationBar(
                            modifier = Modifier
                                .layoutId("BottomNavBar"),
                            items = listOf(
                                BottomNavigationItemData(
                                    name = stringResource(id = R.string.str_home),
                                    route = "home",
                                    iconOutlined = painterResource(id = R.drawable.ic_outlined_home_24),
                                    iconFilled = painterResource(id = R.drawable.ic_filled_home_24)
                                ),
                                BottomNavigationItemData(
                                    name = stringResource(id = R.string.str_songs),
                                    route = "tracks",
                                    iconOutlined = painterResource(id = R.drawable.ic_baseline_music_note_24),
                                    iconFilled = painterResource(id = R.drawable.ic_baseline_music_note_24)
                                ),
                                BottomNavigationItemData(
                                    name = stringResource(id = R.string.str_albums),
                                    route = "albums",
                                    iconOutlined = painterResource(id = R.drawable.ic_outlined_album_24),
                                    iconFilled = painterResource(id = R.drawable.ic_filled_album_24)
                                ),
                                BottomNavigationItemData(
                                    name = stringResource(id = R.string.str_playlists),
                                    route = "playlists",
                                    iconOutlined = painterResource(id = R.drawable.ic_baseline_playlist_play_24),
                                    iconFilled = painterResource(id = R.drawable.ic_baseline_playlist_play_24)
                                ),
                                BottomNavigationItemData(
                                    name = stringResource(id = R.string.str_spoitfy),
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
                    Column(modifier = Modifier.padding(padding)){
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


// UI Elements
@Composable
fun BottomMiniPlayer(
    modifier: Modifier,
    navController: NavController,
    songNameString: String,
    artistNameString: String
) {
    Box(
        modifier = modifier
    ) {
        val constraints = ConstraintSet {
            val musicProgressBar = createRefFor("MusicProgressBar")
            val openPanelButton = createRefFor("OpenPanelButton")
            val songInfoText = createRefFor("SongInfoText")
            val playButton = createRefFor("PlayButton")

            constrain(musicProgressBar) {
                bottom.linkTo(parent.bottom)
            }

            constrain(openPanelButton) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }

            constrain(songInfoText) {
                start.linkTo(openPanelButton.end)
                top.linkTo(parent.top)
                end.linkTo(playButton.start)
                bottom.linkTo(parent.bottom)
            }

            constrain(playButton) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
        }

        ConstraintLayout(constraints, modifier = Modifier.fillMaxSize()) {
            IconButton(
                onClick = { navController.navigate(Screens.PlayerScreen.route) },
                modifier = Modifier
                    .layoutId("OpenPanelButton")
                    .padding(horizontal = 4.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_keyboard_arrow_up_24),
                    contentDescription = stringResource(id = R.string.desc_openmusic)
                )
            }

            Text(
                modifier = Modifier
                    .layoutId("SongInfoText")
                    .width(280.dp),
                text = "$songNameString · $artistNameString",
                maxLines = 1,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )

            IconButton(
                onClick = {  },
                modifier = Modifier
                    .layoutId("PlayButton")
                    .padding(horizontal = 4.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_play_arrow_24),
                    contentDescription = stringResource(id = R.string.desc_openmusic),
                )
            }

            LinearProgressIndicator(
                modifier = Modifier
                    .layoutId("MusicProgressBar")
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .semantics(mergeDescendants = true) {},
                progress = 0.5f
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    items: List<BottomNavigationItemData>,
    navController: NavController,
    onItemClick: (BottomNavigationItemData) -> Unit
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
