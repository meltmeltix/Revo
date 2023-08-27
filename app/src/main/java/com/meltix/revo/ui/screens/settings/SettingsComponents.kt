package com.meltix.revo.ui.screens.settings

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.meltix.pixely_components.PixelyListItem
import com.meltix.pixely_components.PixelyListItemDefaults
import com.meltix.revo.R
import com.meltix.revo.data.classes.settings.MainSettingsItem
import com.meltix.revo.ui.navigation.SettingsScreens

@Composable
fun MainSettingsLayout(
    windowWidthSizeClass: WindowWidthSizeClass,
    onBackButtonClick: () -> Boolean,
    onCompactItemClick: (String) -> Unit,
    currentDestinationRoute: String?,
    onExpandedItemClick: (String) -> Unit,
    viewModel: SettingsViewModel,
    expandedContent: @Composable () -> Unit
) {
    val destinationList = listOf(
        MainSettingsItem(
            headlineText = stringResource(id = R.string.str_library),
            supportingText = stringResource(id = R.string.info_library),
            icon = painterResource(id = R.drawable.ic_outlined_library_music_24),
            route = SettingsScreens.Library.route
        ),
        MainSettingsItem(
            headlineText = stringResource(id = R.string.str_customization),
            supportingText = stringResource(id = R.string.info_customization),
            icon = painterResource(id = R.drawable.ic_outlined_palette_24),
            route = SettingsScreens.Customization.route
        ),
        MainSettingsItem(
            headlineText = stringResource(id = R.string.str_other),
            supportingText = stringResource(id = R.string.info_other),
            icon = painterResource(id = R.drawable.ic_outlined_interests_24),
            route = SettingsScreens.Other.route
        ),
        MainSettingsItem(
            headlineText = stringResource(id = R.string.str_about),
            supportingText = stringResource(id = R.string.info_about),
            icon = painterResource(id = R.drawable.ic_outlined_info_24),
            route = SettingsScreens.About.route
        )
    )
    
    when(windowWidthSizeClass) {
        WindowWidthSizeClass.Compact -> CompactLayout(
            destinationList = destinationList,
            onBackButtonClick = { onBackButtonClick() },
            onItemClick = { onCompactItemClick(it) }
        )
        else -> ExpandedLayout(
            viewModel = viewModel,
            destinationList = destinationList,
            onBackButtonClick = { onBackButtonClick() },
            currentDestinationRoute = currentDestinationRoute,
            onItemClick = { onExpandedItemClick(it) }
        ) { expandedContent() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CompactLayout(
    destinationList: List<MainSettingsItem>,
    onBackButtonClick: () -> Unit,
    onItemClick: (String) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    val systemCutoutPadding = WindowInsets.displayCutout.asPaddingValues()
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Scaffold(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .padding(
                    start = systemCutoutPadding.calculateStartPadding(LayoutDirection.Ltr),
                    end = systemCutoutPadding.calculateEndPadding(LayoutDirection.Ltr)
                ),
            topBar = {
                LargeTopAppBar(
                    title = { Text(stringResource(R.string.str_settings)) },
                    navigationIcon = {
                        IconButton(onClick = { onBackButtonClick() }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_baseline_arrow_back_24),
                                contentDescription = stringResource(R.string.str_back)
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            }
        ) { padding ->
            LazyColumn(
                modifier = Modifier.padding(
                    start = padding.calculateStartPadding(LayoutDirection.Ltr),
                    top = padding.calculateTopPadding(),
                    end = padding.calculateEndPadding(LayoutDirection.Ltr),
                    bottom = 0.dp
                ),
                contentPadding = PaddingValues(bottom = systemBarsPadding.calculateBottomPadding()),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                itemsIndexed(destinationList) { key, item ->
                    key(key) {
                        PixelyListItem(
                            modifier = Modifier.clickable { onItemClick(item.route) },
                            headlineTextString = item.headlineText,
                            supportingTextString = item.supportingText,
                            leadingContent = { Icon(item.icon, item.headlineText) },
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExpandedLayout(
    viewModel: SettingsViewModel,
    destinationList: List<MainSettingsItem>,
    onBackButtonClick: () -> Unit,
    currentDestinationRoute: String?,
    onItemClick: (String) -> Unit,
    content: @Composable () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    val systemCutoutPadding = WindowInsets.displayCutout.asPaddingValues()
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.inverseOnSurface
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = systemCutoutPadding.calculateStartPadding(LayoutDirection.Ltr),
                    top = systemBarsPadding.calculateTopPadding(),
                    end = systemCutoutPadding.calculateEndPadding(LayoutDirection.Ltr)
                ),
        ) {
            Scaffold(
                modifier = Modifier
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
                    .weight(1f),
                topBar = {
                    LargeTopAppBar(
                        title = { Text(stringResource(R.string.str_settings)) },
                        navigationIcon = {
                            IconButton(onClick = { onBackButtonClick() }) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_baseline_arrow_back_24),
                                    contentDescription = stringResource(R.string.str_back)
                                )
                            }
                        },
                        windowInsets = WindowInsets(top = 0.dp),
                        colors = TopAppBarDefaults.largeTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.inverseOnSurface,
                        ),
                        scrollBehavior = scrollBehavior
                    )
                },
                containerColor = MaterialTheme.colorScheme.inverseOnSurface
            ) { padding ->
                LazyColumn(
                    modifier = Modifier.padding(
                        start = padding.calculateStartPadding(LayoutDirection.Ltr) + 10.dp,
                        top = padding.calculateTopPadding(),
                        end = padding.calculateEndPadding(LayoutDirection.Ltr) + 10.dp,
                        bottom = 0.dp
                    ),
                    contentPadding = PaddingValues(bottom = systemBarsPadding.calculateBottomPadding()),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    itemsIndexed(destinationList) { key, item ->
                        val selected =
                            item.route == currentDestinationRoute ||
                            item.route == viewModel.latestDestination
                        if(currentDestinationRoute == SettingsScreens.Library.route) {
                            viewModel.latestDestination = SettingsScreens.Library.route
                        }
                        
                        key(key) {
                            PixelyListItem(
                                modifier = Modifier
                                    .clip(MaterialTheme.shapes.large)
                                    .clickable { onItemClick(item.route) },
                                headlineTextString = item.headlineText,
                                supportingTextString = item.supportingText,
                                leadingContent = { Icon(item.icon, item.headlineText) },
                                colors = PixelyListItemDefaults.colors(
                                    containerColor =
                                        if(selected) MaterialTheme.colorScheme.primaryContainer
                                        else MaterialTheme.colorScheme.inverseOnSurface,
                                    leadingContentColor =
                                        if(selected) MaterialTheme.colorScheme.onPrimaryContainer
                                        else MaterialTheme.colorScheme.onSurface,
                                    headlineColor =
                                        if(selected) MaterialTheme.colorScheme.onSecondaryContainer
                                        else MaterialTheme.colorScheme.onSurface,
                                )
                            )
                        }
                    }
                }
            }
            
            Column(
                modifier = Modifier
                    .padding(end = 24.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomEnd = 0.dp,
                            bottomStart = 0.dp
                        )
                    )
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.surface)
            ) { content() }
        }
    }
}