package com.alessiocameroni.revomusicplayer.uiComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.alessiocameroni.revomusicplayer.R

@Preview
@Composable
fun PreviewComponent() {
    LeftSongControls(
        modifier = Modifier
            .size(360.dp, 280.dp)
            .background(Color.Yellow),
        floatPosition = 0.5f
    )
}

@Composable
fun CenterSongControls(
    modifier: Modifier,
    sPosition: Float
) {

}

@Composable
fun LeftSongControls(
    modifier: Modifier,
    floatPosition: Float
) {
    var sliderPosition by remember { mutableStateOf(floatPosition) }

    val constraints = ConstraintSet {
        val songSlider = createRefFor("SongSlider")
        val textMinutes = createRefFor("MinutesText")
        val boxControlButtons = createRefFor("BoxControlButtons")

        constrain(songSlider) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            end.linkTo(parent.end)
        }

        constrain(textMinutes) {
            start.linkTo(parent.start)
            top.linkTo(songSlider.bottom)
            end.linkTo(parent.end)
        }

        constrain(boxControlButtons) {
            start.linkTo(parent.start)
            top.linkTo(textMinutes.bottom)
            bottom.linkTo(parent.bottom)
        }
    }

    ConstraintLayout(
        constraints,
        modifier = modifier
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

        Box(
            modifier = Modifier
                .layoutId("BoxControlButtons")
                .background(Color.Blue)
                .size(300.dp, 200.dp)
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
                        .width(120.dp),
                    onClick = {  },
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp
                    ),
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
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
                        painter = painterResource(id = R.drawable.ic_filled_skip_previous_24),
                        contentDescription = stringResource(id = R.string.desc_skipprevious),
                        modifier = Modifier.size(FloatingActionButtonDefaults.LargeIconSize)
                    )
                }

                LargeFloatingActionButton(
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
                        painter = painterResource(id = R.drawable.ic_filled_skip_next_24),
                        contentDescription = stringResource(id = R.string.desc_skipnext),
                        modifier = Modifier.size(FloatingActionButtonDefaults.LargeIconSize)
                    )
                }
            }
        }
    }
}

@Composable
fun RightSongControls(
    modifier: Modifier
) {

}