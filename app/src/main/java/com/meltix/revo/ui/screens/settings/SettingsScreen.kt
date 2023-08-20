package com.meltix.revo.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.meltix.pixely_components.PixelyListItem
import com.meltix.revo.R
import com.meltix.revo.ui.navigation.SettingsScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
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
                LargeTopAppBar(
                    title = { Text(text = stringResource(id = R.string.str_settings)) },
                    navigationIcon = {
                        IconButton(
                            onClick = { navController.navigateUp() }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                                contentDescription = stringResource(id = R.string.str_back)
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
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
                ){
                    item {
                        PixelyListItem(
                            modifier = Modifier
                                .clickable { navController.navigate(SettingsScreens.Library.route) },
                            headlineTextString = stringResource(id = R.string.str_library),
                            supportingTextString = stringResource(id = R.string.info_library),
                            leadingContent = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_outlined_library_music_24),
                                    contentDescription = stringResource(id = R.string.info_library)
                                )
                            }
                        )
                    }


                    item {
                        PixelyListItem(
                            modifier = Modifier
                                .clickable { navController.navigate(SettingsScreens.Customization.route) },
                            headlineTextString = stringResource(id = R.string.str_customization),
                            supportingTextString = stringResource(id = R.string.info_customization),
                            leadingContent = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_outlined_palette_24),
                                    contentDescription = stringResource(id = R.string.info_customization)
                                )
                            }
                        )
                    }

                    item {
                        PixelyListItem(
                            modifier = Modifier
                                .clickable { navController.navigate(SettingsScreens.Other.route) },
                            headlineTextString = stringResource(id = R.string.str_other),
                            supportingTextString = stringResource(id = R.string.info_other),
                            leadingContent = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_outlined_interests_24),
                                    contentDescription = stringResource(id = R.string.info_other)
                                )
                            }
                        )
                    }

                    item {
                        PixelyListItem(
                            modifier = Modifier
                                .clickable { navController.navigate(SettingsScreens.About.route) },
                            headlineTextString = stringResource(id = R.string.str_about),
                            supportingTextString = stringResource(id = R.string.info_about),
                            leadingContent = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_outlined_info_24),
                                    contentDescription = stringResource(id = R.string.info_about)
                                )
                            }
                        )
                    }
                }
            }
        )
    }
}