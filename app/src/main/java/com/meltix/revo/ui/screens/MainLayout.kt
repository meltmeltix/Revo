package com.meltix.revo.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.meltix.revo.R
import com.meltix.revo.data.classes.LibraryNavigationItem
import com.meltix.revo.data.classes.WindowType
import com.meltix.revo.ui.screens.search.SearchBar

@Composable
fun MainLayout(
    windowType: WindowType,
    destinationsList: List<LibraryNavigationItem>,
    currentDestinationRoute: String,
    onNavigationItemSelected: (String) -> Unit,
    content: @Composable () -> Unit
) {
    when(windowType) {
        WindowType.COMPACT_PORTRAIT, WindowType.COMPACT_WINDOW -> Compact(
            destinationsList = destinationsList,
            currentDestinationRoute = currentDestinationRoute,
            onNavigationItemSelected = { onNavigationItemSelected(it) },
            miniPlayer = { MiniPlayer(windowType) },
            content = { _ -> content() }
        )
        WindowType.MEDIUM_PORTRAIT, WindowType.EXPANDED_PORTRAIT -> Medium(
            destinationsList = destinationsList,
            currentDestinationRoute = currentDestinationRoute,
            onNavigationItemSelected = { onNavigationItemSelected(it) },
            miniPlayer = { MiniPlayer(windowType) },
            content = { _ -> content() }
        )
    
        else -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Compact(
    destinationsList: List<LibraryNavigationItem>,
    currentDestinationRoute: String,
    onNavigationItemSelected: (String) -> Unit,
    miniPlayer: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    val expandedMenu = remember { mutableStateOf(false) }
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        SearchBar(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.extraLarge)
                                .padding(end = 16.dp)
                                .clickable { },
                            placeholderText = stringResource(id = R.string.search_your_library),
                            leadingUnit = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_search_24),
                                    contentDescription = stringResource(id = R.string.search)
                                )
                            },
                            trailingUnit = {
                                Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                                    IconButton(onClick = { expandedMenu.value = true }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                                            contentDescription = stringResource(id = R.string.menu)
                                        )
                                    }
                
                                    BarDropDownMenu(
                                        expanded = expandedMenu,
                                        onNavigate = {  }
                                    )
                                }
                            }
                        )
                    }
                )
            },
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
            containerColor = Color.Transparent
        ) { paddingValues ->
            BottomSheetScaffold(
                sheetContent = {
                    miniPlayer()
                },
                sheetPeekHeight = 153.dp + systemBarsPadding.calculateBottomPadding(),
                sheetShape = RectangleShape,
                sheetTonalElevation = 3.dp,
                sheetDragHandle = { },
            ) { content(paddingValues) }
        }
    }
}

@Composable
private fun Medium(
    destinationsList: List<LibraryNavigationItem>,
    currentDestinationRoute: String,
    onNavigationItemSelected: (String) -> Unit,
    miniPlayer: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    val expandedMenu = remember { mutableStateOf(false) }
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.inverseOnSurface
    ) {
        PermanentNavigationDrawer(
            drawerContent = {
                PermanentDrawerSheet(
                    modifier = Modifier
                        .fillMaxWidth(0.40f)
                        .padding(12.dp),
                    drawerContainerColor = Color.Transparent
                ) {
                    ExtendedFloatingActionButton(
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation() ,
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_shuffle_24), 
                            contentDescription = stringResource(id = R.string.shuffle)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(text = stringResource(id = R.string.shuffle))
                    }
                    
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
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
                    
                    Column { miniPlayer() }
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        top = systemBarsPadding.calculateTopPadding(),
                        end = 16.dp,
                        bottom = systemBarsPadding.calculateBottomPadding()
                    ),
            ) {
                Row(
                    modifier = Modifier.height(80.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SearchBar(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.extraLarge)
                            .clickable{ },
                        placeholderText = stringResource(id = R.string.search_your_library),
                        leadingUnit = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_search_24),
                                contentDescription = stringResource(id = R.string.search)
                            )
                        },
                        trailingUnit = {
                            Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                                IconButton(onClick = { expandedMenu.value = true }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                                        contentDescription = stringResource(id = R.string.menu)
                                    )
                                }
                
                                BarDropDownMenu(
                                    expanded = expandedMenu,
                                    onNavigate = { }
                                )
                            }
                        }
                    )
                }
    
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(
                            RoundedCornerShape(
                                topStart = 16.dp,
                                topEnd = 16.dp
                            )
                        )
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) { content(PaddingValues()) }
            }
        }
    }
}

@Composable
private fun Expanded(

) {

}

@Composable
private fun ExpandedLandscape(

) {

}