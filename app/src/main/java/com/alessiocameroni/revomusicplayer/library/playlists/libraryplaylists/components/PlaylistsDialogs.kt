package com.alessiocameroni.revomusicplayer.library.playlists.libraryplaylists.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.alessiocameroni.revomusicplayer.R

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddPlaylistDialog(
    modifier: Modifier,
    openDialog: MutableState<Boolean>
) {
    val buttonEnabled = remember { mutableStateOf(true) }

    Dialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        ),
        onDismissRequest = { openDialog.value = false }
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.str_newPlaylist)) },
                    navigationIcon = {
                        IconButton(
                            onClick = { openDialog.value = false }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_close_24),
                                contentDescription = stringResource(id = R.string.desc_closeNewPlaylistDialog)
                            )
                        }
                    },
                    actions = {
                        TextButton(
                            onClick = {  },
                            enabled = buttonEnabled.value
                        ) {
                            Text(text = stringResource(id = R.string.str_create))
                        }
                    }
                )
            },
            content = { padding ->
                Surface(
                    modifier = Modifier
                        .padding(padding)
                ) {

                }
            }
        )
    }
}