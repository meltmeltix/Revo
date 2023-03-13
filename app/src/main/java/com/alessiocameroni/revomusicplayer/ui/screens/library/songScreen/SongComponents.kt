package com.alessiocameroni.revomusicplayer.ui.screens.library.songScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alessiocameroni.pixely_components.PixelyDropdownMenuTitle
import com.alessiocameroni.pixely_components.RoundedDropDownMenu
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.ui.navigation.NavigationScreens
import com.alessiocameroni.revomusicplayer.ui.navigation.Screens

// Screen components
@Composable
fun ScreenContent(
    state: Boolean,
    isListEmpty: Boolean,
    loadingUnit: @Composable () -> Unit,
    noContentUnit: @Composable () -> Unit,
) {
    AnimatedVisibility(
        visible = state && !isListEmpty,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300))
    ) { loadingUnit() }

    if(isListEmpty) {
        AnimatedVisibility(
            visible = true,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300))
        ) { noContentUnit() }
    }
}

@Composable
fun LoadingContent(padding: PaddingValues) {
    Surface(color = MaterialTheme.colorScheme.surface) {
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(bottom = 70.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.str_loadingTunes),
                modifier = Modifier
                    .padding(horizontal = 25.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(20.dp))

            LinearProgressIndicator(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium),
            )
        }
    }

}

@Composable
fun NoSongsMessage(padding: PaddingValues) {
    Surface(color = MaterialTheme.colorScheme.surface) {
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 25.dp)
                .padding(bottom = 70.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_music_note_24),
                contentDescription = stringResource(id = R.string.str_songs),
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .size(50.dp)
            )
            Text(
                text = stringResource(id = R.string.str_tooQuiet),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = stringResource(id = R.string.info_tooQuiet),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
    }
}


// Scaffold components
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongTopActionBar(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior,
    viewModel: SongViewModel,
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
                    navController
                )
                SortDropDownMenu(
                    expandedSortMenu,
                    viewModel
                )
            }
        },
        scrollBehavior = scrollBehavior,
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
                    contentDescription = stringResource(id = R.string.str_sortBy)
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
    val sortTypeList = listOf(
        stringResource(id = R.string.str_name),
        stringResource(id = R.string.str_artist),
        stringResource(id = R.string.str_album),
        stringResource(id = R.string.str_duration),
        stringResource(id = R.string.str_dateAdded),
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
            orderSelection = selectedSortOrder,
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
            typeSelection = selectedSortType,
        )
    }
}

@Composable
private fun SortTypeSelector(
    expanded: MutableState<Boolean>,
    options: List<String>,
    selected: String,
    onSelected: (String) -> Unit,
    viewModel: SongViewModel,
    orderSelection: Int,
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
    viewModel: SongViewModel,
    typeSelection: Int,
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