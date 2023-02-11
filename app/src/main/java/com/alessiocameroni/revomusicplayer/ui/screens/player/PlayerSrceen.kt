package com.alessiocameroni.revomusicplayer.ui.screens.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.ui.theme.RevoMusicPlayerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(navController: NavController) {
    val sliderPosition by rememberSaveable { mutableStateOf(0.5f) }
    val shuffleChecked by rememberSaveable { mutableStateOf(false) }
    val repeatChecked by rememberSaveable { mutableStateOf(false) }
    val openBottomSheet = rememberSaveable { mutableStateOf(false) }

    RevoMusicPlayerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = { TopActionBar(navController) },
                bottomBar = {
                    BottomActionBar(
                        navController = navController,
                        boolShuffleChecked = shuffleChecked,
                        boolRepeatChecked = repeatChecked,
                        openBottomSheet = openBottomSheet
                    )
                },
                content = { padding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                start = 30.dp,
                                top = padding.calculateTopPadding(),
                                end = 30.dp,
                                bottom = padding.calculateBottomPadding() + 15.dp,
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .weight(1f)
                                .clip(MaterialTheme.shapes.extraLarge)
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier,
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .size(120.dp),
                                    painter = painterResource(id = R.drawable.ic_baseline_music_note_24),
                                    contentDescription = stringResource(id = R.string.str_songs),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(246.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CenterSongControls(
                                modifier = Modifier,
                                floatSliderPosition = sliderPosition
                            )
                        }
                    }

                    QueueModalBottomSheet(openBottomSheet = openBottomSheet)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun QueueModalBottomSheet(openBottomSheet: MutableState<Boolean>) {
    val skipHalfExpanded by remember { mutableStateOf(true) }
    val bottomSheetState = rememberSheetState(skipHalfExpanded = skipHalfExpanded)

    if (openBottomSheet.value) {
        ModalBottomSheet(
            onDismissRequest = { openBottomSheet.value = false },
            modifier = Modifier
                .fillMaxSize(),
            sheetState = bottomSheetState
        ) {

        }
    }
}