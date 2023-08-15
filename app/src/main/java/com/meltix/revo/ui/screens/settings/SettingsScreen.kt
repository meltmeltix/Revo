package com.meltix.revo.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.meltix.pixely_components.PixelyListItem
import com.meltix.revo.R
import com.meltix.revo.ui.navigation.SettingsScreens
import com.meltix.revo.ui.theme.RevoMusicPlayerTheme

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
                                    contentDescription = stringResource(id = R.string.str_back)
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
                                    .clickable { navController.navigate(SettingsScreens.LibrarySettingsScreen.route) },
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

                        Row(modifier = Modifier.fillMaxWidth()) {
                            PixelyListItem(
                                modifier = Modifier
                                    .clickable { navController.navigate(SettingsScreens.CustomizationScreen.route) },
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

                        Row(modifier = Modifier.fillMaxWidth()) {
                            PixelyListItem(
                                modifier = Modifier
                                    .clickable { navController.navigate(SettingsScreens.OtherScreen.route) },
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

                        Row(modifier = Modifier.fillMaxWidth()) {
                            PixelyListItem(
                                modifier = Modifier
                                    .clickable { navController.navigate(SettingsScreens.AboutScreen.route) },
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
}