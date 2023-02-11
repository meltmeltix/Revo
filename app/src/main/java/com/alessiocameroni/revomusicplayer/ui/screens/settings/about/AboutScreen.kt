package com.alessiocameroni.revomusicplayer.ui.screens.settings.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alessiocameroni.pixely_components.*
import com.alessiocameroni.revomusicplayer.BuildConfig
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.ui.components.SmallImageContainer
import com.alessiocameroni.revomusicplayer.ui.theme.RevoMusicPlayerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavController) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val uriHandler = LocalUriHandler.current

    RevoMusicPlayerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    LargeTopAppBar(
                        title = { Text(text = stringResource(id = R.string.str_about)) },
                        navigationIcon = {
                            IconButton(
                                onClick = { navController.popBackStack() }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                                    contentDescription = stringResource(id = R.string.desc_back)
                                )
                            }
                        },
                        scrollBehavior = scrollBehavior
                    )
                },
                content = { padding ->
                    LazyColumn(
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            AppLogoItem()
                        }

                        item {
                            PixelySectionTitle(
                                stringTitle = stringResource(id = R.string.str_membersAndContributors)
                            )
                        }
                        
                        item {
                            PixelySegmentedColumn {
                                PixelySegmentedRow(
                                    modifier = Modifier
                                        .clickable {
                                            uriHandler.openUri(
                                                "https://github.com/alessiocameroni"
                                            )
                                        }
                                ) {
                                    CenterCreditItem(
                                        titleText = stringResource(id = R.string.str_AlessioCameroni),
                                        subText = stringResource(id = R.string.desc_AlessioCameroni)
                                    ) {
                                        Image(
                                            painter =
                                                painterResource(id = R.drawable.ill_meltix_200),
                                            contentDescription =
                                                stringResource(id = R.string.str_AlessioCameroni)
                                        )
                                    }
                                }

                                PixelySegmentedRow(
                                    modifier = Modifier
                                        .clickable {
                                            uriHandler.openUri(
                                                "https://github.com/arigata9"
                                            )
                                        }
                                ) {
                                    PixelyListItem(
                                        headlineTextString =
                                            stringResource(id = R.string.str_arigata9),
                                        supportingTextString =
                                            stringResource(id = R.string.desc_arigata9),
                                        leadingContent = {
                                            SmallImageContainer(
                                                modifier = Modifier
                                                    .padding(horizontal = 5.dp)
                                                    .size(54.dp)
                                                    .clip(CircleShape),
                                                painterPlaceholder =
                                                    painterResource(id = R.drawable.ic_outlined_account_circle_24)
                                            ) {
                                                Image(
                                                    modifier = Modifier
                                                        .fillMaxSize(),
                                                    painter =
                                                        painterResource(id = R.drawable.bm_arigata9),
                                                    contentDescription =
                                                        stringResource(id = R.string.str_arigata9),
                                                )
                                            }
                                        },
                                        colors = PixelyListItemDefaults.colors(
                                            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
                                        )
                                    )
                                }
                            }
                        }

                        item {
                            PixelySectionTitle(
                                stringTitle = stringResource(id = R.string.str_other)
                            )
                        }

                        item {
                            PixelySegmentedColumn {
                                PixelySegmentedRow(
                                    modifier = Modifier
                                        .clickable {
                                            uriHandler.openUri(
                                                "https://github.com/alessiocameroni/RevoMusicPlayer"
                                            )
                                        }
                                ) {
                                    PixelyListItem(
                                        headlineTextString = 
                                            stringResource(id = R.string.str_github),
                                        supportingTextString = 
                                            stringResource(id = R.string.str_checkOnGithub),
                                        leadingContent = {
                                            Icon(
                                                painter = 
                                                    painterResource(id = R.drawable.ic_baseline_github_24),
                                                contentDescription = 
                                                    stringResource(id = R.string.str_github)
                                            )
                                        },
                                        colors = PixelyListItemDefaults.colors(
                                            containerColor =
                                            MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
                                        )
                                    )
                                }

                                PixelySegmentedRow {
                                    PixelyListItem(
                                        headlineTextString =
                                            stringResource(id = R.string.str_openSourceLicenses),
                                        supportingTextString =
                                            stringResource(id = R.string.desc_openSourceLicenses),
                                        leadingContent = {
                                            Icon(
                                                painter =
                                                    painterResource(id = R.drawable.ic_outlined_workspace_premium_24),
                                                contentDescription =
                                                    stringResource(id = R.string.str_openSourceLicenses)
                                            )
                                        },
                                        colors = PixelyListItemDefaults.colors(
                                            containerColor =
                                            MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
                                        )
                                    )
                                }
                                
                                PixelySegmentedRow {
                                    PixelyListItem(
                                        headlineTextString =
                                            stringResource(id = R.string.str_appVersion),
                                        supportingTextString = BuildConfig.VERSION_NAME,
                                        leadingContent = {
                                            Icon(
                                                painter = 
                                                    painterResource(id = R.drawable.ic_outlined_info_24), 
                                                contentDescription = 
                                                    stringResource(id = R.string.str_appVersion)
                                            )
                                        },
                                        colors = PixelyListItemDefaults.colors(
                                            containerColor =
                                                MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}