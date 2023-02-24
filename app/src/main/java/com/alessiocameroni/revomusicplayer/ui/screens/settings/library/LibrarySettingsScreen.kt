package com.alessiocameroni.revomusicplayer.ui.screens.settings.library

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alessiocameroni.pixely_components.PixelyListItem
import com.alessiocameroni.pixely_components.PixelySectionTitle
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.ui.theme.RevoMusicPlayerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibrarySettingsScreen(navController: NavController) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    RevoMusicPlayerTheme{
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    LargeTopAppBar(
                        title = { Text(text = stringResource(id = R.string.str_library)) },
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
                            PixelySectionTitle(stringTitle = stringResource(id = R.string.str_spotify))
                        }

                        item {
                            val switchChecked = remember { mutableStateOf(false) }

                            PixelyListItem(
                                modifier = Modifier
                                    .clickable {
                                        switchChecked.value = !switchChecked.value
                                    },
                                headlineTextString = stringResource(id = R.string.str_showSpotifyIntegration),
                                supportingTextString = stringResource(id = R.string.desc_showSpotifyIntegration),
                                trailingContent = {
                                    Switch(
                                        checked = switchChecked.value,
                                        onCheckedChange = { switchChecked.value = it }
                                    )
                                },
                                trailingContentDivider = true
                            )
                        }
                    }
                }
            )
        }
    }
}