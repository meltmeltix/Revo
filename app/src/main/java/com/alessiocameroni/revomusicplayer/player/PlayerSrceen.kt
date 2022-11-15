package com.alessiocameroni.revomusicplayer.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import androidx.navigation.NavController
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.data.navigation.Screens
import com.alessiocameroni.revomusicplayer.player.components.CenterSongControls
import com.alessiocameroni.revomusicplayer.ui.theme.RevoMusicPlayerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(navController: NavController) {
    val sliderPosition by remember { mutableStateOf(0.5f) }
    val shuffleChecked by remember { mutableStateOf(false) }
    val repeatChecked by remember { mutableStateOf(false) }

    RevoMusicPlayerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = { TopActionBar(navController) },
                bottomBar = { BottomActionBar(
                    navController,
                    shuffleChecked,
                    repeatChecked
                ) }
            ) { padding ->
                val constraints = ConstraintSet {
                    val boxAlbumCover = createRefFor("AlbumCover")
                    val boxPlayerControls = createRefFor("PlayerControls")

                    constrain(boxAlbumCover) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(boxPlayerControls.top)
                    }

                    constrain(boxPlayerControls) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                }

                ConstraintLayout(
                    constraints,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = 30.dp,
                            top = padding.calculateTopPadding() + 15.dp,
                            end = 30.dp,
                            bottom = padding.calculateBottomPadding() + 15.dp,
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .layoutId("AlbumCover")
                            .size(340.dp)
                            .clip(MaterialTheme.shapes.extraLarge)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(150.dp),
                            painter = painterResource(id = R.drawable.ic_baseline_music_note_24),
                            contentDescription = stringResource(id = R.string.str_songs),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    Box(
                        modifier = Modifier
                            .layoutId("PlayerControls")
                            .fillMaxWidth()
                            .height(246.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CenterSongControls(
                            modifier = Modifier,
                            floatSliderPosition = sliderPosition
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopActionBar(navController: NavController) {
    TopAppBar(title = { Text(text = "") },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.ic_baseline_keyboard_arrow_down_24
                    ),
                    contentDescription = stringResource(
                        id = R.string.desc_closeMusic
                    )
                )
            }
        }
    )
}

@Composable
fun BottomActionBar(
    navController: NavController,
    boolShuffleChecked: Boolean,
    boolRepeatChecked: Boolean
) {
    var shuffleChecked by remember { mutableStateOf(boolShuffleChecked) }
    var repeatChecked by remember { mutableStateOf(boolRepeatChecked) }
    val expanded = remember { mutableStateOf(false) }

    BottomAppBar(
        actions = {
            Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                IconButton(
                    onClick = { expanded.value = true }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                        contentDescription = stringResource(id = R.string.str_moreOptions)
                    )
                }

                MaterialTheme(shapes = MaterialTheme.shapes.copy(extraSmall = MaterialTheme.shapes.large)) {
                    DropdownMenu(
                        modifier = Modifier.width(180.dp),
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false }
                    ) {
                        PlayerMenuItems(
                            navController = navController,
                            expanded = expanded
                        )
                    }
                }

            }

            IconToggleButton(
                checked = shuffleChecked,
                onCheckedChange = { shuffleChecked = it },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_shuffle_24),
                    contentDescription = stringResource(id = R.string.str_moreOptions)
                )
            }

            IconToggleButton(
                checked = repeatChecked,
                onCheckedChange = { repeatChecked = it }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_repeat_24),
                    contentDescription = stringResource(id = R.string.str_moreOptions)
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO */ },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_queue_music_24), 
                    contentDescription = stringResource(id = R.string.str_queue)
                )
            }
        }
    )
}

@Composable
fun PlayerMenuItems(
    navController: NavController,
    expanded: MutableState<Boolean>
) {
    Divider()
    DropdownMenuItem(
        text = { Text(text = stringResource(id = R.string.str_settings)) },
        onClick = {
            navController.navigate(Screens.SettingsScreen.route)
            expanded.value = false
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_outlined_settings_24),
                contentDescription = stringResource(id = R.string.desc_settings)
            )
        }
    )
}