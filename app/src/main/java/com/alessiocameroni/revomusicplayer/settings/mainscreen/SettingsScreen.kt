package com.alessiocameroni.revomusicplayer.settings.mainscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alessiocameroni.pixely_components.PixelyListItem
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.data.modifiers.clickableRowItem
import com.alessiocameroni.revomusicplayer.data.navigation.SettingsScreens
import com.alessiocameroni.revomusicplayer.ui.theme.RevoMusicPlayerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    RevoMusicPlayerTheme{
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold(
                topBar = {
                    LargeTopAppBar(
                        title = { Text(text = stringResource(id = R.string.str_settings)) },
                        navigationIcon = {
                            IconButton(
                                onClick = { navController.navigateUp() }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                                    contentDescription = stringResource(id = R.string.desc_back)
                                )
                            }
                        }
                    )
                },
                content = { padding ->
                    Column(
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ){
                        Row(modifier = Modifier.fillMaxWidth()) {
                            PixelyListItem(
                                modifier = Modifier
                                    .clickableRowItem()
                                    .clickable { navController.navigate(SettingsScreens.LibrarySettingsScreen.route) },
                                stringHeadlineText = stringResource(id = R.string.str_library),
                                stringSupportingText = stringResource(id = R.string.desc_library),
                                leadingUnit = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_outlined_library_music_24),
                                        contentDescription = stringResource(id = R.string.desc_library)
                                    )
                                }
                            )
                        }

                        Row(modifier = Modifier.fillMaxWidth()) {
                            PixelyListItem(
                                modifier = Modifier
                                    .clickableRowItem()
                                    .clickable { navController.navigate(SettingsScreens.CustomizationScreen.route) },
                                stringHeadlineText = stringResource(id = R.string.str_customization),
                                stringSupportingText = stringResource(id = R.string.desc_customization),
                                leadingUnit = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_outlined_palette_24), 
                                        contentDescription = stringResource(id = R.string.desc_customization)
                                    )
                                }
                            )
                        }

                        Row(modifier = Modifier.fillMaxWidth()) {
                            PixelyListItem(
                                modifier = Modifier
                                    .clickableRowItem()
                                    .clickable { navController.navigate(SettingsScreens.OtherScreen.route) },
                                stringHeadlineText = stringResource(id = R.string.str_other),
                                stringSupportingText = stringResource(id = R.string.desc_other),
                                leadingUnit = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_outlined_interests_24),
                                        contentDescription = stringResource(id = R.string.desc_other)
                                    )
                                }
                            )
                        }

                        Row(modifier = Modifier.fillMaxWidth()) {
                            PixelyListItem(
                                modifier = Modifier
                                    .clickableRowItem()
                                    .clickable { navController.navigate(SettingsScreens.AboutScreen.route) },
                                stringHeadlineText = stringResource(id = R.string.str_about),
                                stringSupportingText = stringResource(id = R.string.desc_about),
                                leadingUnit = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_outlined_info_24), 
                                        contentDescription = stringResource(id = R.string.desc_about)
                                    )
                                }
                            )
                        }
                    }
                }
            )
        }
    }
}