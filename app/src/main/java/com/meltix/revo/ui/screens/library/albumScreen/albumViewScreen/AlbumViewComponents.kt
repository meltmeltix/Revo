package com.meltix.revo.ui.screens.library.albumScreen.albumViewScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.meltix.pixely_components.PixelyDropdownMenuTitle
import com.meltix.pixely_components.RoundedDropDownMenu
import com.meltix.revo.R
import com.meltix.revo.data.classes.album.AlbumDetails
import com.meltix.revo.data.classes.album.AlbumDuration
import com.meltix.revo.data.classes.album.HeaderLayout
import com.meltix.revo.data.classes.preferences.SortingOrder
import com.meltix.revo.data.classes.preferences.SortingType
import com.meltix.revo.ui.navigation.NavigationScreens
import com.meltix.revo.ui.navigation.Screens
import com.meltix.revo.ui.theme.*
import com.meltix.revo.util.functions.selectSortingOrderString
import com.meltix.revo.util.functions.selectSortingTypeString
import com.meltix.revo.ui.theme.Grey90

// Scaffold components
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumViewTopActionBar(
    navController: NavController,
    navControllerBottomBar: NavHostController,
    viewModel: AlbumViewViewModel,
    firstVisibleItem: State<Boolean>,
    albumDetails: AlbumDetails,
) {
    val expandedSortMenu = remember { mutableStateOf(false) }
    val expandedMenu = remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = firstVisibleItem, label = "")
    val buttonContainerColor by transition.animateColor(
        transitionSpec = { tween(1) },
        label = ""
    ) {
        when(it.value) {
            true -> MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
            false -> MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
        }
    }
    val topBarContainerFloat by transition.animateFloat(
        transitionSpec = { tween(1) },
        label = ""
    ) {
        when(it.value) {
            true -> 1f
            false -> 0f
        }
    }

    TopAppBar(
        title = {
            AnimatedVisibility(
                visible = firstVisibleItem.value,
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
                onClick = { navControllerBottomBar.navigateUp() },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = buttonContainerColor,
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                    contentDescription = stringResource(id = R.string.str_back)
                )
            }
        },
        actions = {
            Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                IconButton(
                    onClick = { expandedSortMenu.value = true },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = buttonContainerColor,
                    )
                ) {
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
                IconButton(
                    onClick = { expandedMenu.value = true },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = buttonContainerColor,
                    )
                ) {
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
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp).copy(topBarContainerFloat)
        )
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
    viewModel: AlbumViewViewModel
) {
    val selectedSortType by viewModel.sortingType.collectAsStateWithLifecycle(SortingType.TRACK)
    val selectedSortOrder by viewModel.sortingOrder.collectAsStateWithLifecycle(SortingOrder.ASCENDING)

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

        HorizontalDivider()

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

// Header components
@Composable
fun AlbumViewHeader(
    layout: HeaderLayout,
    albumDetails: AlbumDetails,
    leadingUnit: @Composable () -> Unit?,
    navController: NavHostController,
    viewModel: AlbumViewViewModel,
) {
    when(layout) {
        HeaderLayout.REVO -> RevoHeader(
            layout = layout,
            albumDetails = albumDetails,
            leadingUnit = leadingUnit,
            navController = navController,
            viewModel = viewModel
        )
        HeaderLayout.FRUIT_MUSIC -> FruitMusicHeader(
            layout = layout,
            albumDetails = albumDetails,
            leadingUnit = leadingUnit,
            navController = navController,
            viewModel = viewModel
        )
        HeaderLayout.MINIMAL -> MinimalMusicHeader(
            layout = layout,
            albumDetails = albumDetails,
            leadingUnit = leadingUnit,
            navController = navController,
            viewModel = viewModel
        )
    }
}

@Composable
private fun RevoHeader(
    layout: HeaderLayout,
    albumDetails: AlbumDetails,
    leadingUnit: @Composable () -> Unit?,
    navController: NavHostController,
    viewModel: AlbumViewViewModel,
) {
    val gradientStartY = with(LocalDensity.current) { 100.dp.toPx() }

    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .padding(top = 70.dp)
                .clip(MaterialTheme.shapes.extraLarge)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .aspectRatio(1f),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(120.dp),
                painter = painterResource(id = R.drawable.ic_outlined_album_24),
                contentDescription = stringResource(id = R.string.str_albumImage),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )

            leadingUnit()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f)
                            ),
                            startY = gradientStartY
                        )
                    )
            )

            Box(
                modifier = Modifier
                    .padding(25.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomStart
            ) {
                HeaderText(
                    albumDetails = albumDetails,
                    navController = navController,
                    viewModel = viewModel,
                )
            }
        }

        HeaderButtons(
            layout = layout,
            onPlayClick = {  },
            onShuffleClick = {  }
        )
    }
}

@Composable
private fun FruitMusicHeader(
    layout: HeaderLayout,
    albumDetails: AlbumDetails,
    leadingUnit: @Composable () -> Unit?,
    navController: NavHostController,
    viewModel: AlbumViewViewModel,
) {
    val gradientStartY = with(LocalDensity.current) { 140.dp.toPx() }

    Column(modifier = Modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .aspectRatio(0.82f),
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
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f)
                            ),
                            startY = gradientStartY
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .padding(25.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                HeaderText(
                    albumDetails = albumDetails,
                    navController = navController,
                    viewModel = viewModel
                )

                HeaderButtons(
                    layout = layout,
                    onPlayClick = {  },
                    onShuffleClick = {  }
                )
            }
        }
    }
}

@Composable
private fun MinimalMusicHeader(
    layout: HeaderLayout,
    albumDetails: AlbumDetails,
    leadingUnit: @Composable () -> Unit?,
    navController: NavHostController,
    viewModel: AlbumViewViewModel,
) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .padding(top = 70.dp)
                .clip(MaterialTheme.shapes.extraLarge)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .aspectRatio(3.6f),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = R.drawable.ic_outlined_album_24),
                contentDescription = stringResource(id = R.string.str_albumImage),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )

            leadingUnit()
        }

        HeaderText(
            modifier = Modifier.padding(
                start = 20.dp,
                bottom = 8.dp,
                end = 20.dp,
            ),
            albumDetails = albumDetails,
            navController = navController,
            viewModel = viewModel,
            disableWhiteColor = true
        )

        HeaderButtons(
            layout = layout,
            onPlayClick = {  },
            onShuffleClick = {  }
        )
    }
}

@Composable
private fun HeaderText(
    modifier: Modifier = Modifier,
    albumDetails: AlbumDetails,
    navController: NavHostController,
    viewModel: AlbumViewViewModel,
    disableWhiteColor: Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val textColor = when(disableWhiteColor) {
        true -> MaterialTheme.colorScheme.onSurface
        false -> Color.White
    }
    val supportingTextColor = when(disableWhiteColor) {
        true -> MaterialTheme.colorScheme.onSurfaceVariant
        false -> Grey90
    }
    val songs by viewModel.songs.collectAsStateWithLifecycle(emptyList())
    val albumDuration by viewModel.albumDuration.collectAsStateWithLifecycle(
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

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = albumDetails.title,
            modifier = Modifier,
            style = MaterialTheme.typography.headlineMedium,
            color = textColor,
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
                    navController.navigate(
                        NavigationScreens.ArtistViewScreen.route +
                                "/${albumDetails.artistId}"
                    )
                },
            color = supportingTextColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = albumInfo,
            modifier = Modifier,
            color = supportingTextColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun HeaderButtons(
    layout: HeaderLayout,
    onPlayClick: () -> Unit,
    onShuffleClick: () -> Unit
) {
    when(layout) {
        HeaderLayout.FRUIT_MUSIC -> {
            Row(
                modifier = Modifier.padding(top = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                ElevatedButton(
                    onClick = onPlayClick,
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

                ElevatedButton(
                    onClick = onShuffleClick,
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
        else -> {
            Row(
                modifier = Modifier.padding(horizontal = 15.dp),
                horizontalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                FilledTonalButton(
                    onClick = onPlayClick,
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
                    onClick = onShuffleClick,
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
    }
}