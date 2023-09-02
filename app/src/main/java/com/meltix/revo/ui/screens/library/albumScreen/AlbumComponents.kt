package com.meltix.revo.ui.screens.library.albumScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.meltix.pixely_components.PixelyDropdownMenuTitle
import com.meltix.pixely_components.RoundedDropDownMenu
import com.meltix.revo.R
import com.meltix.revo.data.classes.ContentState
import com.meltix.revo.data.classes.preferences.SortingOrder
import com.meltix.revo.data.classes.preferences.SortingType
import com.meltix.revo.ui.navigation.RootScreens
import com.meltix.revo.util.functions.selectSortingOrderString
import com.meltix.revo.util.functions.selectSortingTypeString

@Composable
fun AlbumLayout(
    windowClass: WindowSizeClass,
    viewModel: AlbumViewModel,
    onRefresh: () -> Unit,
    onNavigate: (String) -> Unit,
    contentState: ContentState,
    loadingContent: @Composable () -> Unit,
    noContent: @Composable () -> Unit,
    content: LazyListScope.() -> Unit
) {
    when(windowClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> CompactLayout(
            viewModel = viewModel,
            onRefresh = { onRefresh() },
            onNavigate = { onNavigate(it) },
            contentState = contentState,
            loadingContent = loadingContent,
            noContent = noContent,
            content = content
        )
        else -> ExpandedLayout(
            viewModel = viewModel,
            onRefresh = { onRefresh() },
            onNavigate = { onNavigate(it) },
            contentState = contentState,
            loadingContent = loadingContent,
            noContent = noContent,
            content = content
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CompactLayout(
    viewModel: AlbumViewModel,
    onRefresh: () -> Unit,
    onNavigate: (String) -> Unit,
    contentState: ContentState,
    loadingContent: @Composable () -> Unit,
    noContent: @Composable () -> Unit,
    content: LazyListScope.() -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    val expandedMenu = remember { mutableStateOf(false) }
    val expandedSortMenu = remember { mutableStateOf(false) }
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.str_albums)) },
                    actions = {
                        IconButton(onClick = { /*TODO add searchbar when it is actually possible to use it*/ }) {
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
                                expanded = expandedMenu,
                                expandedSortMenu = expandedSortMenu,
                                onRefresh = { onRefresh() },
                                onNavigate = { onNavigate(it) },
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
        ) { padding ->
            when(contentState) {
                ContentState.LOADING -> loadingContent()
                ContentState.FAILURE -> noContent()
                ContentState.SUCCESS -> {
                    LazyColumn(
                        modifier = Modifier
                            .padding(
                                start = padding.calculateStartPadding(LayoutDirection.Ltr),
                                top = padding.calculateTopPadding(),
                                end = padding.calculateEndPadding(LayoutDirection.Rtl),
                                bottom = 0.dp,
                            )
                            .fillMaxSize(),
                        contentPadding = PaddingValues(bottom = systemBarsPadding.calculateBottomPadding()),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) { content() }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExpandedLayout(
    viewModel: AlbumViewModel,
    onRefresh: () -> Unit,
    onNavigate: (String) -> Unit,
    contentState: ContentState,
    loadingContent: @Composable () -> Unit,
    noContent: @Composable () -> Unit,
    content: LazyListScope.() -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    val expandedMenu = remember { mutableStateOf(false) }
    val expandedSortMenu = remember { mutableStateOf(false) }
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.inverseOnSurface
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.str_albums)) },
                    actions = {
                        IconButton(onClick = { /*TODO add searchbar when it is actually possible to use it*/ }) {
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
                                expanded = expandedMenu,
                                expandedSortMenu = expandedSortMenu,
                                onRefresh = { onRefresh() },
                                onNavigate = { onNavigate(it) },
                            )
    
                            SortDropDownMenu(
                                expandedSortMenu,
                                viewModel
                            )
                        }
                    },
                    windowInsets = WindowInsets(top = 0.dp),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.inverseOnSurface,
                        scrolledContainerColor = MaterialTheme.colorScheme.inverseOnSurface
                    ),
                    scrollBehavior = scrollBehavior
                )
            },
            containerColor = MaterialTheme.colorScheme.inverseOnSurface
        ) { padding ->
            when (contentState) {
                ContentState.LOADING -> loadingContent()
                ContentState.FAILURE -> noContent()
                ContentState.SUCCESS -> {
                    LazyColumn(
                        modifier = Modifier
                            .padding(
                                start = padding.calculateStartPadding(LayoutDirection.Ltr),
                                top = padding.calculateTopPadding(),
                                end = padding.calculateEndPadding(LayoutDirection.Rtl),
                                bottom = 0.dp,
                            )
                            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surface),
                        contentPadding = PaddingValues(bottom = systemBarsPadding.calculateBottomPadding()),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) { content() }
                }
            }
        }
    }
}

@Composable
private fun TopBarDropDownMenu(
    expanded: MutableState<Boolean>,
    expandedSortMenu: MutableState<Boolean>,
    onRefresh: () -> Unit,
    onNavigate: (String) -> Unit,
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
            text = { Text(text = stringResource(id = R.string.str_refresh)) },
            onClick = { onRefresh() },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_refresh_24),
                    contentDescription = stringResource(id = R.string.str_refresh)
                )
            }
        )
        
        HorizontalDivider()
        
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.str_settings)) },
            onClick = {
                onNavigate(RootScreens.SettingsGraph.route)
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