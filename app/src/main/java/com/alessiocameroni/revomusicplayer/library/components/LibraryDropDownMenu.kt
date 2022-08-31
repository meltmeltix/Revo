package com.alessiocameroni.revomusicplayer.library.components

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.navigation.Screens

@Composable
fun LibraryDropDownMenu(
    navController: NavController,
    expanded: MutableState<Boolean>,
    itemSortBy: Boolean,
    itemGridType: Boolean,
    itemOpenSpotify: Boolean,
    itemSettings: Boolean
) {
    MaterialTheme(shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(16.dp))) {
        DropdownMenu(
            modifier = Modifier.width(180.dp),
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            var expandedGrid by remember { mutableStateOf(false) }

            if(itemSortBy) {
                DropdownMenuItem(
                    text = { Text(text = stringResource(id = R.string.str_sortby)) },
                    onClick = { },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_sort_24),
                            contentDescription = stringResource(id = R.string.desc_sortyby)
                        )
                    }
                )
            }

            if(itemGridType) {
                DropdownMenuItem(
                    text = { Text(text = stringResource(id = R.string.str_gridtype)) },
                    onClick = { expandedGrid = true },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_grid_on_24),
                            contentDescription = stringResource(id = R.string.desc_gridtype)
                        )
                    }
                )

                MaterialTheme(shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(16.dp))) {
                    DropdownMenu(
                        modifier = Modifier.width(180.dp),
                        expanded = expandedGrid,
                        onDismissRequest = { expandedGrid = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = stringResource(id = R.string.str_settings)) },
                            onClick = {

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

            if(itemOpenSpotify) {
                Divider()

                DropdownMenuItem(
                    text = { Text(text = stringResource(id = R.string.str_openspotify)) },
                    onClick = { },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_launch_spotify_24px),
                            contentDescription = stringResource(id = R.string.desc_openspotify)
                        )
                    }
                )
            }

            if(itemSettings) {
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