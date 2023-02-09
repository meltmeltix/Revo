package com.alessiocameroni.revomusicplayer.ui.screens.player

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.alessiocameroni.revomusicplayer.R

@Composable
fun CenterSongControls(
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
fun LeftSongControls(
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
fun RightSongControls(
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