package com.alessiocameroni.revomusicplayer.ui.screens.library.albumScreen

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
import androidx.navigation.NavHostController
import com.alessiocameroni.pixely_components.PixelyDropdownMenuTitle
import com.alessiocameroni.pixely_components.RoundedDropDownMenu
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.ui.navigation.NavigationScreens
import com.alessiocameroni.revomusicplayer.ui.navigation.Screens

// Scaffold components
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumTopActionBar(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior,
    viewModel: AlbumViewModel,
    isListEmpty: Boolean
) {
    val expandedMenu = remember { mutableStateOf(false) }
    val expandedSortMenu = remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = stringResource(id = R.string.str_albums)) },
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
                    expandedMenu,
                    expandedSortMenu,
                    navController,
                    isListEmpty
                )
                SortDropDownMenu(
                    expandedSortMenu,
                    viewModel
                )
            }
        }, scrollBehavior = scrollBehavior
    )
}

@Composable
private fun TopBarDropDownMenu(
    expanded: MutableState<Boolean>,
    expandedSortMenu: MutableState<Boolean>,
    navController: NavController,
    isListEmpty: Boolean
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
                    contentDescription = stringResource(id = R.string.str_sortBy)
                )
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_right_24),
                    contentDescription = stringResource(id = R.string.str_moreOptions)
                )
            },
            enabled = !isListEmpty
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
                    contentDescription = stringResource(id = R.string.str_settings)
                )
            }
        )
    }
}

@Composable
private fun SortDropDownMenu(
    expanded: MutableState<Boolean>,
    viewModel: AlbumViewModel
) {
    val sortTypeList = listOf(
        stringResource(id = R.string.str_name),
        stringResource(id = R.string.str_artist),
        stringResource(id = R.string.str_year),
        stringResource(id = R.string.str_songNumber)
    )
    val sortOrderList = listOf(
        stringResource(id = R.string.str_ascending),
        stringResource(id = R.string.str_descending)
    )
    var selectedSortType by remember { viewModel.sortingType }
    var selectedSortOrder by remember { viewModel.sortingOrder }

    RoundedDropDownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false }
    ) {
        PixelyDropdownMenuTitle(
            modifier = Modifier.padding(top = 5.dp),
            stringTitle = stringResource(id = R.string.str_sortType)
        )
        SortTypeSelector(
            expanded = expanded,
            options = sortTypeList,
            selected = sortTypeList[selectedSortType],
            onSelected = { selectedSortType = sortTypeList.indexOf(it) },
            viewModel = viewModel,
            orderSelection = selectedSortOrder
        )

        Divider()

        PixelyDropdownMenuTitle(
            modifier = Modifier.padding(top = 5.dp),
            stringTitle = stringResource(id = R.string.str_sortOrder)
        )
        SortOrderSelector(
            expanded = expanded,
            options = sortOrderList,
            selected = sortOrderList[selectedSortOrder],
            onSelected = { selectedSortOrder = sortOrderList.indexOf(it) },
            viewModel = viewModel,
            typeSelection = selectedSortType
        )
    }
}

@Composable
private fun SortTypeSelector(
    expanded: MutableState<Boolean>,
    options: List<String>,
    selected: String,
    onSelected: (String) -> Unit,
    viewModel: AlbumViewModel,
    orderSelection: Int
) {
    options.forEach { text ->
        DropdownMenuItem(
            text = { Text(text = text) },
            onClick = {
                onSelected(text)
                viewModel.setSortData(options.indexOf(text), orderSelection)
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

@Composable
private fun SortOrderSelector(
    expanded: MutableState<Boolean>,
    options: List<String>,
    selected: String,
    onSelected: (String) -> Unit,
    viewModel: AlbumViewModel,
    typeSelection: Int
) {
    options.forEach { text ->
        DropdownMenuItem(
            text = { Text(text = text) },
            onClick = {
                onSelected(text)
                viewModel.setSortData(typeSelection, options.indexOf(text))
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

// Screen components
@Composable
fun AlbumItemDropDownMenu(
    expanded: MutableState<Boolean>,
    navControllerBottomBar: NavHostController,
    artistId: Long
) {
    RoundedDropDownMenu(
        expanded = expanded.value, 
        onDismissRequest = { expanded.value = false }
    ) {
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.str_goToArtist)) }, 
            onClick = { 
                navControllerBottomBar.navigate(
                    NavigationScreens.ArtistViewScreen.route + "/$artistId"
                )
                expanded.value = false
            }
        )
    }
}