package com.alessiocameroni.revomusicplayer.appScreens

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.SearchActivity
import com.alessiocameroni.revomusicplayer.SettingsActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpotifyFavoritesScreen() {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text(text = stringResource(id = R.string.str_spoitfy)) },
                navigationIcon = {
                    IconButton(onClick = {
                        context.startActivity(Intent(context, SearchActivity::class.java))
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_search_24),
                            contentDescription = stringResource(id = R.string.desc_searchmenu)
                        )
                    }
                },
                actions = {
                    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                                contentDescription = stringResource(id = R.string.str_settings)
                            )
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text(text = stringResource(id = R.string.str_sortby)) },
                                onClick = {  },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_baseline_sort_24),
                                        contentDescription = stringResource(id = R.string.desc_sortyby)
                                    )
                                }
                            )
                            DropdownMenuItem(
                                text = { Text(text = stringResource(id = R.string.str_gridtype)) },
                                onClick = {  },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_baseline_grid_on_24),
                                        contentDescription = stringResource(id = R.string.desc_gridtype)
                                    )
                                }
                            )
                            Divider()
                            DropdownMenuItem(
                                text = { Text(text = stringResource(id = R.string.str_openspotify)) },
                                onClick = {  },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_launch_spotify_24px),
                                        contentDescription = stringResource(id = R.string.desc_openspotify)
                                    )
                                }
                            )
                            Divider()
                            DropdownMenuItem(
                                text = { Text(text = stringResource(id = R.string.str_settings)) },
                                onClick = {
                                    context.startActivity(Intent(context, SettingsActivity::class.java))
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
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)){

            }
        }
    )
}