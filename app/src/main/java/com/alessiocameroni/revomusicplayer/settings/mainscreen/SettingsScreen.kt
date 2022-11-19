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
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.data.modifiers.clickableRowItem
import com.alessiocameroni.revomusicplayer.data.navigation.SettingsScreens
import com.alessiocameroni.revomusicplayer.settings.components.SettingsCategoryItem
import com.alessiocameroni.revomusicplayer.settings.components.SettingsItem
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
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ){
                        Row(modifier = Modifier.fillMaxWidth()) {
                            SettingsCategoryItem(
                                painterIcon = painterResource(id = R.drawable.outlined_library_music_24),
                                stringTitleItem = stringResource(id = R.string.str_library),
                                stringSubtitleItem = stringResource(id = R.string.desc_library),
                                modifier = Modifier
                                    .clickableRowItem()
                                    .clickable { navController.navigate(SettingsScreens.LibrarySettingsScreen.route) }
                            )
                        }

                        Row(modifier = Modifier.fillMaxWidth()) {
                            SettingsCategoryItem(
                                painterIcon = painterResource(id = R.drawable.ic_outlined_palette_24),
                                stringTitleItem = stringResource(id = R.string.str_customization),
                                stringSubtitleItem = stringResource(id = R.string.desc_customization),
                                modifier = Modifier
                                    .clickableRowItem()
                                    .clickable { navController.navigate(SettingsScreens.CustomizationScreen.route) }
                            )
                        }

                        Row(modifier = Modifier.fillMaxWidth()) {
                            SettingsCategoryItem(
                                painterIcon = painterResource(id = R.drawable.ic_outlined_info_24),
                                stringTitleItem = stringResource(id = R.string.str_about),
                                stringSubtitleItem = stringResource(id = R.string.desc_about),
                                modifier = Modifier
                                    .clickableRowItem()
                                    .clickable { navController.navigate(SettingsScreens.AboutScreen.route) }
                            )
                        }

                        Row(modifier = Modifier.fillMaxWidth()) {
                            SettingsItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickableRowItem()
                                    .clickable {  },
                                stringMainTitle = "This is a long title",
                                stringSubtitle = "And this is a long string explaining",
                                leadingUnit = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_outlined_settings_24),
                                        contentDescription = "Description"
                                    )
                                },
                                trailingUnit = {
                                    Switch(
                                        checked = false, onCheckedChange = {}
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