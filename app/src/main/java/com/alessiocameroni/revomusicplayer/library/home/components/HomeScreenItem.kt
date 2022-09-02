package com.alessiocameroni.revomusicplayer.library.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alessiocameroni.revomusicplayer.R

@Preview(showBackground = true)
@Composable
fun PreviewComposable() {

}

@Composable
fun WelcomeHeader(
    modifier: Modifier
) {
    Box( modifier = modifier ) {
        Text(
            modifier = Modifier
                .padding(25.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.str_welcome),
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Composable
fun HomeActionsButtons(
    modifier: Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.height(300.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        userScrollEnabled = false
    ) {
        item {
            FilledTonalButton(
                modifier = Modifier
                    .height(50.dp),
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    modifier = Modifier.padding(end = 8.dp),
                    painter = painterResource(id = R.drawable.ic_baseline_history_24),
                    contentDescription = stringResource(id = R.string.str_history)
                )

                Text(text = stringResource(id = R.string.str_history))
            }
        }

        item {
            FilledTonalButton(
                modifier = Modifier
                    .height(50.dp),
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    modifier = Modifier.padding(end = 8.dp),
                    painter = painterResource(id = R.drawable.ic_baseline_library_add_24),
                    contentDescription = stringResource(id = R.string.str_lastadded)
                )

                Text(text = stringResource(id = R.string.str_lastadded))
            }
        }

        item {
            FilledTonalButton(
                modifier = Modifier
                    .height(50.dp),
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    modifier = Modifier.padding(end = 8.dp),
                    painter = painterResource(id = R.drawable.ic_baseline_queue_music_24),
                    contentDescription = stringResource(id = R.string.str_queue)
                )

                Text(text = stringResource(id = R.string.str_queue))
            }
        }

        item {
            FilledTonalButton(
                modifier = Modifier
                    .height(50.dp),
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    modifier = Modifier.padding(end = 8.dp),
                    painter = painterResource(id = R.drawable.ic_baseline_shuffle_24),
                    contentDescription = stringResource(id = R.string.str_shuffle)
                )

                Text(text = stringResource(id = R.string.str_shuffle))
            }
        }
    }
}