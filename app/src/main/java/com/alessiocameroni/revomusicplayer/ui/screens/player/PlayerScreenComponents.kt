package com.alessiocameroni.revomusicplayer.ui.screens.player

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import androidx.navigation.NavController
import com.alessiocameroni.pixely_components.RoundedDropDownMenu
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.ui.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PlayerTopActionBar(navController: NavController) {
    val interactionSource = remember { MutableInteractionSource() }

    TopAppBar(
        title = { Text(text = "") },
        modifier = Modifier.clickable(
            interactionSource = interactionSource,
            indication = null
        ) { navController.navigateUp() },
        navigationIcon = {
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(40.dp)
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_keyboard_arrow_down_24),
                    contentDescription = stringResource(id = R.string.desc_closeMusic)
                )
            }
        }
    )
}

@Composable
internal fun BottomActionBar(
    navController: NavController,
    boolShuffleChecked: Boolean,
    boolRepeatChecked: Boolean,
    openBottomSheet: MutableState<Boolean>
) {
    var shuffleChecked by rememberSaveable { mutableStateOf(boolShuffleChecked) }
    var repeatChecked by rememberSaveable { mutableStateOf(boolRepeatChecked) }
    val expanded = rememberSaveable { mutableStateOf(false) }

    BottomAppBar(
        actions = {
            BottomAppBarDropDownMenu(
                expanded = expanded,
                navController = navController
            )

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
                onClick = { openBottomSheet.value = true },
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
internal fun BottomAppBarDropDownMenu(
    expanded: MutableState<Boolean>,
    navController: NavController,
) {
    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
        IconButton(onClick = { expanded.value = true }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                contentDescription = stringResource(id = R.string.str_moreOptions)
            )
        }

        RoundedDropDownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
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
    }
}

// Song Controls
@Composable
internal fun CenterSongControls(
    modifier: Modifier,
    floatSliderPosition: Float
) {
    var sliderPosition by remember { mutableStateOf(floatSliderPosition) }
    var favouriteChecked by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                        .fillMaxWidth(),
                    text = "SongName",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                        .fillMaxWidth(),
                    text = "ArtistName",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            IconToggleButton(
                modifier = Modifier
                    .padding(start = 15.dp),
                checked = favouriteChecked,
                onCheckedChange = { favouriteChecked = it }
            ) {
                if(favouriteChecked) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filled_favorite_24),
                        contentDescription = stringResource(id = R.string.desc_favorite)
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outlined_favorite_24),
                        contentDescription = stringResource(id = R.string.desc_favorite)
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterHorizontally)
        ) {
            FilledTonalIconButton(
                modifier = Modifier
                    .size(70.dp),
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.ic_filled_skip_previous_24),
                    contentDescription = stringResource(id = R.string.desc_skipPrevious)
                )
            }

            FilledTonalIconButton(
                modifier = Modifier
                    .size(110.dp, 70.dp),
                colors = IconButtonDefaults.filledTonalIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                ),
                shape = MaterialTheme.shapes.large,
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.ic_baseline_play_arrow_24),
                    contentDescription = stringResource(id = R.string.desc_playSong)
                )
            }

            FilledTonalIconButton(
                modifier = Modifier
                    .size(70.dp),
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.ic_filled_skip_next_24),
                    contentDescription = stringResource(id = R.string.desc_skipNext)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val constraints = ConstraintSet {
                val textCurrentTime = createRefFor("TextCurrentTime")
                val textSongTime = createRefFor("TextSongTime")
                val progressBarSong = createRefFor("ProgressBarSong")

                constrain(textCurrentTime) {
                    start.linkTo(parent.start)
                    bottom.linkTo(progressBarSong.top)
                }

                constrain(textSongTime) {
                    end.linkTo(parent.end)
                    bottom.linkTo(progressBarSong.top)
                }

                constrain(progressBarSong) {
                    bottom.linkTo(parent.bottom)
                }
            }

            ConstraintLayout(
                constraints,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .layoutId("TextCurrentTime"),
                    style = MaterialTheme.typography.labelSmall,
                    text = "02:00"
                )

                Text(
                    modifier = Modifier
                        .layoutId("TextSongTime"),
                    style = MaterialTheme.typography.labelSmall,
                    text = "04:00"
                )

                Slider(
                    modifier = Modifier
                        .layoutId("ProgressBarSong"),
                    value = sliderPosition,
                    onValueChange = { sliderPosition = it }
                )
            }
        }
    }
}

@Composable
internal fun LeftSongControls(
    modifier: Modifier,
    floatSliderPosition: Float
) {
    var sliderPosition by remember { mutableStateOf(floatSliderPosition) }
    var favouriteChecked by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                        .fillMaxWidth(),
                    text = "SongName",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                        .fillMaxWidth(),
                    text = "ArtistName",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            IconToggleButton(
                modifier = Modifier
                    .padding(start = 15.dp),
                checked = favouriteChecked,
                onCheckedChange = { favouriteChecked = it }
            ) {
                if(favouriteChecked) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filled_favorite_24),
                        contentDescription = stringResource(id = R.string.desc_favorite)
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outlined_favorite_24),
                        contentDescription = stringResource(id = R.string.desc_favorite)
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.Start)
        ) {


            FilledTonalIconButton(
                modifier = Modifier
                    .size(110.dp, 70.dp),
                colors = IconButtonDefaults.filledTonalIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                ),
                shape = MaterialTheme.shapes.large,
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.ic_baseline_play_arrow_24),
                    contentDescription = stringResource(id = R.string.desc_playSong)
                )
            }

            FilledTonalIconButton(
                modifier = Modifier
                    .size(70.dp),
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.ic_filled_skip_previous_24),
                    contentDescription = stringResource(id = R.string.desc_skipPrevious)
                )
            }

            FilledTonalIconButton(
                modifier = Modifier
                    .size(70.dp),
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.ic_filled_skip_next_24),
                    contentDescription = stringResource(id = R.string.desc_skipNext)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val constraints = ConstraintSet {
                val textCurrentTime = createRefFor("TextCurrentTime")
                val textSongTime = createRefFor("TextSongTime")
                val progressBarSong = createRefFor("ProgressBarSong")

                constrain(textCurrentTime) {
                    start.linkTo(parent.start)
                    bottom.linkTo(progressBarSong.top)
                }

                constrain(textSongTime) {
                    end.linkTo(parent.end)
                    bottom.linkTo(progressBarSong.top)
                }

                constrain(progressBarSong) {
                    bottom.linkTo(parent.bottom)
                }
            }

            ConstraintLayout(
                constraints,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .layoutId("TextCurrentTime"),
                    style = MaterialTheme.typography.labelSmall,
                    text = "02:00"
                )

                Text(
                    modifier = Modifier
                        .layoutId("TextSongTime"),
                    style = MaterialTheme.typography.labelSmall,
                    text = "04:00"
                )

                Slider(
                    modifier = Modifier
                        .layoutId("ProgressBarSong"),
                    value = sliderPosition,
                    onValueChange = { sliderPosition = it }
                )
            }
        }
    }
}

@Composable
internal fun RightSongControls(
    modifier: Modifier,
    floatSliderPosition: Float
) {
    var sliderPosition by remember { mutableStateOf(floatSliderPosition) }
    var favouriteChecked by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                        .fillMaxWidth(),
                    text = "SongName",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                        .fillMaxWidth(),
                    text = "ArtistName",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            IconToggleButton(
                modifier = Modifier
                    .padding(start = 15.dp),
                checked = favouriteChecked,
                onCheckedChange = { favouriteChecked = it }
            ) {
                if(favouriteChecked) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filled_favorite_24),
                        contentDescription = stringResource(id = R.string.desc_favorite)
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outlined_favorite_24),
                        contentDescription = stringResource(id = R.string.desc_favorite)
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.End)
        ) {
            FilledTonalIconButton(
                modifier = Modifier
                    .size(70.dp),
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.ic_filled_skip_previous_24),
                    contentDescription = stringResource(id = R.string.desc_skipPrevious)
                )
            }

            FilledTonalIconButton(
                modifier = Modifier
                    .size(70.dp),
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.ic_filled_skip_next_24),
                    contentDescription = stringResource(id = R.string.desc_skipNext)
                )
            }

            FilledTonalIconButton(
                modifier = Modifier
                    .size(110.dp, 70.dp),
                colors = IconButtonDefaults.filledTonalIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                ),
                shape = MaterialTheme.shapes.large,
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.ic_baseline_play_arrow_24),
                    contentDescription = stringResource(id = R.string.desc_playSong)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val constraints = ConstraintSet {
                val textCurrentTime = createRefFor("TextCurrentTime")
                val textSongTime = createRefFor("TextSongTime")
                val progressBarSong = createRefFor("ProgressBarSong")

                constrain(textCurrentTime) {
                    start.linkTo(parent.start)
                    bottom.linkTo(progressBarSong.top)
                }

                constrain(textSongTime) {
                    end.linkTo(parent.end)
                    bottom.linkTo(progressBarSong.top)
                }

                constrain(progressBarSong) {
                    bottom.linkTo(parent.bottom)
                }
            }

            ConstraintLayout(
                constraints,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .layoutId("TextCurrentTime"),
                    style = MaterialTheme.typography.labelSmall,
                    text = "02:00"
                )

                Text(
                    modifier = Modifier
                        .layoutId("TextSongTime"),
                    style = MaterialTheme.typography.labelSmall,
                    text = "04:00"
                )

                Slider(
                    modifier = Modifier
                        .layoutId("ProgressBarSong"),
                    value = sliderPosition,
                    onValueChange = { sliderPosition = it }
                )
            }
        }
    }
}