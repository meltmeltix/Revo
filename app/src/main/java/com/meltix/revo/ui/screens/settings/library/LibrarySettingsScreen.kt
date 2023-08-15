package com.meltix.revo.ui.screens.settings.library

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.meltix.pixely_components.PixelySectionTitle
import com.meltix.revo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibrarySettingsScreen(
    navController: NavController,
    viewModel: LibrarySettingsViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    val systemCutoutPadding = WindowInsets.displayCutout.asPaddingValues()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .padding(
                    PaddingValues(
                        start = systemCutoutPadding.calculateStartPadding(LayoutDirection.Ltr),
                        end = systemCutoutPadding.calculateStartPadding(LayoutDirection.Rtl)
                    )
                ),
            topBar = {
                LibrarySettingsTopActionBar(
                    navController,
                    scrollBehavior
                )
            },
            content = { padding ->
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
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    item {
                        PixelySectionTitle(stringTitle = stringResource(id = R.string.str_spotify))
                    }

                    item {
                        val checkedState by viewModel.spotifyEnabledState.collectAsStateWithLifecycle(false)

                        SpotifyVisibilitySelection(
                            checked = checkedState,
                            onChecked = { viewModel.setSpotifyVisibility(it) }
                        )
                    }
                }
            }
        )
    }
}