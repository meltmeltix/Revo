package com.meltix.revo.ui.screens.player

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.meltix.pixely_components.RoundedDropDownMenu
import com.meltix.revo.R
import com.meltix.revo.data.classes.player.PlayerLayout
import com.meltix.revo.ui.navigation.RootScreens

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
                    contentDescription = stringResource(id = R.string.str_closePlayer)
                )
            }
        }
    )
}

@Composable
internal fun PlayerBottomActionBar(
    navController: NavController,
    boolShuffleChecked: Boolean,
    boolRepeatChecked: Boolean,
    openBottomSheet: MutableState<Boolean>
) {
    var shuffleChecked by remember { mutableStateOf(boolShuffleChecked) }
    var repeatChecked by remember { mutableStateOf(boolRepeatChecked) }
    val expanded = remember { mutableStateOf(false) }

    BottomAppBar(
        actions = {
            PlayerBottomAppBarDropDownMenu(
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
internal fun PlayerBottomAppBarDropDownMenu(
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
            HorizontalDivider()
            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.str_settings)) },
                onClick = {
                    navController.navigate(RootScreens.SettingsGraph.route)
                    expanded.value = false
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outlined_settings_24),
                        contentDescription = stringResource(id = R.string.str_settings)
                    )
                }
            )
        }
    }
}

// Player Controls
@Composable
internal fun PlayerControls(
    modifier: Modifier = Modifier,
    buttonsLayout: PlayerLayout,
    floatSliderPosition: Float
) {
    var sliderPosition by remember { mutableFloatStateOf(floatSliderPosition) }
    var favouriteChecked by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 2.dp),
                    text = "SongName",
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    modifier = Modifier.padding(vertical = 2.dp),
                    text = "ArtistName",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            IconToggleButton(
                checked = favouriteChecked,
                onCheckedChange = { favouriteChecked = it }
            ) {
                if(favouriteChecked) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filled_favorite_24),
                        contentDescription = stringResource(id = R.string.str_addToFavorites)
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outlined_favorite_24),
                        contentDescription = stringResource(id = R.string.str_addToFavorites)
                    )
                }
            }
        }

        when (buttonsLayout) {
            PlayerLayout.LEFT -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.Start)
                ) {
                    PlayButton()
                    PreviousButton()
                    NextButton()
                }
            }
            PlayerLayout.CENTER -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterHorizontally)
                ) {
                    PreviousButton()
                    PlayButton()
                    NextButton()
                }
            }
            PlayerLayout.RIGHT -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.End)
                ) {
                    PreviousButton()
                    NextButton()
                    PlayButton()
                }
            }
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row( modifier = Modifier.fillMaxWidth() ) {
                Text(
                    modifier = Modifier,
                    style = MaterialTheme.typography.labelSmall,
                    text = "02:00"
                )
                
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    modifier = Modifier,
                    style = MaterialTheme.typography.labelSmall,
                    text = "04:00"
                )
            }

            Slider(
                modifier = Modifier,
                value = sliderPosition,
                onValueChange = { sliderPosition = it }
            )
        }
    }
}

@Composable
private fun PreviousButton() {
    FilledTonalIconButton(
        modifier = Modifier.size(70.dp),
        onClick = { /*TODO*/ }
    ) {
        Icon(
            modifier = Modifier.size(30.dp),
            painter = painterResource(id = R.drawable.ic_filled_skip_previous_24),
            contentDescription = stringResource(id = R.string.str_previous)
        )
    }
}

@Composable
private fun PlayButton() {
    FilledTonalIconButton(
        modifier = Modifier.size(110.dp, 70.dp),
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
            contentDescription = stringResource(id = R.string.str_play)
        )
    }
}

@Composable
private fun NextButton() {
    FilledTonalIconButton(
        modifier = Modifier.size(70.dp),
        onClick = { /*TODO*/ }
    ) {
        Icon(
            modifier = Modifier.size(30.dp),
            painter = painterResource(id = R.drawable.ic_filled_skip_next_24),
            contentDescription = stringResource(id = R.string.str_next)
        )
    }
}

// Queue Sheet 
