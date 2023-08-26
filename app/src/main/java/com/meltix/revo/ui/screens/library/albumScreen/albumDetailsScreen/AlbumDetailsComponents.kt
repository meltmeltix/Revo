package com.meltix.revo.ui.screens.library.albumScreen.albumDetailsScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.meltix.pixely_components.PixelyDropdownMenuTitle
import com.meltix.pixely_components.RoundedDropDownMenu
import com.meltix.revo.R
import com.meltix.revo.data.classes.album.AlbumDetails
import com.meltix.revo.data.classes.album.HeaderLayout
import com.meltix.revo.data.classes.preferences.SortingOrder
import com.meltix.revo.data.classes.preferences.SortingType
import com.meltix.revo.ui.components.albumDetailsTopAppBarColor
import com.meltix.revo.ui.components.albumDetailsTopElementsColor
import com.meltix.revo.ui.components.topAppBarInsetsOnWindowsSize
import com.meltix.revo.ui.navigation.DetailsScreens
import com.meltix.revo.ui.navigation.RootScreens
import com.meltix.revo.ui.screens.library.albumScreen.albumDetailsScreen.components.FruitHeader
import com.meltix.revo.ui.screens.library.albumScreen.albumDetailsScreen.components.MinimalHeader
import com.meltix.revo.ui.screens.library.albumScreen.albumDetailsScreen.components.RevoHeader
import com.meltix.revo.util.functions.selectSortingOrderString
import com.meltix.revo.util.functions.selectSortingTypeString

@Composable
fun AlbumDetailsHeader(
    viewModel: AlbumDetailsViewModel,
    navController: NavController,
    windowClass: WindowSizeClass,
    layout: HeaderLayout,
    leadingUnit: @Composable (() -> Unit),
    albumDetails: AlbumDetails
) {
    when(layout) {
        HeaderLayout.REVO -> RevoHeader(
            viewModel = viewModel,
            navController = navController,
            windowClass = windowClass,
            leadingUnit = leadingUnit,
            albumDetails = albumDetails
        )
        HeaderLayout.FRUIT_MUSIC -> FruitHeader(
            viewModel = viewModel,
            navController = navController,
            windowClass = windowClass,
            leadingUnit = leadingUnit,
            albumDetails = albumDetails
        )
        HeaderLayout.MINIMAL -> MinimalHeader(
            viewModel = viewModel,
            navController = navController,
            windowClass = windowClass,
            leadingUnit = leadingUnit,
            albumDetails = albumDetails
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumDetailsTopActionBar(
    rootNavController: NavController,
    libraryNavController: NavController,
    firstVisibleItem: State<Boolean>,
    scrollBehavior: TopAppBarScrollBehavior,
    viewModel: AlbumDetailsViewModel,
    headerLayout: HeaderLayout,
    windowClass: WindowSizeClass,
    albumDetails: AlbumDetails,
) {
    val expandedSortMenu = remember { mutableStateOf(false) }
    val expandedMenu = remember { mutableStateOf(false) }
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()

    Box(modifier = Modifier.fillMaxWidth()) {
        if(windowClass.heightSizeClass != WindowHeightSizeClass.Compact && headerLayout == HeaderLayout.FRUIT_MUSIC) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(systemBarsPadding.calculateTopPadding())
                    .background(
                        albumDetailsTopElementsColor(
                            windowClass,
                            headerLayout,
                            firstVisibleItem
                        )
                    )
            )
        }

        TopAppBar(
            title = {
                AnimatedVisibility(
                    visible = firstVisibleItem.value,
                    enter = fadeIn(animationSpec = tween(100)),
                    exit = fadeOut(animationSpec = tween(100))
                ) { Text(text = albumDetails.title, maxLines = 1, overflow = TextOverflow.Ellipsis) }
            },
            navigationIcon = {
                IconButton(
                    onClick = { libraryNavController.navigateUp() },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = albumDetailsTopElementsColor(windowClass, headerLayout, firstVisibleItem)
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
                            containerColor = albumDetailsTopElementsColor(windowClass, headerLayout, firstVisibleItem)
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
                            containerColor = albumDetailsTopElementsColor(windowClass, headerLayout, firstVisibleItem)
                        )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                            contentDescription = stringResource(id = R.string.str_settings)
                        )
                    }

                    TopBarDropDownMenu(
                        expanded = expandedMenu,
                        rootNavController = rootNavController,
                        libraryNavController = libraryNavController,
                        artistId = albumDetails.artistId
                    )
                }
            },
            windowInsets = topAppBarInsetsOnWindowsSize(windowClass),
            colors = albumDetailsTopAppBarColor(windowClass, headerLayout, firstVisibleItem),
            scrollBehavior = scrollBehavior,
        )
    }
}

@Composable
private fun TopBarDropDownMenu(
    expanded: MutableState<Boolean>,
    rootNavController: NavController,
    libraryNavController: NavController,
    artistId: Long
) {
    RoundedDropDownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false }
    ) {
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.str_goToArtist)) },
            onClick = {
                libraryNavController.navigate(DetailsScreens.ArtistDetails.route + "/$artistId")
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
                rootNavController.navigate(RootScreens.SettingsGraph.route)
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
    viewModel: AlbumDetailsViewModel
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

@Composable
fun AlbumDetailsItemDropDownMenu(
    expanded: MutableState<Boolean>
) {
    RoundedDropDownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false }
    ) {
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.str_addToFavorites)) },
            onClick = { }
        )

        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.str_addToPlaylist)) },
            onClick = { }
        )
    }
}