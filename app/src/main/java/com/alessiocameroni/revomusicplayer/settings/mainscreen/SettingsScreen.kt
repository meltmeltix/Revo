package com.alessiocameroni.revomusicplayer.settings.mainscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.navigation.SettingsScreens
import com.alessiocameroni.revomusicplayer.settings.mainscreen.components.SettingsCategoryItem
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
                                    .fillMaxWidth()
                                    .height(84.dp)
                                    .clip(RoundedCornerShape(22.dp))
                                    .clickable { navController.navigate(SettingsScreens.LibrarySettingsScreen.route) }
                            )
                        }

                        Row(modifier = Modifier.fillMaxWidth()) {
                            SettingsCategoryItem(
                                painterIcon = painterResource(id = R.drawable.ic_outlined_palette_24),
                                stringTitleItem = stringResource(id = R.string.str_customization),
                                stringSubtitleItem = stringResource(id = R.string.desc_customization),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(84.dp)
                                    .clip(RoundedCornerShape(22.dp))
                                    .clickable { navController.navigate(SettingsScreens.CustomizationScreen.route) }
                            )
                        }

                        Row(modifier = Modifier.fillMaxWidth()) {
                            SettingsCategoryItem(
                                painterIcon = painterResource(id = R.drawable.ic_outlined_info_24),
                                stringTitleItem = stringResource(id = R.string.str_about),
                                stringSubtitleItem = stringResource(id = R.string.desc_about),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(84.dp)
                                    .clip(RoundedCornerShape(22.dp))
                                    .clickable { navController.navigate(SettingsScreens.AboutScreen.route) }
                            )
                        }
                    }
                }
            )
        }
    }
}