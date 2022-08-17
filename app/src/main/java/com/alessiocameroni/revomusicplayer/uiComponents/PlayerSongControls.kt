package com.alessiocameroni.revomusicplayer.uiComponents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.alessiocameroni.revomusicplayer.R

@Preview(showBackground = true)
@Composable
fun PreviewRight() {
    RightSongControls(
        modifier = Modifier
            .width(360.dp),
        floatPosition = 0.5f,
        boolShuffleChecked = false,
        boolRepeatChecked = false
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewLeft() {
    LeftSongControls(
        modifier = Modifier
            .width(360.dp),
        floatPosition = 0.5f,
        boolShuffleChecked = false,
        boolRepeatChecked = false
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCenter() {
    CenterSongControls(
        modifier = Modifier
            .width(360.dp),
        floatPosition = 0.5f,
        boolShuffleChecked = false,
        boolRepeatChecked = false
    )
}

@Composable
fun CenterSongControls(
    modifier: Modifier,
    floatPosition: Float,
    boolShuffleChecked: Boolean,
    boolRepeatChecked: Boolean
) {
    var sliderPosition by remember { mutableStateOf(floatPosition) }
    var shuffleChecked by remember { mutableStateOf(boolShuffleChecked) }
    var repeatChecked by remember { mutableStateOf(boolRepeatChecked) }

    val constraints = ConstraintSet {
        val songSlider = createRefFor("SongSlider")
        val textMinutes = createRefFor("MinutesText")
        val boxControlButtons = createRefFor("BoxControlButtons")
        val boxSecondaryButtons = createRefFor("BoxSecondaryButtons")

        constrain(textMinutes) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            end.linkTo(parent.end)
        }

        constrain(songSlider) {
            start.linkTo(parent.start)
            top.linkTo(textMinutes.bottom)
            end.linkTo(parent.end)
        }

        constrain(boxControlButtons) {
            start.linkTo(parent.start)
            top.linkTo(songSlider.bottom)
            end.linkTo(parent.end)
        }

        constrain(boxSecondaryButtons) {
            start.linkTo(parent.start)
            top.linkTo(boxControlButtons.bottom)
            start.linkTo(parent.start)
        }
    }

    ConstraintLayout(
        constraints,
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .layoutId("MinutesText")
                .width(360.dp),
            text = "10:00/10:00",
            textAlign = TextAlign.Right,
            style = MaterialTheme.typography.labelSmall
        )

        Slider(
            modifier = Modifier
                .layoutId("SongSlider")
                .padding(bottom = 10.dp),
            value = sliderPosition,
            onValueChange = { sliderPosition = it }
        )

        Box(
            modifier = Modifier
                .layoutId("BoxControlButtons")
                .fillMaxWidth()
                .height(100.dp)
                .padding(bottom = 20.dp)
        ) {
            val constraintsBoxControlsButtons = ConstraintSet {
                val previousFAB = createRefFor("PreviousFAB")
                val playFAB = createRefFor("PlayFAB")
                val nextFAB = createRefFor("NextFAB")

                constrain(previousFAB) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(playFAB.start)
                    bottom.linkTo(parent.bottom)
                }

                constrain(playFAB) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }

                constrain(nextFAB) {
                    start.linkTo(playFAB.end)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
            }

            ConstraintLayout(
                constraintsBoxControlsButtons,
                modifier = Modifier.fillMaxSize()
            ) {
                FloatingActionButton(
                    modifier = Modifier
                        .layoutId("PreviousFAB")
                        .clip(RoundedCornerShape(16.dp)),
                    onClick = {  },
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp
                    ),
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.ic_filled_skip_previous_24
                        ),
                        contentDescription = stringResource(
                            id = R.string.desc_skipprevious
                        )
                    )
                }

                LargeFloatingActionButton(
                    modifier = Modifier
                        .layoutId("PlayFAB")
                        .width(130.dp),
                    onClick = {  },
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp
                    ),
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.ic_baseline_play_arrow_24
                        ),
                        contentDescription = stringResource(
                            id = R.string.desc_playsong
                        ),
                        modifier = Modifier
                            .size(FloatingActionButtonDefaults.LargeIconSize)
                    )
                }

                FloatingActionButton(
                    modifier = Modifier
                        .layoutId("NextFAB")
                        .clip(RoundedCornerShape(16.dp)),
                    onClick = {  },
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp
                    ),
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.ic_filled_skip_next_24
                        ),
                        contentDescription = stringResource(
                            id = R.string.desc_skipnext
                        )
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .layoutId("BoxSecondaryButtons")
                .fillMaxWidth()
                .height(110.dp)
        ) {
            val constraintsBoxSecondaryButtons = ConstraintSet{
                val moreButton = createRefFor("MoreButton")
                val queueButton = createRefFor("QueueButton")
                val shuffleButton = createRefFor("ShuffleButton")
                val repeatButton = createRefFor("RepeatButton")

                constrain(moreButton) {
                    top.linkTo(repeatButton.bottom)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }

                constrain(queueButton) {
                    start.linkTo(parent.start)
                    top.linkTo(shuffleButton.bottom)
                    bottom.linkTo(parent.bottom)
                }

                constrain(shuffleButton) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(queueButton.top)
                }

                constrain(repeatButton) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(moreButton.top)
                }
            }

            ConstraintLayout(
                constraintsBoxSecondaryButtons,
                modifier = Modifier.fillMaxSize()
            ) {
                FilledTonalIconButton(
                    modifier = Modifier
                        .layoutId("MoreButton")
                        .width(165.dp),
                    onClick = {  },
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                        contentDescription = stringResource(id = R.string.str_moreoptions)
                    )
                }

                FilledTonalIconButton(
                    modifier = Modifier
                        .layoutId("QueueButton")
                        .width(165.dp),
                    onClick = {  },
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_playlist_play_24),
                        contentDescription = stringResource(id = R.string.str_moreoptions)
                    )
                }

                FilledTonalIconToggleButton(
                    checked = shuffleChecked,
                    onCheckedChange = { shuffleChecked = it },
                    modifier = Modifier
                        .layoutId("ShuffleButton")
                        .width(165.dp),
                    colors = IconButtonDefaults.filledTonalIconToggleButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_shuffle_24),
                        contentDescription = stringResource(id = R.string.str_moreoptions)
                    )
                }

                FilledTonalIconToggleButton(
                    checked = repeatChecked,
                    onCheckedChange = { repeatChecked = it },
                    modifier = Modifier
                        .layoutId("RepeatButton")
                        .width(165.dp),
                    colors = IconButtonDefaults.filledTonalIconToggleButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_repeat_24),
                        contentDescription = stringResource(id = R.string.str_moreoptions)
                    )
                }
            }
        }
    }
}

@Composable
fun LeftSongControls(
    modifier: Modifier,
    floatPosition: Float,
    boolShuffleChecked: Boolean,
    boolRepeatChecked: Boolean
) {
    var sliderPosition by remember { mutableStateOf(floatPosition) }
    var shuffleChecked by remember { mutableStateOf(boolShuffleChecked) }
    var repeatChecked by remember { mutableStateOf(boolRepeatChecked) }

    val constraints = ConstraintSet {
        val songSlider = createRefFor("SongSlider")
        val textMinutes = createRefFor("MinutesText")
        val boxControlButtons = createRefFor("BoxControlButtons")
        val boxSecondaryButtons = createRefFor("BoxSecondaryButtons")

        constrain(textMinutes) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            end.linkTo(parent.end)
        }

        constrain(songSlider) {
            start.linkTo(parent.start)
            top.linkTo(textMinutes.bottom)
            end.linkTo(parent.end)
        }

        constrain(boxControlButtons) {
            start.linkTo(parent.start)
            top.linkTo(songSlider.bottom)
            bottom.linkTo(parent.bottom)
        }

        constrain(boxSecondaryButtons) {
            top.linkTo(songSlider.bottom)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        }
    }

    ConstraintLayout(
        constraints,
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .layoutId("MinutesText")
                .width(360.dp),
            text = "10:00/10:00",
            textAlign = TextAlign.Right,
            style = MaterialTheme.typography.labelSmall
        )

        Slider(
            modifier = Modifier
                .layoutId("SongSlider")
                .padding(bottom = 10.dp),
            value = sliderPosition,
            onValueChange = { sliderPosition = it }
        )

        Box(
            modifier = Modifier
                .layoutId("BoxControlButtons")
                .size(210.dp)
        ) {
            val constraintsBoxControlsButtons = ConstraintSet {
                val previousFAB = createRefFor("PreviousFAB")
                val playFAB = createRefFor("PlayFAB")
                val nextFAB = createRefFor("NextFAB")

                constrain(playFAB) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }

                constrain(previousFAB) {
                    start.linkTo(parent.start)
                    top.linkTo(playFAB.bottom)
                    end.linkTo(nextFAB.start)
                    bottom.linkTo(parent.bottom)
                }

                constrain(nextFAB) {
                    start.linkTo(previousFAB.end)
                    top.linkTo(playFAB.bottom)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
            }

            ConstraintLayout(
                constraintsBoxControlsButtons,
                modifier = Modifier.fillMaxSize()
            ) {
                LargeFloatingActionButton(
                    modifier = Modifier
                        .layoutId("PlayFAB")
                        .width(210.dp)
                        .padding(bottom = 10.dp),
                    onClick = {  },
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp
                    ),
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_play_arrow_24),
                        contentDescription = stringResource(id = R.string.desc_playsong),
                        modifier = Modifier
                            .size(FloatingActionButtonDefaults.LargeIconSize)
                    )
                }

                LargeFloatingActionButton(
                    modifier = Modifier
                        .layoutId("PreviousFAB")
                        .clip(RoundedCornerShape(16.dp))
                        .padding(top = 10.dp, end = 10.dp),
                    onClick = {  },
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp
                    ),
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filled_skip_previous_24),
                        contentDescription = stringResource(id = R.string.desc_skipprevious),
                        modifier = Modifier.size(FloatingActionButtonDefaults.LargeIconSize)
                    )
                }

                LargeFloatingActionButton(
                    modifier = Modifier
                        .layoutId("NextFAB")
                        .clip(RoundedCornerShape(16.dp))
                        .padding(start = 10.dp, top = 10.dp),
                    onClick = {  },
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp
                    ),
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filled_skip_next_24),
                        contentDescription = stringResource(id = R.string.desc_skipnext),
                        modifier = Modifier.size(FloatingActionButtonDefaults.LargeIconSize)
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .layoutId("BoxSecondaryButtons")
                .padding(start = 20.dp)
                .size(100.dp, 210.dp)
        ) {
            val constraintsBoxSecondaryButtons = ConstraintSet{
                val moreButton = createRefFor("MoreButton")
                val queueButton = createRefFor("QueueButton")
                val shuffleButton = createRefFor("ShuffleButton")
                val repeatButton = createRefFor("RepeatButton")

                constrain(moreButton) {
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }

                constrain(queueButton) {
                    end.linkTo(parent.end)
                    bottom.linkTo(moreButton.top)
                }

                constrain(shuffleButton) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }

                constrain(repeatButton) {
                    top.linkTo(shuffleButton.bottom)
                    end.linkTo(parent.end)
                }
            }

            ConstraintLayout(
                constraintsBoxSecondaryButtons,
                modifier = Modifier.fillMaxSize()
            ) {
                FilledTonalIconButton(
                    modifier = Modifier
                        .layoutId("MoreButton")
                        .fillMaxWidth(),
                    onClick = {  },
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                        contentDescription = stringResource(id = R.string.str_moreoptions)
                    )
                }

                FilledTonalIconButton(
                    modifier = Modifier
                        .layoutId("QueueButton")
                        .fillMaxWidth(),
                    onClick = {  },
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_playlist_play_24),
                        contentDescription = stringResource(id = R.string.str_moreoptions)
                    )
                }

                FilledTonalIconToggleButton(
                    checked = shuffleChecked,
                    onCheckedChange = { shuffleChecked = it },
                    modifier = Modifier
                        .layoutId("ShuffleButton")
                        .fillMaxWidth(),
                    colors = IconButtonDefaults.filledTonalIconToggleButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_shuffle_24),
                        contentDescription = stringResource(id = R.string.str_moreoptions)
                    )
                }

                FilledTonalIconToggleButton(
                    checked = repeatChecked,
                    onCheckedChange = { repeatChecked = it },
                    modifier = Modifier
                        .layoutId("RepeatButton")
                        .fillMaxWidth(),
                    colors = IconButtonDefaults.filledTonalIconToggleButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_repeat_24),
                        contentDescription = stringResource(id = R.string.str_moreoptions)
                    )
                }
            }
        }
    }
}

@Composable
fun RightSongControls(
    modifier: Modifier,
    floatPosition: Float,
    boolShuffleChecked: Boolean,
    boolRepeatChecked: Boolean
) {
    var sliderPosition by remember { mutableStateOf(floatPosition) }
    var shuffleChecked by remember { mutableStateOf(boolShuffleChecked) }
    var repeatChecked by remember { mutableStateOf(boolRepeatChecked) }

    val constraints = ConstraintSet {
        val songSlider = createRefFor("SongSlider")
        val textMinutes = createRefFor("MinutesText")
        val boxControlButtons = createRefFor("BoxControlButtons")
        val boxSecondaryButtons = createRefFor("BoxSecondaryButtons")

        constrain(textMinutes) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            end.linkTo(parent.end)
        }

        constrain(songSlider) {
            start.linkTo(parent.start)
            top.linkTo(textMinutes.bottom)
            end.linkTo(parent.end)
        }

        constrain(boxControlButtons) {
            top.linkTo(songSlider.bottom)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        }

        constrain(boxSecondaryButtons) {
            top.linkTo(songSlider.bottom)
            start.linkTo(parent.start)
            bottom.linkTo(parent.bottom)
        }
    }

    ConstraintLayout(
        constraints,
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .layoutId("MinutesText")
                .width(360.dp),
            text = "10:00/10:00",
            textAlign = TextAlign.Right,
            style = MaterialTheme.typography.labelSmall
        )

        Slider(
            modifier = Modifier
                .layoutId("SongSlider")
                .padding(bottom = 10.dp),
            value = sliderPosition,
            onValueChange = { sliderPosition = it }
        )

        Box(
            modifier = Modifier
                .layoutId("BoxControlButtons")
                .size(210.dp)
        ) {
            val constraintsBoxControlsButtons = ConstraintSet {
                val previousFAB = createRefFor("PreviousFAB")
                val playFAB = createRefFor("PlayFAB")
                val nextFAB = createRefFor("NextFAB")

                constrain(playFAB) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }

                constrain(previousFAB) {
                    start.linkTo(parent.start)
                    top.linkTo(playFAB.bottom)
                    end.linkTo(nextFAB.start)
                    bottom.linkTo(parent.bottom)
                }

                constrain(nextFAB) {
                    start.linkTo(previousFAB.end)
                    top.linkTo(playFAB.bottom)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
            }

            ConstraintLayout(
                constraintsBoxControlsButtons,
                modifier = Modifier.fillMaxSize()
            ) {
                LargeFloatingActionButton(
                    modifier = Modifier
                        .layoutId("PlayFAB")
                        .width(210.dp)
                        .padding(bottom = 10.dp),
                    onClick = {  },
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp
                    ),
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_play_arrow_24),
                        contentDescription = stringResource(id = R.string.desc_playsong),
                        modifier = Modifier
                            .size(FloatingActionButtonDefaults.LargeIconSize)
                    )
                }

                LargeFloatingActionButton(
                    modifier = Modifier
                        .layoutId("PreviousFAB")
                        .clip(RoundedCornerShape(16.dp))
                        .padding(top = 10.dp, end = 10.dp),
                    onClick = {  },
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp
                    ),
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filled_skip_previous_24),
                        contentDescription = stringResource(id = R.string.desc_skipprevious),
                        modifier = Modifier.size(FloatingActionButtonDefaults.LargeIconSize)
                    )
                }

                LargeFloatingActionButton(
                    modifier = Modifier
                        .layoutId("NextFAB")
                        .clip(RoundedCornerShape(16.dp))
                        .padding(start = 10.dp, top = 10.dp),
                    onClick = {  },
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp
                    ),
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filled_skip_next_24),
                        contentDescription = stringResource(id = R.string.desc_skipnext),
                        modifier = Modifier.size(FloatingActionButtonDefaults.LargeIconSize)
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .layoutId("BoxSecondaryButtons")
                .padding(end = 20.dp)
                .size(100.dp, 210.dp)
        ) {
            val constraintsBoxSecondaryButtons = ConstraintSet{
                val moreButton = createRefFor("MoreButton")
                val queueButton = createRefFor("QueueButton")
                val shuffleButton = createRefFor("ShuffleButton")
                val repeatButton = createRefFor("RepeatButton")

                constrain(moreButton) {
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                }

                constrain(queueButton) {
                    start.linkTo(parent.start)
                    bottom.linkTo(moreButton.top)
                }

                constrain(shuffleButton) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }

                constrain(repeatButton) {
                    start.linkTo(parent.start)
                    top.linkTo(shuffleButton.bottom)
                }
            }

            ConstraintLayout(
                constraintsBoxSecondaryButtons,
                modifier = Modifier.fillMaxSize()
            ) {
                FilledTonalIconButton(
                    modifier = Modifier
                        .layoutId("MoreButton")
                        .fillMaxWidth(),
                    onClick = {  },
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                        contentDescription = stringResource(id = R.string.str_moreoptions)
                    )
                }

                FilledTonalIconButton(
                    modifier = Modifier
                        .layoutId("QueueButton")
                        .fillMaxWidth(),
                    onClick = {  },
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_playlist_play_24),
                        contentDescription = stringResource(id = R.string.str_moreoptions)
                    )
                }

                FilledTonalIconToggleButton(
                    checked = shuffleChecked,
                    onCheckedChange = { shuffleChecked = it },
                    modifier = Modifier
                        .layoutId("ShuffleButton")
                        .fillMaxWidth(),
                    colors = IconButtonDefaults.filledTonalIconToggleButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_shuffle_24),
                        contentDescription = stringResource(id = R.string.str_moreoptions)
                    )
                }

                FilledTonalIconToggleButton(
                    checked = repeatChecked,
                    onCheckedChange = { repeatChecked = it },
                    modifier = Modifier
                        .layoutId("RepeatButton")
                        .fillMaxWidth(),
                    colors = IconButtonDefaults.filledTonalIconToggleButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_repeat_24),
                        contentDescription = stringResource(id = R.string.str_moreoptions)
                    )
                }
            }
        }
    }
}