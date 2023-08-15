package com.meltix.revo.ui.screens.settings.customization

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.meltix.pixely_components.PixelyListItem
import com.meltix.pixely_components.PixelySectionTitle
import com.meltix.revo.R
import com.meltix.revo.ui.navigation.SettingsScreens
import com.meltix.revo.ui.theme.RevoMusicPlayerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomizationScreen(
    navController: NavController
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    val systemCutoutPadding = WindowInsets.displayCutout.asPaddingValues()

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
                    CustomizationTopActionBar(
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
                            PixelySectionTitle(
                                stringTitle = stringResource(id = R.string.str_theme)
                            )
                        }
                        
                        item { 
                            PixelyListItem(
                                modifier = Modifier
                                    .clickable { }, 
                                headlineTextString = stringResource(id = R.string.str_appTheme)
                            )
                        }

                        item {
                            PixelyListItem(
                                modifier = Modifier
                                    .clickable { },
                                headlineTextString = stringResource(id = R.string.str_colorScheme)
                            )
                        }
                        
                        item {
                            PixelySectionTitle(
                                stringTitle = stringResource(id = R.string.str_interface)
                            )
                        }

                        item {
                            PixelyListItem(
                                modifier = Modifier
                                    .clickable {
                                        navController.navigate(
                                            SettingsScreens.PlayerLayoutScreen.route
                                        )
                                    },
                                headlineTextString = stringResource(id = R.string.str_layoutPlayer),
                                supportingTextString = stringResource(id = R.string.info_layoutPlayer)
                            )
                        }

                        item {
                            PixelyListItem(
                                modifier = Modifier.clickable {
                                    navController.navigate(
                                        SettingsScreens.AlbumViewLayoutScreen.route
                                    )
                                },
                                headlineTextString = stringResource(id = R.string.str_albumViewLayout),
                                supportingTextString = stringResource(id = R.string.info_albumViewLayout)
                            )
                        }
                    }
                }
            )
        }
    }
}