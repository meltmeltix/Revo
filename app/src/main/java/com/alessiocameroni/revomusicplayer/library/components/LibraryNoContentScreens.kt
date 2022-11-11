package com.alessiocameroni.revomusicplayer.library.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.data.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoSongsScreen(
    navController: NavController,
    expanded: MutableState<Boolean>,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {  },
                actions = {
                    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                        IconButton(onClick = { expanded.value = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                                contentDescription = stringResource(id = R.string.str_settings)
                            )
                        }

                        DropdownMenu(
                            modifier = Modifier.width(180.dp),
                            expanded = expanded.value,
                            onDismissRequest = { expanded.value = false }
                        ) {
                            MaterialTheme(shapes = MaterialTheme.shapes.copy(extraSmall = MaterialTheme.shapes.large)) {
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
                },
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .size(50.dp)
                    .padding(bottom = 5.dp),
                painter = painterResource(id = R.drawable.baseline_music_off_24),
                contentDescription = stringResource(id = R.string.str_icon)
            )

            Text(
                text = stringResource(id = R.string.str_tooQuiet),
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = stringResource(id = R.string.desc_tooQuiet),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}