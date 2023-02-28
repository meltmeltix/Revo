package com.alessiocameroni.revomusicplayer.ui.screens.library.artistScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alessiocameroni.pixely_components.PixelyDropdownMenuTitle
import com.alessiocameroni.pixely_components.RoundedDropDownMenu
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.ui.navigation.Screens

/**
 * Scaffold components
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistTopActionBar(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior,
    viewModel: ArtistViewModel
) {
    val expandedMenu = remember { mutableStateOf(false) }
    val expandedSortMenu = remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = stringResource(id = R.string.str_artists)) },
        navigationIcon = {
            IconButton(onClick = { navController.navigate(Screens.SearchScreen.route) }) {
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
                    expandedMenu,
                    expandedSortMenu,
                    navController
                )
                SortDropDownMenu(
                    expandedSortMenu,
                    viewModel
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun TopBarDropDownMenu(
    expanded: MutableState<Boolean>,
    expandedSortMenu: MutableState<Boolean>,
    navController: NavController
) {
    RoundedDropDownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false }
    ) {
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.str_sortBy)) },
            onClick = {
                expanded.value = false
                expandedSortMenu.value = true
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_sort_24),
                    contentDescription = stringResource(id = R.string.desc_sortBy)
                )
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_right_24),
                    contentDescription = stringResource(id = R.string.str_moreOptions)
                )
            }
        )

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

@Composable
private fun SortDropDownMenu(
    expanded: MutableState<Boolean>,
    viewModel: ArtistViewModel
) {
    val sortOrderList = listOf(
        stringResource(id = R.string.str_ascending),
        stringResource(id = R.string.str_descending)
    )
    var selectedSortOrder by remember { viewModel.sortingOrder }

    RoundedDropDownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false }
    ) {
        PixelyDropdownMenuTitle(
            modifier = Modifier.padding(top = 5.dp),
            stringTitle = stringResource(id = R.string.str_sortOrder)
        )
        SortOrderSelector(
            expanded = expanded,
            options = sortOrderList,
            selected = sortOrderList[selectedSortOrder],
            onSelected = { selectedSortOrder = sortOrderList.indexOf(it) },
            viewModel = viewModel
        )
    }
}

@Composable
private fun SortOrderSelector(
    expanded: MutableState<Boolean>,
    options: List<String>,
    selected: String,
    onSelected: (String) -> Unit,
    viewModel: ArtistViewModel,
) {
    options.forEach { text ->
        DropdownMenuItem(
            text = { Text(text = text) },
            onClick = {
                onSelected(text)
                viewModel.saveSortOrderSelection(
                    options.indexOf(text)
                )
                expanded.value = false
            },
            trailingIcon = {
                RadioButton(
                    selected = (text == selected),
                    onClick = null
                )
            }
        )
    }
}

