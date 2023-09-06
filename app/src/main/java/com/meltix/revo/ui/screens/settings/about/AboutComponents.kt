package com.meltix.revo.ui.screens.settings.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.meltix.revo.R
import com.meltix.revo.ui.components.SmallImageContainer

@Composable
fun AboutLayout(
    windowClass: WindowSizeClass,
    onNavigateUp: () -> Unit,
    content: LazyListScope.() -> Unit
) {
    when(windowClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> CompactLayout(onNavigateUp, content)
        else -> ExpandedLayout { content() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CompactLayout(onNavigateUp: () -> Unit, content: LazyListScope.() -> Unit) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text(text = stringResource(id = R.string.str_about)) },
                navigationIcon = {
                    IconButton(onClick = { onNavigateUp() }) {
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
private fun ExpandedLayout(content: LazyListScope.() -> Unit) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text(stringResource(R.string.str_about)) },
                windowInsets = WindowInsets(top = 0.dp),
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
fun AppLogoItem() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ill_revo_r_monochrome),
            contentDescription = stringResource(id = R.string.app_name),
            modifier = Modifier
                .padding(vertical = 30.dp)
                .size(50.dp),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondaryContainer)
        )

        Image(
            painter = painterResource(id = R.drawable.ill_revo_text_logo),
            contentDescription = stringResource(id = R.string.app_name),
            modifier = Modifier
                .padding(vertical = 20.dp)
                .size(120.dp, 60.dp),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondaryContainer)
        )
    }
}

@Composable
fun CenterCreditItem(
    titleText: String,
    subText: String,
    imageContent: @Composable (() -> Unit)
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SmallImageContainer(
            modifier = Modifier
                .clip(CircleShape)
                .size(70.dp),
            painterPlaceholder = painterResource(id = R.drawable.ic_outlined_account_circle_24)
        ) { imageContent() }

        Text(
            text = titleText,
            modifier = Modifier.padding(top = 5.dp),
            style = MaterialTheme.typography.titleLarge,
            fontSize = 20.sp
        )

        Text(
            text = subText,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}