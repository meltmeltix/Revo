package com.alessiocameroni.revomusicplayer.settings.about

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.navigation.Screens
import com.alessiocameroni.revomusicplayer.settings.about.components.CreditsItem
import com.alessiocameroni.revomusicplayer.settings.about.components.MadeWithLoveItem
import com.alessiocameroni.revomusicplayer.settings.main.components.SectionTitle
import com.alessiocameroni.revomusicplayer.ui.theme.RevoMusicPlayerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavController) {
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        decayAnimationSpec,
        rememberTopAppBarState()
    )

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
                },
                content = { padding ->
                    Column(
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SectionTitle(
                            stringTitle = stringResource(id = R.string.str_developer),
                            modifier = Modifier.fillMaxWidth()
                        )
                        
                        Row( modifier = Modifier.fillMaxWidth() ) {
                            MadeWithLoveItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(22.dp))
                                    .clickable { },
                                stringTitle = stringResource(id = R.string.str_madewithloveby),
                                stringName = stringResource(id = R.string.str_alessiocameroni),
                                painterProfile = painterResource(id = R.drawable.ill_meltix_200)
                            )
                        }

                        Divider()
                        
                        SectionTitle(
                            stringTitle = stringResource(id = R.string.str_specialthanks), 
                            modifier = Modifier.fillMaxWidth()
                        )

                        Row( modifier = Modifier.fillMaxWidth() ) {
                            val openDialog = remember { mutableStateOf(false) }

                            CreditsItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(22.dp))
                                    .clickable { openDialog.value = true },
                                stringTitle = stringResource(id = R.string.str_katherine), 
                                stringDescription = stringResource(id = R.string.desc_katherine)
                            )
                        }

                        Row( modifier = Modifier.fillMaxWidth() ) {
                            val openDialog = remember { mutableStateOf(false) }

                            CreditsItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(22.dp))
                                    .clickable { openDialog.value = true },
                                stringTitle = stringResource(id = R.string.str_arigata9),
                                stringDescription = stringResource(id = R.string.desc_arigata9)
                            )
                        }
                    }
                }
            )
        }
    }
}
