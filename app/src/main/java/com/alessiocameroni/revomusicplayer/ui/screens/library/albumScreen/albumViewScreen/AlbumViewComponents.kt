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
import com.alessiocameroni.revomusicplayer.data.classes.album.AlbumDetails
import com.alessiocameroni.revomusicplayer.data.classes.album.AlbumDuration
import com.alessiocameroni.revomusicplayer.data.classes.preferences.SortingOrder
import com.alessiocameroni.revomusicplayer.data.classes.preferences.SortingType
import com.alessiocameroni.revomusicplayer.ui.navigation.NavigationScreens
import com.alessiocameroni.revomusicplayer.ui.navigation.Screens
import com.alessiocameroni.revomusicplayer.util.functions.selectSortingOrderString
import com.alessiocameroni.revomusicplayer.util.functions.selectSortingTypeString

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
    val selectedSortType by viewModel.sortingType.collectAsState(SortingType.TRACK)
    val selectedSortOrder by viewModel.sortingOrder.collectAsState(SortingOrder.ASCENDING)

    RoundedDropDownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false }
    ) {
        PixelyDropdownMenuTitle(
            stringTitle = stringResource(id = R.string.str_sortType)
        )

        SortTypeSelector(
            expanded = expanded,
            options = viewModel.sortTypeList,
            selected = selectedSortType,
            onSelected = { viewModel.setSortType(it) },
        )

        Divider()

        PixelyDropdownMenuTitle(
            stringTitle = stringResource(id = R.string.str_sortOrder)
        )

        SortOrderSelector(
            expanded = expanded,
            options = SortingOrder.values(),
            selected = selectedSortOrder,
            onSelected = { viewModel.setSortOrder(it) },
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
    val songs by viewModel.songs.collectAsState(emptyList())
    val albumDuration by viewModel.albumDuration.collectAsState(
        AlbumDuration( 0, 0, 0, 0)
    )

    val albumInfo =
        "${songs.size} " +
        pluralStringResource(id = R.plurals.str_songAmount, count = songs.size) +
        " · " +
        when {
            albumDuration.hours > 0 -> {
                "${albumDuration.hours} " +
                        pluralStringResource(id = R.plurals.str_hourAmountAbbr, count = albumDuration.hours) +
                        " ${albumDuration.minutes} " +
                        pluralStringResource(id = R.plurals.str_minutesAmountAbbr, count = albumDuration.minutes)
            }
            else -> {
                "${albumDuration.minutes} " +
                        pluralStringResource(id = R.plurals.str_minutesAmountAbbr, count = albumDuration.minutes) +
                        " ${albumDuration.seconds} " +
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