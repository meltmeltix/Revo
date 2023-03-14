package com.alessiocameroni.revomusicplayer.ui.screens.library.albumScreen.albumViewScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.alessiocameroni.pixely_components.PixelyDropdownMenuTitle
import com.alessiocameroni.pixely_components.RoundedDropDownMenu
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.data.classes.AlbumDetails
import com.alessiocameroni.revomusicplayer.ui.navigation.NavigationScreens
import com.alessiocameroni.revomusicplayer.ui.navigation.Screens

// Scaffold components
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumViewTopActionBar(
    navController: NavController,
    navControllerBottomBar: NavHostController,
    viewModel: AlbumViewViewModel,
    scrollBehavior: TopAppBarScrollBehavior,
    textVisibility: State<Boolean>,
    albumDetails: AlbumDetails,
) {
    val expandedSortMenu = remember { mutableStateOf(false) }
    val expandedMenu = remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            AnimatedVisibility(
                visible = textVisibility.value,
                enter = fadeIn(animationSpec = tween(100)),
                exit = fadeOut(animationSpec = tween(100))
            ) {
                Text(
                    text = albumDetails.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = { navControllerBottomBar.navigateUp() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                    contentDescription = stringResource(id = R.string.str_back)
                )
            }
        },
        actions = {
            Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                IconButton(onClick = { expandedSortMenu.value = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_sort_24),
                        contentDescription = stringResource(id = R.string.str_sortBy)
                    )
                }

                SortDropDownMenu(
                    expandedSortMenu,
                    viewModel
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
                    expanded = expandedMenu,
                    navController = navController,
                    navControllerBottomBar = navControllerBottomBar,
                    artistId = albumDetails.artistId
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun TopBarDropDownMenu(
    expanded: MutableState<Boolean>,
    navController: NavController,
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
                    NavigationScreens.ArtistViewScreen.route +
                        "/$artistId"
                )
                expanded.value = false
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_outlined_go_to_artist_24), 
                    contentDescription = stringResource(id = R.string.str_goToArtist)
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
    viewModel: AlbumViewViewModel
) {
    val sortTypeList = listOf(
        stringResource(id = R.string.str_trackNumber),
        stringResource(id = R.string.str_name),
        stringResource(id = R.string.str_duration)
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
    viewModel: AlbumViewViewModel,
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
    viewModel: AlbumViewViewModel,
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

// Header components
@Composable
fun AlbumViewHeader(
    albumDetails: AlbumDetails,
    navControllerBottomBar: NavHostController,
    viewModel: AlbumViewViewModel,
    leadingUnit: @Composable () -> Unit?,
) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .clip(MaterialTheme.shapes.extraLarge)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .aspectRatio(1f),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(120.dp),
                painter = painterResource(id = R.drawable.ic_outlined_album_24),
                contentDescription = stringResource(id = R.string.str_albumImage),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )

            leadingUnit()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.35f))
            )

            HeaderText(
                albumDetails = albumDetails,
                navControllerBottomBar = navControllerBottomBar,
                viewModel = viewModel,
            )
        }

        HeaderButtons()
    }
}

@Composable
private fun HeaderText(
    albumDetails: AlbumDetails,
    navControllerBottomBar: NavHostController,
    viewModel: AlbumViewViewModel,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val songs by viewModel.songs.observeAsState(emptyList())
    val hoursAmount: Int = viewModel.albumHoursAmount.value
    val minutesAmount: Int = viewModel.albumMinutesAmount.value
    val secondsAmount: Int = viewModel.albumSecondsAmount.value

    val albumInfo =
        "${songs.size} " +
        pluralStringResource(id = R.plurals.str_songAmount, count = songs.size) +
        " · " +
        when {
            hoursAmount > 0 -> {
                "$hoursAmount " +
                        pluralStringResource(id = R.plurals.str_hourAmountAbbr, count = hoursAmount) +
                        " $minutesAmount " +
                        pluralStringResource(id = R.plurals.str_minutesAmountAbbr, count = minutesAmount)
            }
            else -> {
                "$minutesAmount " +
                        pluralStringResource(id = R.plurals.str_minutesAmountAbbr, count = minutesAmount) +
                        " $secondsAmount " +
                        stringResource(id = R.string.str_secondsAmountAbbr)
            }
        }

    Row(
        modifier = Modifier
            .padding(25.dp)
            .fillMaxSize(),
        verticalAlignment = Alignment.Bottom
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = albumDetails.title,
                modifier = Modifier,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = albumDetails.artistName,
                modifier = Modifier
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        navControllerBottomBar.navigate(
                            NavigationScreens.ArtistViewScreen.route +
                                "/${albumDetails.artistId}"
                        )
                    },
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = albumInfo,
                modifier = Modifier,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun HeaderButtons() {
    Row(
        modifier = Modifier
            .padding(horizontal = 15.dp),
        horizontalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        FilledTonalButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .weight(0.5f)
                .height(45.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_play_arrow_24),
                contentDescription = stringResource(id = R.string.str_play),
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(18.dp)
            )

            Text(text = stringResource(id = R.string.str_play))
        }

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .weight(0.5f)
                .height(45.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_shuffle_24),
                contentDescription = stringResource(id = R.string.str_shuffle),
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(18.dp)
            )

            Text(text = stringResource(id = R.string.str_shuffle))
        }
    }
}


// List components
@Composable
fun AlbumViewItemDropDownMenu(
    expanded: MutableState<Boolean>
) {
    RoundedDropDownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false }
    ) {

    }
}