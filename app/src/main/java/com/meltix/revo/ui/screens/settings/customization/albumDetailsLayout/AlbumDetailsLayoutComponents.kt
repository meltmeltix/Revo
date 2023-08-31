package com.meltix.revo.ui.screens.settings.customization.albumDetailsLayout

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.meltix.pixely_components.PixelyListItem
import com.meltix.revo.R
import com.meltix.revo.data.classes.album.HeaderLayout
import com.meltix.revo.util.functions.selectAlbumViewHeaderLayoutString
import com.meltix.revo.util.functions.selectAlbumViewHeaderLayoutSupportingString

@Composable
fun AlbumDetailsLytLayout(
    windowWidthSizeClass: WindowWidthSizeClass,
    onBackButtonClick: () -> Unit,
    content: LazyListScope.() -> Unit
) {
    when(windowWidthSizeClass) {
        WindowWidthSizeClass.Compact -> CompactLayout(onBackButtonClick, content)
        else -> ExpandedLayout(onBackButtonClick, content)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CompactLayout(onBackButtonClick: () -> Unit, content: LazyListScope.() -> Unit) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text(stringResource(id = R.string.str_albumViewLayout)) },
                navigationIcon = {
                    IconButton(onClick = { onBackButtonClick() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                            contentDescription = stringResource(id = R.string.str_back)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = padding.calculateStartPadding(LayoutDirection.Ltr),
                    top = padding.calculateTopPadding(),
                    end = padding.calculateEndPadding(LayoutDirection.Rtl),
                    bottom = 0.dp,
                ),
            contentPadding = PaddingValues(bottom = systemBarsPadding.calculateBottomPadding()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) { content() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExpandedLayout(onBackButtonClick: () -> Unit, content: LazyListScope.() -> Unit) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text(stringResource(id = R.string.str_albumViewLayout)) },
                navigationIcon = {
                    IconButton(onClick = { onBackButtonClick() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                            contentDescription = stringResource(id = R.string.str_back)
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
                bottom = 0.dp,
            ),
            contentPadding = PaddingValues(bottom = systemBarsPadding.calculateBottomPadding()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) { content() }
    }
}

@Composable
fun LayoutSelector(
    options: Array<HeaderLayout>,
    selected: HeaderLayout,
    onSelected: (HeaderLayout) -> Unit
) {
    options.forEach { option ->
        PixelyListItem(
            modifier = Modifier
                .selectable(
                    selected = (option == selected),
                    onClick = { onSelected(option) },
                    role = Role.RadioButton
                ),
            headlineTextString = selectAlbumViewHeaderLayoutString(option),
            supportingTextString = selectAlbumViewHeaderLayoutSupportingString(option),
            leadingContent = { RadioButton(selected = (option == selected), onClick = null) }
        )
    }
}

@Composable
fun AlbumPreview(
    windowWidthClass: WindowWidthSizeClass,
    selectedLayout: HeaderLayout
) {
    when(windowWidthClass) {
        WindowWidthSizeClass.Compact -> {
            Image(
                modifier = Modifier
                    .padding(100.dp, 30.dp, 100.dp, 0.dp)
                    .fillMaxSize(),
                painter = albumPainterLayout(WindowWidthSizeClass.Compact, selectedLayout),
                contentDescription = stringResource(id = R.string.app_name),
                contentScale = ContentScale.FillWidth
            )
        }
        WindowWidthSizeClass.Medium -> {
            Image(
                modifier = Modifier
                    .padding(30.dp, 30.dp, 30.dp, 0.dp)
                    .fillMaxSize(),
                painter = albumPainterLayout(WindowWidthSizeClass.Medium, selectedLayout),
                contentDescription = stringResource(id = R.string.app_name),
                contentScale = ContentScale.FillWidth
            )
        }
        WindowWidthSizeClass.Expanded -> {
        
        }
    }
}

@Composable
private fun albumPainterLayout(windowWidthClass: WindowWidthSizeClass, selectedLayout: HeaderLayout): Painter {
    val darkTheme = isSystemInDarkTheme()
    
    return when(windowWidthClass) {
        WindowWidthSizeClass.Medium ->
            when (selectedLayout) {
                HeaderLayout.REVO ->
                    when (darkTheme) {
                        true -> painterResource(id = R.drawable.ill_revo_medium_dark)
                        false -> painterResource(id = R.drawable.ill_revo_medium_light)
                    }
                HeaderLayout.FRUIT_MUSIC ->
                    when (darkTheme) {
                        true -> painterResource(id = R.drawable.ill_fruit_medium_dark)
                        false -> painterResource(id = R.drawable.ill_fruit_medium_light)
                    }
                HeaderLayout.MINIMAL ->
                    when (darkTheme) {
                        true -> painterResource(id = R.drawable.ill_minimal_medium_dark)
                        false -> painterResource(id = R.drawable.ill_minimal_medium_light)
                    }
            }
        WindowWidthSizeClass.Expanded ->
            when (selectedLayout) {
                HeaderLayout.REVO ->
                    when (darkTheme) {
                        true -> painterResource(id = R.drawable.ill_revo_compact_dark)
                        false -> painterResource(id = R.drawable.ill_revo_compact_light)
                    }
                HeaderLayout.FRUIT_MUSIC ->
                    when (darkTheme) {
                        true -> painterResource(id = R.drawable.ill_fruit_compact_dark)
                        false -> painterResource(id = R.drawable.ill_fruit_compact_light)
                    }
                HeaderLayout.MINIMAL ->
                    when (darkTheme) {
                        true -> painterResource(id = R.drawable.ill_minimal_compact_dark)
                        false -> painterResource(id = R.drawable.ill_minimal_compact_light)
                    }
            }
        else -> {
            when (selectedLayout) {
                HeaderLayout.REVO ->
                    when (darkTheme) {
                        true -> painterResource(id = R.drawable.ill_revo_compact_dark)
                        false -> painterResource(id = R.drawable.ill_revo_compact_light)
                    }
                HeaderLayout.FRUIT_MUSIC ->
                    when (darkTheme) {
                        true -> painterResource(id = R.drawable.ill_fruit_compact_dark)
                        false -> painterResource(id = R.drawable.ill_fruit_compact_light)
                    }
                HeaderLayout.MINIMAL ->
                    when (darkTheme) {
                        true -> painterResource(id = R.drawable.ill_minimal_compact_dark)
                        false -> painterResource(id = R.drawable.ill_minimal_compact_light)
                    }
            }
        }
    }
}