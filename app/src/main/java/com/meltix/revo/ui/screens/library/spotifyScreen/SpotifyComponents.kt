package com.meltix.revo.ui.screens.library.spotifyScreen

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.meltix.pixely_components.RoundedDropDownMenu
import com.meltix.revo.R
import com.meltix.revo.ui.navigation.RootScreens

@Composable
fun SpotifyLayout(
    windowClass: WindowSizeClass,
    //viewModel: SongViewModel,
    onOpenSpotifyClick: () -> Unit,
    onRefresh: () -> Unit,
    onNavigate: (String) -> Unit,
    //contentState: ContentState,
    //loadingContent: @Composable () -> Unit,
    //noContent: @Composable () -> Unit,
    content: LazyListScope.() -> Unit
) {
    when(windowClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> CompactLayout(
            //viewModel = viewModel,
            onOpenSpotifyClick = { onOpenSpotifyClick() },
            onRefresh = { onRefresh() },
            onNavigate = { onNavigate(it) },
            //contentState = contentState,
            //loadingContent = loadingContent,
            //noContent = noContent,
            content = content
        )
        else -> ExpandedLayout(
            //viewModel = viewModel,
            onOpenSpotifyClick = { onOpenSpotifyClick() },
            onRefresh = { onRefresh() },
            onNavigate = { onNavigate(it) },
            //contentState = contentState,
            //loadingContent = loadingContent,
            //noContent = noContent,
            content = content
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CompactLayout(
    //viewModel: SongViewModel,
    onOpenSpotifyClick: () -> Unit,
    onRefresh: () -> Unit,
    onNavigate: (String) -> Unit,
    //contentState: ContentState,
    //loadingContent: @Composable () -> Unit,
    //noContent: @Composable () -> Unit,
    content: LazyListScope.() -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    val expandedMenu = remember { mutableStateOf(false) }
    //val expandedSortMenu = remember { mutableStateOf(false) }
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.str_spotify)) },
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
                                //expandedSortMenu = expandedSortMenu,
                                onOpenSpotifyClick = { onOpenSpotifyClick() },
                                onRefresh = { onRefresh() },
                                onNavigate = { onNavigate(it) },
                            )
                            
                            //SortDropDownMenu(expandedSortMenu, viewModel)
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            }
        ) { padding ->
            /*when(contentState) {
                ContentState.LOADING -> loadingContent()
                ContentState.FAILURE -> noContent()
                ContentState.SUCCESS -> {
                
                }
            }*/
    
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExpandedLayout(
    //viewModel: SongViewModel,
    onOpenSpotifyClick: () -> Unit,
    onRefresh: () -> Unit,
    onNavigate: (String) -> Unit,
    //contentState: ContentState,
    //loadingContent: @Composable () -> Unit,
    //noContent: @Composable () -> Unit,
    content: LazyListScope.() -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    val expandedMenu = remember { mutableStateOf(false) }
    //val expandedSortMenu = remember { mutableStateOf(false) }
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.inverseOnSurface
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.str_spotify)) },
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
                                //expandedSortMenu = expandedSortMenu,
                                onOpenSpotifyClick = { onOpenSpotifyClick() },
                                onRefresh = { onRefresh() },
                                onNavigate = { onNavigate(it) },
                            )
                            
                            //SortDropDownMenu(expandedSortMenu, viewModel)
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
            /*when (contentState) {
                ContentState.LOADING -> loadingContent()
                ContentState.FAILURE -> noContent()
                ContentState.SUCCESS -> {
                
                }
            }*/
    
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

@Composable
private fun TopBarDropDownMenu(
    expanded: MutableState<Boolean>,
    onOpenSpotifyClick: () -> Unit,
    onRefresh: () -> Unit,
    onNavigate: (String) -> Unit
) {
    RoundedDropDownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false }
    ) {
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.str_openSpotify)) },
            onClick = { onOpenSpotifyClick() },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launch_spotify_24px),
                    contentDescription = stringResource(id = R.string.str_openSpotify)
                )
            }
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
            text = {
                Text(text = stringResource(id = R.string.str_settings))
            },
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