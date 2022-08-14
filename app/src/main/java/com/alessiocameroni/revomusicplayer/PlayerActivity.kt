package com.alessiocameroni.revomusicplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.FloatingActionButtonDefaults.elevation
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.alessiocameroni.revomusicplayer.ui.theme.RevoMusicPlayerTheme

class PlayerActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var sliderPosition by remember { mutableStateOf(0.5f) }

            RevoMusicPlayerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            SmallTopAppBar(
                                title = { Text(text = "") },
                                navigationIcon = {
                                    IconButton(onClick = { }) {
                                        Icon(
                                            painter = painterResource(
                                                id = R.drawable.ic_baseline_keyboard_arrow_down_24
                                            ),
                                            contentDescription = stringResource(
                                                id = R.string.desc_closemusic
                                            )
                                        )
                                    }
                                }
                            )
                        },
                    ) { padding ->
                        val constraints = ConstraintSet {
                            val albumCover = createRefFor("AlbumCover")
                            val songInformation = createRefFor("SongInformation")
                            val songControls = createRefFor("SongControls")
                            val bottomOptions = createRefFor("BottomOptions")

                            constrain(albumCover) {
                                start.linkTo(parent.start)
                                top.linkTo(parent.top)
                                end.linkTo(parent.end)
                                bottom.linkTo(songInformation.top)
                            }

                            constrain(songInformation) {
                                start.linkTo(parent.start)
                                top.linkTo(albumCover.bottom)
                                end.linkTo(parent.end)
                            }

                            constrain(songControls) {
                                start.linkTo(parent.start)
                                top.linkTo(songInformation.bottom)
                                end.linkTo(parent.end)
                            }

                            constrain(bottomOptions) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom)
                            }
                        }

                        ConstraintLayout(
                            constraints, modifier = Modifier
                                .fillMaxSize()
                                .padding(padding)
                        ) {
                            Box(
                                modifier = Modifier
                                    .layoutId("AlbumCover")
                                    .size(360.dp)
                                    .padding(10.dp, 10.dp, 10.dp, 20.dp)
                                    .clip(MaterialTheme.shapes.extraLarge)
                                    .background(MaterialTheme.colorScheme.primary)
                            ) {

                            }

                            Box(
                                modifier = Modifier
                                    .layoutId("SongInformation")
                                    .size(360.dp, 80.dp)
                                    .padding(10.dp, 10.dp, 10.dp, 5.dp)
                            ) {
                                val songInfoConstraints = ConstraintSet {
                                    val songNameText = createRefFor("SongNameText")
                                    val artistNameText = createRefFor("ArtistNameText")
                                    val favoriteIconButton = createRefFor("FavoriteIconButton")

                                    constrain(songNameText) {
                                        start.linkTo(parent.start)
                                        top.linkTo(parent.top)
                                        bottom.linkTo(artistNameText.top)
                                    }

                                    constrain(artistNameText) {
                                        start.linkTo(parent.start)
                                        top.linkTo(songNameText.bottom)
                                        bottom.linkTo(parent.bottom)
                                    }

                                    constrain(favoriteIconButton) {
                                        top.linkTo(parent.top)
                                        end.linkTo(parent.end)
                                        bottom.linkTo(parent.bottom)
                                    }
                                }

                                ConstraintLayout(
                                    songInfoConstraints,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .layoutId("SongNameText")
                                            .width(300.dp),
                                        text = "SongName",
                                        textAlign = TextAlign.Start,
                                        style = MaterialTheme.typography.headlineSmall,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )

                                    Text(
                                        modifier = Modifier
                                            .layoutId("ArtistNameText")
                                            .width(300.dp),
                                        text = "ArtistName",
                                        textAlign = TextAlign.Start,
                                        style = MaterialTheme.typography.titleMedium,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )

                                    IconButton(
                                        modifier = Modifier
                                            .layoutId("FavoriteIconButton"),
                                        onClick = { }
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_outline_favorite_border_24),
                                            contentDescription = stringResource(id = R.string.desc_favorite)
                                        )
                                    }
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .layoutId("SongControls")
                                    .size(360.dp, 180.dp)
                                    .padding(10.dp, 5.dp, 10.dp, 10.dp)
                            ) {
                                val songControlsConstraints = ConstraintSet{
                                    val songSlider = createRefFor("SongSlider")
                                    val minutesText = createRefFor("MinutesText")
                                    val previousFAB = createRefFor("PreviousFAB")
                                    val playFAB = createRefFor("PlayFAB")
                                    val nextFAB = createRefFor("NextFAB")

                                    constrain(songSlider) {
                                        start.linkTo(parent.start)
                                        top.linkTo(parent.top)
                                        end.linkTo(parent.end)
                                    }

                                    constrain(minutesText) {
                                        start.linkTo(parent.start)
                                        top.linkTo(songSlider.bottom)
                                        end.linkTo(parent.end)
                                    }

                                    constrain(previousFAB) {
                                        start.linkTo(parent.start)
                                        top.linkTo(minutesText.bottom)
                                        end.linkTo(playFAB.start)
                                        bottom.linkTo(parent.bottom)
                                    }

                                    constrain(playFAB) {
                                        start.linkTo(parent.start)
                                        top.linkTo(minutesText.bottom)
                                        end.linkTo(parent.end)
                                        bottom.linkTo(parent.bottom)
                                    }

                                    constrain(nextFAB) {
                                        start.linkTo(playFAB.end)
                                        top.linkTo(minutesText.bottom)
                                        end.linkTo(parent.end)
                                        bottom.linkTo(parent.bottom)
                                    }
                                }

                                ConstraintLayout(
                                    songControlsConstraints,
                                    modifier = Modifier
                                        .fillMaxSize()
                                ) {
                                    Slider(
                                        modifier = Modifier
                                            .layoutId("SongSlider"),
                                        value = sliderPosition,
                                        onValueChange = { sliderPosition = it }
                                    )

                                    Text(
                                        modifier = Modifier
                                            .layoutId("MinutesText")
                                            .width(360.dp),
                                        text = "10:00/10:00",
                                        textAlign = TextAlign.Right,
                                        style = MaterialTheme.typography.labelSmall
                                    )

                                    FloatingActionButton(
                                        modifier = Modifier
                                            .layoutId("PreviousFAB")
                                            .clip(RoundedCornerShape(16.dp)),
                                        onClick = {  },
                                        elevation = elevation(
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
                                        elevation = elevation(
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
                                        elevation = elevation(
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
                                    .layoutId("BottomOptions")
                                    .size(360.dp, 80.dp)
                                    .padding(10.dp, 0.dp, 10.dp, 25.dp)
                                    .background(Color.Yellow)
                            ) {

                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewScreen() {
    var sliderPosition by remember { mutableStateOf(0.5f) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                SmallTopAppBar(
                    title = { Text(text = "") },
                    navigationIcon = {
                        IconButton(onClick = { }) {
                            Icon(
                                painter = painterResource(
                                    id = R.drawable.ic_baseline_keyboard_arrow_down_24
                                ),
                                contentDescription = stringResource(
                                    id = R.string.desc_closemusic
                                )
                            )
                        }
                    }
                )
            },
        ) { padding ->
            val constraints = ConstraintSet {
                val albumCover = createRefFor("AlbumCover")
                val songInformation = createRefFor("SongInformation")
                val songControls = createRefFor("SongControls")
                val bottomOptions = createRefFor("BottomOptions")

                constrain(albumCover) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(songInformation.top)
                }

                constrain(songInformation) {
                    start.linkTo(parent.start)
                    top.linkTo(albumCover.bottom)
                    end.linkTo(parent.end)
                }

                constrain(songControls) {
                    start.linkTo(parent.start)
                    top.linkTo(songInformation.bottom)
                    end.linkTo(parent.end)
                }

                constrain(bottomOptions) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
            }

            ConstraintLayout(
                constraints, modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Box(
                    modifier = Modifier
                        .layoutId("AlbumCover")
                        .size(360.dp)
                        .padding(10.dp, 10.dp, 10.dp, 20.dp)
                        .clip(MaterialTheme.shapes.extraLarge)
                        .background(MaterialTheme.colorScheme.primary)
                ) {

                }

                Box(
                    modifier = Modifier
                        .layoutId("SongInformation")
                        .size(360.dp, 80.dp)
                        .padding(10.dp, 10.dp, 10.dp, 5.dp)
                ) {
                    val songInfoConstraints = ConstraintSet {
                        val songNameText = createRefFor("SongNameText")
                        val artistNameText = createRefFor("ArtistNameText")
                        val favoriteIconButton = createRefFor("FavoriteIconButton")

                        constrain(songNameText) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(artistNameText.top)
                        }

                        constrain(artistNameText) {
                            start.linkTo(parent.start)
                            top.linkTo(songNameText.bottom)
                            bottom.linkTo(parent.bottom)
                        }

                        constrain(favoriteIconButton) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
                    }

                    ConstraintLayout(
                        songInfoConstraints,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            modifier = Modifier
                                .layoutId("SongNameText")
                                .width(300.dp),
                            text = "SongName",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.headlineSmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            modifier = Modifier
                                .layoutId("ArtistNameText")
                                .width(300.dp),
                            text = "ArtistName",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        IconButton(
                            modifier = Modifier
                                .layoutId("FavoriteIconButton"),
                            onClick = { }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_outline_favorite_border_24),
                                contentDescription = stringResource(id = R.string.desc_favorite)
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .layoutId("SongControls")
                        .size(360.dp, 180.dp)
                        .padding(10.dp, 5.dp, 10.dp, 10.dp)
                ) {
                    val songControlsConstraints = ConstraintSet{
                        val songSlider = createRefFor("SongSlider")
                        val minutesText = createRefFor("MinutesText")
                        val previousFAB = createRefFor("PreviousFAB")
                        val playFAB = createRefFor("PlayFAB")
                        val nextFAB = createRefFor("NextFAB")

                        constrain(songSlider) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }

                        constrain(minutesText) {
                            start.linkTo(parent.start)
                            top.linkTo(songSlider.bottom)
                            end.linkTo(parent.end)
                        }

                        constrain(previousFAB) {
                            start.linkTo(parent.start)
                            top.linkTo(minutesText.bottom)
                            end.linkTo(playFAB.start)
                            bottom.linkTo(parent.bottom)
                        }

                        constrain(playFAB) {
                            start.linkTo(parent.start)
                            top.linkTo(minutesText.bottom)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }

                        constrain(nextFAB) {
                            start.linkTo(playFAB.end)
                            top.linkTo(minutesText.bottom)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
                    }

                    ConstraintLayout(
                        songControlsConstraints,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Slider(
                            modifier = Modifier
                                .layoutId("SongSlider"),
                            value = sliderPosition,
                            onValueChange = { sliderPosition = it }
                        )

                        Text(
                            modifier = Modifier
                                .layoutId("MinutesText")
                                .width(360.dp),
                            text = "10:00/10:00",
                            textAlign = TextAlign.Right,
                            style = MaterialTheme.typography.labelSmall
                        )

                        FloatingActionButton(
                            modifier = Modifier
                                .layoutId("PreviousFAB")
                                .clip(RoundedCornerShape(16.dp)),
                            onClick = {  },
                            elevation = elevation(
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
                            elevation = elevation(
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
                            elevation = elevation(
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
                        .layoutId("BottomOptions")
                        .size(360.dp, 80.dp)
                        .padding(10.dp, 0.dp, 10.dp, 25.dp)
                        .background(Color.Yellow)
                ) {

                }
            }
        }
    }
}