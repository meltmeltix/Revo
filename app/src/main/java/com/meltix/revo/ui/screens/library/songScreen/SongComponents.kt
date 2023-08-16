package com.meltix.revo.ui.screens.library.songScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.meltix.pixely_components.PixelyDropdownMenuTitle
import com.meltix.pixely_components.RoundedDropDownMenu
import com.meltix.revo.R
import com.meltix.revo.data.classes.preferences.SortingOrder
import com.meltix.revo.data.classes.preferences.SortingType
import com.meltix.revo.ui.components.topAppBarColorOnWindowSize
import com.meltix.revo.ui.components.topAppBarInsetsOnWindowsSize
import com.meltix.revo.ui.navigation.NavigationScreens
import com.meltix.revo.ui.navigation.Screens
import com.meltix.revo.util.functions.selectSortingOrderString
import com.meltix.revo.util.functions.selectSortingTypeString

// Scaffold components
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongTopActionBar(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior,
    viewModel: SongViewModel,
    windowClass: WindowSizeClass,
) {
    val expandedMenu = remember { mutableStateOf(false) }
    val expandedSortMenu = remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = stringResource(id = R.string.str_songs)) },
        actions = {
            IconButton(onClick = { navController.navigate(Screens.SearchScreen.route) }) {
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
                )

                SortDropDownMenu(
                    expandedSortMenu,
                    viewModel
                )
            }
        },
        windowInsets = topAppBarInsetsOnWindowsSize(windowClass),
        colors = topAppBarColorOnWindowSize(windowClass),
        scrollBehavior = scrollBehavior,
    )
}

@Composable
private fun TopBarDropDownMenu(
    expanded: MutableState<Boolean>,
    expandedSortMenu: MutableState<Boolean>,
    navController: NavController,
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
        )

        HorizontalDivider()
        
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
    viewModel: SongViewModel
) {
    val selectedSortType by viewModel.sortingType.collectAsStateWithLifecycle(SortingType.TITLE)
    val selectedSortOrder by viewModel.sortingOrder.collectAsStateWithLifecycle(SortingOrder.ASCENDING)

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
            options = viewModel.sortTypeList,
            selected = selectedSortType,
            onSelected = { viewModel.setSortType(it) }
        )

        HorizontalDivider()
        
        PixelyDropdownMenuTitle(
            modifier = Modifier.padding(top = 5.dp),
            stringTitle = stringResource(id = R.string.str_sortOrder)
        )
        SortOrderSelector(
            expanded = expanded,
            options = SortingOrder.values(),
            selected = selectedSortOrder,
            onSelected = { viewModel.setSortOrder(it) }
        )
    }
}

@Composable
private fun SortTypeSelector(
    expanded: MutableState<Boolean>,
    options: List<SortingType>,
    selected: SortingType,
    onSelected: (SortingType) -> Unit,
) {
    options.forEach { option ->
        DropdownMenuItem(
            text = { Text(selectSortingTypeString(option)) },
            onClick = {
                onSelected(option)
                expanded.value = false
            },
            trailingIcon = { RadioButton(selected = option == selected, onClick = null) }
        )
    }
}

@Composable
private fun SortOrderSelector(
    expanded: MutableState<Boolean>,
    options: Array<SortingOrder>,
    selected: SortingOrder,
    onSelected: (SortingOrder) -> Unit
) {
    options.forEach { option ->
        DropdownMenuItem(
            text = { Text(selectSortingOrderString(option)) },
            onClick = {
                onSelected(option)
                expanded.value = false
            },
            trailingIcon = { RadioButton(selected = option == selected, onClick = null) }
        )
    }
}

// Items components
@Composable
fun SongItemDropDownMenu(
    expanded: MutableState<Boolean>,
    navControllerBottomBar: NavController,
    albumId: Long,
    artistId: Long,
) {
    RoundedDropDownMenu(
        expanded = expanded.value, 
        onDismissRequest = { expanded.value = false }
    ) {
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.str_goToAlbum)) },
            onClick = {
                navControllerBottomBar.navigate(
                    NavigationScreens.AlbumViewScreen.route + "/$albumId"
                )
                expanded.value = false
            }
        )

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