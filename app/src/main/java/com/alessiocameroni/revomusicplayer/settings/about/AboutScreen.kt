package com.alessiocameroni.revomusicplayer.settings.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.navigation.Screens
import com.alessiocameroni.revomusicplayer.settings.about.components.BigCardAboutItem
import com.alessiocameroni.revomusicplayer.settings.about.components.BigCreditsItem
import com.alessiocameroni.revomusicplayer.settings.about.components.CreditsDialogDetails
import com.alessiocameroni.revomusicplayer.settings.about.components.CreditsItem
import com.alessiocameroni.revomusicplayer.settings.main.components.SectionTitle
import com.alessiocameroni.revomusicplayer.ui.theme.RevoMusicPlayerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavController) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

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
                                onClick = { navController.navigate(Screens.SettingsScreen.route) }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                                    contentDescription = stringResource(id = R.string.desc_back)
                                )
                            }
                        },
                        scrollBehavior = scrollBehavior
                    )
                }
            ) { padding ->
                LazyColumn(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        BigCardAboutItem(
                            modifier = Modifier
                                .padding(14.dp, 16.dp, 14.dp, 0.dp)
                                .fillMaxWidth(),
                            unitBanner = {
                                Image(
                                    contentScale = ContentScale.FillWidth,
                                    modifier = Modifier.fillMaxWidth(),
                                    painter = painterResource(id = R.drawable.ill_revo_banner),
                                    contentDescription = "Description wee"
                                )
                            },
                            cardShape = RoundedCornerShape(12.dp)
                        )
                    }

                    item {
                        SectionTitle (
                            stringTitle = stringResource(id = R.string.str_developer),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    item {
                        val openDialog = remember { mutableStateOf(false) }
                        val uriHandler = LocalUriHandler.current

                        BigCreditsItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(22.dp))
                                .clickable { openDialog.value = true },
                            stringTitle = stringResource(id = R.string.str_madewithloveby),
                            stringName = stringResource(id = R.string.str_alessiocameroni),
                            unitProfile = {
                                Image(
                                    painter = painterResource(id = R.drawable.ill_meltix_200),
                                    contentDescription = stringResource(id = R.string.str_alessiocameroni)
                                )
                            }
                        )

                        if (openDialog.value) {
                        CreditsDialogDetails(
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(24.dp))
                                .width(500.dp),
                            openDialog = openDialog,
                            stringTitle = stringResource(id = R.string.str_alessiocameroni),
                            stringDescription = stringResource(id = R.string.bio_alessiocameroni),
                            unitProfile = {
                                Image(
                                    painter = painterResource(id = R.drawable.ill_meltix_200),
                                    contentDescription = stringResource(id = R.string.str_alessiocameroni)
                                )
                            }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                IconButton(
                                    onClick = { uriHandler.openUri("https://www.instagram.com/meltmeltix/") }
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_baseline_instagram_24),
                                        contentDescription = stringResource(id = R.string.str_instgram)
                                    )
                                }

                                IconButton(
                                    onClick = { uriHandler.openUri("https://github.com/alessiocameroni") }
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_baseline_github_24),
                                        contentDescription = stringResource(id = R.string.str_github)
                                    )
                                }

                                IconButton(
                                    onClick = { uriHandler.openUri("https://twitter.com/meltmeltix") }
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_baseline_twitter_24),
                                        contentDescription = stringResource(id = R.string.str_twitter)
                                    )
                                }
                            }
                        }
                    }
                    }

                    item { Divider() }

                    item {
                        SectionTitle(
                            stringTitle = stringResource(id = R.string.str_specialthanks),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    item {
                        val openDialog = remember { mutableStateOf(false) }
                        val uriHandler = LocalUriHandler.current

                        CreditsItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(22.dp))
                                .clickable { openDialog.value = true },
                            stringTitle = stringResource(id = R.string.str_katherine),
                            stringDescription = stringResource(id = R.string.desc_katherine),
                            unitProfile = null
                        )

                        if (openDialog.value) {
                            CreditsDialogDetails(
                                modifier = Modifier
                                    .clip(shape = RoundedCornerShape(24.dp))
                                    .width(500.dp),
                                openDialog = openDialog,
                                stringTitle = stringResource(id = R.string.str_katherine),
                                stringDescription = stringResource(id = R.string.desc_katherine),
                                unitProfile = null
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                ) {
                                    IconButton(
                                        onClick = { uriHandler.openUri("https://www.instagram.com/kats.keebs/") }
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_baseline_instagram_24),
                                            contentDescription = stringResource(id = R.string.str_instgram)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    item {
                        val openDialog = remember { mutableStateOf(false) }
                        val uriHandler = LocalUriHandler.current

                        CreditsItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(22.dp))
                                .clickable { openDialog.value = true },
                            stringTitle = stringResource(id = R.string.str_arigata9),
                            stringDescription = stringResource(id = R.string.desc_arigata9),
                            unitProfile = null
                        )

                        if (openDialog.value) {
                            CreditsDialogDetails(
                                modifier = Modifier
                                    .clip(shape = RoundedCornerShape(24.dp))
                                    .width(500.dp),
                                openDialog = openDialog,
                                stringTitle = stringResource(id = R.string.str_arigata9),
                                stringDescription = stringResource(id = R.string.desc_arigata9),
                                unitProfile = null
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                ) {
                                    IconButton(
                                        onClick = { uriHandler.openUri("https://github.com/arigata9") }
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_baseline_github_24),
                                            contentDescription = stringResource(id = R.string.str_github)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
