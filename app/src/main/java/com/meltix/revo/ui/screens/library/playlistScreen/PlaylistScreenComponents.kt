package com.meltix.revo.ui.screens.library.playlistScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.meltix.pixely_components.RoundedDropDownMenu
import com.meltix.revo.R
import com.meltix.revo.ui.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistTopActionbar(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior
) {
    val expandedMenu = remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = stringResource(id = R.string.str_playlists)) },
        actions = {
            IconButton(
                onClick = { navController.navigate(Screens.SearchScreen.route) }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_search_24),
                    contentDescription = stringResource(id = R.string.str_search)
                )
            }

            Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                IconButton(onClick = { expandedMenu.value = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                        contentDescription = stringResource(id = R.string.str_settings)
                    )
                }

                TopBarDropDownMenu(
                    expanded = expandedMenu,
                    navController = navController
                )
            }
        }, scrollBehavior = scrollBehavior
    )
}

@Composable
private fun TopBarDropDownMenu(
    expanded: MutableState<Boolean>,
    navController: NavController
) {
    RoundedDropDownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false }
    ) {
        HorizontalDivider()

        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.str_settings)) },
            onClick = {
                navController.navigate(Screens.SettingsScreen.route)
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

// Screen components
@Composable
fun PlaylistItemDropDownMenu(
    expanded: MutableState<Boolean>
) {
    RoundedDropDownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false }
    ) {

    }
}

@Composable
fun AddPlaylistFAB(openDialog: MutableState<Boolean>) {
    LargeFloatingActionButton(
        onClick = { openDialog.value = true },
        modifier = Modifier.offset(y = (-80).dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_playlist_add_24),
            contentDescription = stringResource(id = R.string.str_newPlaylist),
            modifier = Modifier.size(FloatingActionButtonDefaults.LargeIconSize),
        )
    }
}

@Composable
fun AddPlaylistDialog(
    modifier: Modifier,
    openDialog: MutableState<Boolean>
) {
    val buttonEnabled = remember { mutableStateOf(true) }
    var iconText by remember { mutableStateOf("") }
    var playlistText by remember { mutableStateOf("") }

    AlertDialog(
        modifier = modifier,
        onDismissRequest = { openDialog.value = false },
        confirmButton = {
            when {
                playlistText.isEmpty() -> buttonEnabled.value = false
                else -> buttonEnabled.value = true
            }

            FilledTonalButton(
                onClick = { /*TODO*/ },
                enabled = buttonEnabled.value
            ) {
                Text(text = stringResource(id = R.string.str_create))
            }
        },
        dismissButton = {
            TextButton(onClick = { openDialog.value = false }) {
                Text(text = stringResource(id = R.string.str_cancel))
            }
        },
        title = {
            Text(
                text = stringResource(id = R.string.str_newPlaylist)
            )
        },
        text = {
            AddPlaylistDialogContent(
                iconText = iconText,
                onIconTextChange = { if(it.length <= 2) iconText = it },
                playlistText = playlistText,
                onPlaylistTextChange = { playlistText = it }
            )
        }
    )
}

@Composable
private fun AddPlaylistDialogContent(
    iconText: String,
    onIconTextChange: (String) -> Unit,
    playlistText: String,
    onPlaylistTextChange: (String) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(56.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            OutlinedTextField(
                value = iconText,
                onValueChange = onIconTextChange,
                shape = CircleShape,
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center
                ),
                placeholder = { Icon(painter = painterResource(id = R.drawable.ic_baseline_playlist_play_24), contentDescription = null) }
            )
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = playlistText,
            onValueChange = onPlaylistTextChange,
            label = { Text(text = stringResource(id = R.string.str_playlistTitle)) },
            singleLine = true
        )
    }
}