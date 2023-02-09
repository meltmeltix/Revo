package com.alessiocameroni.revomusicplayer.ui.screens.settings.customization

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alessiocameroni.pixely_components.PixelyListItem
import com.alessiocameroni.pixely_components.PixelySectionTitle
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.ui.navigation.CustomizationSettingsScreens
import com.alessiocameroni.revomusicplayer.ui.theme.RevoMusicPlayerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomizationScreen(
    navController: NavController
) {
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
                        title = { Text(text = stringResource(id = R.string.str_customization)) },
                        navigationIcon = {
                            IconButton(
                                onClick = { navController.navigateUp() }
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
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        item {
                            PixelySectionTitle(
                                modifier = Modifier,
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
                                modifier = Modifier,
                                stringTitle = stringResource(id = R.string.str_interface)
                            )
                        }

                        item {
                            PixelyListItem(
                                modifier = Modifier
                                    .clickable {
                                        navController.navigate(
                                            CustomizationSettingsScreens.PlayerLayoutScreen.route
                                        )
                                    },
                                headlineTextString = stringResource(id = R.string.str_layoutPlayer),
                                supportingTextString = stringResource(id = R.string.desc_layoutPlayer)
                                /*TODO(Put "dynamic" string here with layout, and both strings)*/
                            )
                        }
                    }
                }
            )
        }
    }
}