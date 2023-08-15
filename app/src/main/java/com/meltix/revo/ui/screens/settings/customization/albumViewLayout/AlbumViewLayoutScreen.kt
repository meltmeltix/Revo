package com.meltix.revo.ui.screens.settings.customization.albumViewLayout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.meltix.pixely_components.PixelySupportInfoText
import com.meltix.revo.R
import com.meltix.revo.data.classes.album.HeaderLayout
import com.meltix.revo.ui.theme.RevoMusicPlayerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumViewLayoutScreen(
    navController: NavController,
    viewModel: AlbumViewLayoutViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    val systemCutoutPadding = WindowInsets.displayCutout.asPaddingValues()
    val selectedLayout by viewModel.headerLayout.collectAsStateWithLifecycle(HeaderLayout.REVO)

    RevoMusicPlayerTheme {
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
                    AlbumViewLayoutTopActionBar(
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
                            AlbumViewLayoutHeaderPreview(
                                modifier = Modifier.padding(horizontal = 15.dp),
                                selectedOption = selectedLayout
                            )
                        }

                        item {
                            Row(modifier = Modifier) {
                                PixelySupportInfoText(
                                    stringText = stringResource(id = R.string.info_albumViewLayout)
                                )
                            }
                        }

                        item {
                            Column(
                                modifier = Modifier.selectableGroup(),
                                verticalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                LayoutSelector(
                                    options = HeaderLayout.values(),
                                    selected = selectedLayout,
                                    onSelected = { viewModel.setHeaderLayout(it) },
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}