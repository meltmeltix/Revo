package com.alessiocameroni.revomusicplayer.ui.screens.player

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import com.alessiocameroni.revomusicplayer.ui.navigation.Screens
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
private fun TopActionBar(navController: NavController) {
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
private fun BottomActionBar(
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
private fun BottomAppBarDropDownMenu(
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

        MaterialTheme(shapes = MaterialTheme.shapes.copy(extraSmall = MaterialTheme.shapes.large)) {
            DropdownMenu(
                modifier = Modifier.width(180.dp),
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QueueModalBottomSheet(openBottomSheet: MutableState<Boolean>) {
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