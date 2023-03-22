package com.alessiocameroni.revomusicplayer.ui.screens.library.artistScreen.artistViewScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.alessiocameroni.pixely_components.PixelyDropdownMenuTitle
import com.alessiocameroni.pixely_components.PixelySectionTitle
import com.alessiocameroni.pixely_components.RoundedDropDownMenu
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.data.classes.artist.ArtistDetails
import com.alessiocameroni.revomusicplayer.ui.components.LargeImageContainer
import com.alessiocameroni.revomusicplayer.ui.components.SmallImageContainer
import com.alessiocameroni.revomusicplayer.ui.navigation.NavigationScreens
import com.alessiocameroni.revomusicplayer.ui.navigation.Screens

// Scaffold components
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistViewTopActionBar(
    artistDetails: ArtistDetails,
    navController: NavController,
    navControllerBottomBar: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior,
    textVisibility: State<Boolean>,
) {
    val expandedMenu = remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            AnimatedVisibility(
                visible = textVisibility.value,
                enter = fadeIn(animationSpec = tween(100)),
                exit = fadeOut(animationSpec = tween(100))
            ) {
                ArtistInfoText(
                    largeText = false,
                    artistDetails = artistDetails
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
                IconButton(onClick = { expandedMenu.value = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                        contentDescription = stringResource(id = R.string.str_settings)
                    )
                }

                ArtistViewDropDownMenu(
                    expanded = expandedMenu,
                    navController = navController,
                )
            }
        },
        scrollBehavior = scrollBehavior,
    )
}

@Composable
private fun ArtistViewDropDownMenu(
    expanded: MutableState<Boolean>,
    navController: NavController,
) {
    RoundedDropDownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false }
    ) {
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


// Header components
@Composable
fun ArtistViewHeader(
    artistDetails: ArtistDetails,
    leadingUnit: @Composable () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Row(
            modifier = Modifier
                .height(72.dp)
                .padding(
                    horizontal = 10.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SmallImageContainer(
                modifier = Modifier
                    .padding(start = 5.dp)
                    .clip(CircleShape),
                painterPlaceholder = painterResource(id = R.drawable.ic_outlined_artist_24)
            ) {
                leadingUnit()
            }

            ArtistInfoText(
                largeText = true,
                artistDetails = artistDetails,
            )
        }

        HeaderButtons()
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
            onClick = {  },
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
            onClick = {  },
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

// Text components
@Composable
private fun ArtistInfoText(
    largeText: Boolean,
    artistDetails: ArtistDetails
) {
    val artistInfo =
        "${artistDetails.numberOfAlbums} " +
                pluralStringResource(
                    id = R.plurals.str_albumAmount,
                    count = artistDetails.numberOfAlbums
                ) +
                " · " +
                "${artistDetails.numberOfTracks} " +
                pluralStringResource(
                    id = R.plurals.str_songAmount,
                    count = artistDetails.numberOfTracks
                )

    if(largeText) {
        Column(
            modifier = Modifier.padding(start = 15.dp)
        ) {
            Text(
                text = artistDetails.artist,
                modifier = Modifier,
                style = MaterialTheme.typography.headlineMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = artistInfo,
                modifier = Modifier,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    } else {
        Column {
            Text(
                text = artistDetails.artist,
                modifier = Modifier,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = artistInfo,
                modifier = Modifier,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


// Screen components
@Composable
fun ArtistViewSongSectionTitle(viewModel: ArtistViewViewModel) {
    val sortTypeList = listOf(
        stringResource(id = R.string.str_name),
        stringResource(id = R.string.str_trackNumber),
        stringResource(id = R.string.str_duration),
        stringResource(id = R.string.str_album)
    )
    val sortOrderList = listOf(
        stringResource(id = R.string.str_ascending),
        stringResource(id = R.string.str_descending)
    )
    val expanded = remember { mutableStateOf(false) }
    var selectedSortType by remember { viewModel.sortingType }
    var selectedSortOrder by remember { viewModel.sortingOrder }

    PixelySectionTitle(
        stringTitle = stringResource(id = R.string.str_songs),
        horizontalContentPadding = 15.dp,
        trailingUnit = {
            Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                IconButton(onClick = { expanded.value = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_sort_24),
                        contentDescription = stringResource(id = R.string.str_sortBy)
                    )
                }

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
        }
    )
}

@Composable
private fun SortTypeSelector(
    expanded: MutableState<Boolean>,
    options: List<String>,
    selected: String,
    onSelected: (String) -> Unit,
    viewModel: ArtistViewViewModel,
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
    viewModel: ArtistViewViewModel,
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

@Composable
fun ArtistViewItemDropDownMenu(
    expanded: MutableState<Boolean>,
    navControllerBottomBar: NavController,
    albumId: Long
) {
    RoundedDropDownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false }
    ) {
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.str_goToAlbum)) },
            onClick = {
                navControllerBottomBar.navigate(
                    NavigationScreens.AlbumViewScreen.route +
                        "/$albumId"
                )
                expanded.value = false
            }
        )
    }
}

@Composable
fun ArtistViewHorizontalListItem(
    albumTitle: String,
    leadingUnit: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .widthIn(max = 130.dp)
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        LargeImageContainer(
            painterPlaceholder = painterResource(id = R.drawable.ic_outlined_album_24)
        ) { leadingUnit() }

        Text(
            text = albumTitle,
            modifier = Modifier,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.titleMedium
        )
    }
}