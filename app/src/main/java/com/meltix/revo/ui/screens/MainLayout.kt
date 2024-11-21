package com.meltix.revo.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.meltix.revo.R
import com.meltix.revo.data.classes.Position
import com.meltix.revo.data.classes.library.LibraryNavigationItem
import com.meltix.revo.ui.components.CollapsingLayout
import com.meltix.revo.ui.components.fabPositionResolver
import com.meltix.revo.ui.screens.search.SearchBar
import com.meltix.revo.util.functions.WindowType

@Composable
fun MainLayout(
    windowType: WindowType,
    fabPosition: Position,
    destinationsList: List<LibraryNavigationItem>,
    currentDestinationRoute: String,
    onNavigationItemSelected: (String) -> Unit,
    miniPlayer: @Composable (Modifier, Color) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    when(windowType) {
        WindowType.COMPACT_PORTRAIT, WindowType.COMPACT_WINDOW -> Compact(
            fabPosition = fabPositionResolver(fabPosition),
            destinationsList = destinationsList,
            currentDestinationRoute = currentDestinationRoute,
            onNavigationItemSelected = onNavigationItemSelected,
            miniPlayer = miniPlayer,
            content = content
        )
        
        WindowType.MEDIUM_PORTRAIT, WindowType.EXPANDED_PORTRAIT -> Medium(
            destinationsList = destinationsList,
            currentDestinationRoute = currentDestinationRoute,
            onNavigationItemSelected = onNavigationItemSelected,
            miniPlayer = miniPlayer,
            content = content
        )
        
        WindowType.COMPACT_LANDSCAPE -> ExpandedLandscape(
            fabPosition = fabPositionResolver(fabPosition),
            destinationsList = destinationsList,
            currentDestinationRoute = currentDestinationRoute,
            onNavigationItemSelected = onNavigationItemSelected,
            miniPlayer = miniPlayer,
            content = content
        )
    
        else -> { Text(text = "To be added") }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Compact(
    fabPosition: FabPosition,
    destinationsList: List<LibraryNavigationItem>,
    currentDestinationRoute: String,
    onNavigationItemSelected: (String) -> Unit,
    miniPlayer: @Composable (Modifier, Color) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    val expandedMenu = remember { mutableStateOf(false) }
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    destinationsList.forEach { item ->
                        val selected = item.route == currentDestinationRoute
                        
                        NavigationBarItem(
                            selected = selected,
                            onClick = { onNavigationItemSelected(item.route) },
                            icon = {
                                Icon(
                                    painter =
                                    if (selected) painterResource(id = item.selectedIcon)
                                    else painterResource(id = item.unselectedIcon),
                                    contentDescription = stringResource(id = item.name)
                                )
                            },
                            label = { Text(text = stringResource(id = item.name)) }
                        )
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    modifier = Modifier.padding(bottom = 73.dp),
                    onClick = { }
                ) {
                
                }
            },
            floatingActionButtonPosition = fabPosition
        ) { paddingValues ->
            CollapsingLayout(
                topContent = {
                    SearchBar(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(
                                top = systemBarsPadding.calculateTopPadding()
                            )
                            .clip(MaterialTheme.shapes.extraLarge)
                            .clickable { },
                        placeholderText = stringResource(id = R.string.search_your_library),
                        leadingUnit = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_search_24),
                                contentDescription = stringResource(id = R.string.search)
                            )
                        },
                        trailingUnit = {
                            BarDropDownMenu {
                            
                            }
                        }
                    )
                },
                bodyContent = {
                    content(
                        PaddingValues(
                            bottom =
                                paddingValues.calculateBottomPadding() +
                                72.dp + 32.dp + 56.dp
                        )
                    )
                }
            )
            
            BottomSheetScaffold(
                sheetContent = {
                    miniPlayer(
                        Modifier.padding(start = 16.dp, top = 12.dp, end = 16.dp),
                        Color.Transparent
                    )
                },
                sheetPeekHeight = 153.dp + systemBarsPadding.calculateBottomPadding(),
                sheetShape = RectangleShape,
                sheetTonalElevation = 3.dp,
                sheetDragHandle = { },
            ) {
            
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun Medium(
    destinationsList: List<LibraryNavigationItem>,
    currentDestinationRoute: String,
    onNavigationItemSelected: (String) -> Unit,
    miniPlayer: @Composable (Modifier, Color) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    val systemCutoutPadding = WindowInsets.displayCutout.asPaddingValues()
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.inverseOnSurface
    ) {
        PermanentNavigationDrawer(
            modifier = Modifier.padding(
                start = systemCutoutPadding.calculateStartPadding(LayoutDirection.Ltr),
                end = systemCutoutPadding.calculateEndPadding(LayoutDirection.Ltr) + 16.dp
            ),
            drawerContent = {
                PermanentDrawerSheet(
                    modifier = Modifier.width(260.dp),
                    drawerContainerColor = MaterialTheme.colorScheme.inverseOnSurface
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        ExtendedFloatingActionButton(
                            modifier = Modifier.fillMaxWidth(),
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                            onClick = { /*TODO*/ }
                        ) {
                        
                        }
                        
                        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
                            destinationsList.forEach { item ->
                                val selected = item.route == currentDestinationRoute
                                
                                NavigationDrawerItem(
                                    label = { Text(text = stringResource(id = item.name)) },
                                    selected = selected,
                                    icon = {
                                        Icon(
                                            painter =
                                            if (selected) painterResource(id = item.selectedIcon)
                                            else painterResource(id = item.unselectedIcon),
                                            contentDescription = stringResource(id = item.name)
                                        )
                                    },
                                    onClick = { onNavigationItemSelected(item.route) },
                                    colors = NavigationDrawerItemDefaults.colors(
                                        unselectedContainerColor = MaterialTheme.colorScheme.inverseOnSurface,
                                    )
                                )
                            }
                        }
                        
                        miniPlayer(Modifier, Color.Transparent)
                    }
                }
            }
        ) {
            Scaffold(
                topBar = {
                    Row(
                        modifier = Modifier
                            .padding(top = systemBarsPadding.calculateTopPadding() + 12.dp)
                            .height(64.dp)
                    ) {
                        SearchBar(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.extraLarge)
                                .clickable { },
                            placeholderText = stringResource(id = R.string.search_your_library),
                            leadingUnit = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_search_24),
                                    contentDescription = stringResource(id = R.string.search)
                                )
                            },
                            trailingUnit = {
                                BarDropDownMenu {
                                
                                }
                            }
                        )
                    }
                },
                containerColor = MaterialTheme.colorScheme.inverseOnSurface
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(top = paddingValues.calculateTopPadding())
                        .clip(MaterialTheme.shapes.large)
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    content(
                        PaddingValues(
                            bottom = paddingValues.calculateBottomPadding()
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun Expanded(
    destinationsList: List<LibraryNavigationItem>,
    currentDestinationRoute: String,
    onNavigationItemSelected: (String) -> Unit,
    miniPlayer: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExpandedLandscape(
    fabPosition: FabPosition,
    destinationsList: List<LibraryNavigationItem>,
    currentDestinationRoute: String,
    onNavigationItemSelected: (String) -> Unit,
    miniPlayer: @Composable (Modifier, Color) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    val systemCutoutPadding = WindowInsets.displayCutout.asPaddingValues()
    
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        PermanentNavigationDrawer(
            modifier = Modifier.padding(
                start = systemCutoutPadding.calculateStartPadding(LayoutDirection.Ltr),
                end = systemCutoutPadding.calculateEndPadding(LayoutDirection.Ltr)
            ),
            drawerContent = {
                PermanentDrawerSheet(
                    modifier = Modifier.width(260.dp),
                    drawerContainerColor = MaterialTheme.colorScheme.inverseOnSurface
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Column(modifier = Modifier.weight(1f)) {
                            destinationsList.forEach { item ->
                                val selected = item.route == currentDestinationRoute
                                
                                NavigationDrawerItem(
                                    label = { Text(text = stringResource(id = item.name)) },
                                    selected = selected,
                                    modifier = Modifier.height(45.dp),
                                    icon = {
                                        Icon(
                                            painter =
                                            if (selected) painterResource(id = item.selectedIcon)
                                            else painterResource(id = item.unselectedIcon),
                                            contentDescription = stringResource(id = item.name)
                                        )
                                    },
                                    onClick = { onNavigationItemSelected(item.route) },
                                    colors = NavigationDrawerItemDefaults.colors(
                                        unselectedContainerColor = MaterialTheme.colorScheme.inverseOnSurface,
                                    )
                                )
                            }
                        }
                        miniPlayer(Modifier, Color.Transparent)
                    }
                }
            }
        ) {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    TopAppBar(
                        title = {
                            SearchBar(
                                modifier = Modifier
                                    .padding(end = 16.dp)
                                    .clip(MaterialTheme.shapes.extraLarge)
                                    .clickable { },
                                placeholderText = stringResource(id = R.string.search_your_library),
                                leadingUnit = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_baseline_search_24),
                                        contentDescription = stringResource(id = R.string.search)
                                    )
                                },
                                trailingUnit = {
                                    BarDropDownMenu {
                                    
                                    }
                                }
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(),
                        scrollBehavior = scrollBehavior
                    )
                },
                floatingActionButton = {
                    // TODO
                    FloatingActionButton(onClick = { /*TODO*/ }) {
                    
                    }
                },
                floatingActionButtonPosition = fabPosition
            ) { paddingValues ->
                content(
                    PaddingValues(
                        top = paddingValues.calculateTopPadding(),
                        bottom = paddingValues.calculateBottomPadding() + 72.dp + 16.dp
                    )
                )
            }
        }
    }
}