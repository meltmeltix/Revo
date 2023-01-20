package com.alessiocameroni.revomusicplayer.library.playlists.libraryplaylists.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alessiocameroni.revomusicplayer.R

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