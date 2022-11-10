package com.alessiocameroni.revomusicplayer.library.main.components

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.data.navigation.Screens

@Composable
fun LibraryDropDownMenu(
    navController: NavController,
    expandedMenu: MutableState<Boolean>,
    expandedNestedMenu: MutableState<Boolean>,
    itemSortBy: Boolean,
    itemGridType: Boolean,
    itemOpenSpotify: Boolean,
    itemSettings: Boolean
) {
    MaterialTheme(shapes = MaterialTheme.shapes.copy(extraSmall = MaterialTheme.shapes.large)) {
        DropdownMenu(
            modifier = Modifier.width(180.dp),
            expanded = expandedMenu.value,
            onDismissRequest = { expandedMenu.value = false }
        ) {
            val expandedGridNested by remember { mutableStateOf(expandedNestedMenu) }

            if(itemSortBy) {
                DropdownMenuItem(
                    text = { Text(text = stringResource(id = R.string.str_sortBy)) },
                    onClick = { },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_sort_24),
                            contentDescription = stringResource(id = R.string.desc_sortBy)
                        )
                    },
                )
            }

            if(itemGridType) {
                DropdownMenuItem(
                    text = { Text(text = stringResource(id = R.string.str_gridType)) },
                    onClick = {
                        expandedMenu.value = false
                        expandedGridNested.value = true
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_grid_on_24),
                            contentDescription = stringResource(id = R.string.desc_gridType)
                        )
                    },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_right_24),
                            contentDescription = stringResource(id = R.string.desc_openGridTypeMenu)
                        )
                    }
                )
            }

            if(itemOpenSpotify) {
                Divider()

                DropdownMenuItem(
                    text = { Text(text = stringResource(id = R.string.str_openSpotify)) },
                    onClick = { },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_launch_spotify_24px),
                            contentDescription = stringResource(id = R.string.desc_openSpotify)
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
                        expandedMenu.value = false
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

@Composable
fun NestedGridTypeMenu(
    expandedNestedMenu: MutableState<Boolean>
) {
    val radioOptions = listOf(
        "1",
        "2"
    )
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

    MaterialTheme(shapes = MaterialTheme.shapes.copy(extraSmall = MaterialTheme.shapes.large)) {
        DropdownMenu(
            modifier = Modifier.width(180.dp),
            expanded = expandedNestedMenu.value,
            onDismissRequest = { expandedNestedMenu.value = false }
        ) {
            DropdownMenuTitle(text = "Columns")

            radioOptions.forEach { text ->
                DropdownMenuItem(
                    modifier = Modifier
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = {  },
                            role = Role.RadioButton
                        ),
                    text = { Text(text = text) },
                    onClick = { onOptionSelected(text) },
                    trailingIcon = {
                        RadioButton(
                            selected = (text == selectedOption),
                            onClick = null
                        )
                    }
                )
            }
        }
    }
}