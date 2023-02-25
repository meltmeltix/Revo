package com.alessiocameroni.revomusicplayer.ui.screens.library.playlistScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.alessiocameroni.pixely_components.RoundedDropDownMenu
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.ui.navigation.Screens
import com.alessiocameroni.revomusicplayer.ui.screens.library.TopBarDropDownMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistTopActionbar(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior
) {
    val expandedMenu = remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = stringResource(id = R.string.str_playlists)) },
        navigationIcon = {
            IconButton(
                onClick = { navController.navigate(Screens.SearchScreen.route) }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_search_24),
                    contentDescription = stringResource(id = R.string.desc_searchMenu)
                )
            }
        },
        actions = {
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
fun PlaylistItemDropDownMenu(
    expanded: MutableState<Boolean>,
    navController: NavController
) {
    RoundedDropDownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false }
    ) {

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlaylistDialog(
    modifier: Modifier,
    openDialog: MutableState<Boolean>
) {
    val buttonEnabled = remember { mutableStateOf(true) }
    var text by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        modifier = modifier,
        onDismissRequest = { openDialog.value = false },
        confirmButton = {
            when {
                text.isEmpty() -> buttonEnabled.value = false
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
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = { text = it },
                label = { Text(text = stringResource(id = R.string.str_playlistTitle)) }
            )
        }
    )
}