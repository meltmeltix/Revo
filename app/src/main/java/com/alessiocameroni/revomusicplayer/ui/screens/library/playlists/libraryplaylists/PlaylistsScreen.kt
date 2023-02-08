package com.alessiocameroni.revomusicplayer.ui.screens.library.playlists.libraryplaylists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.alessiocameroni.pixely_components.PixelyListItem
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.data.navigation.PlaylistsScreens
import com.alessiocameroni.revomusicplayer.data.navigation.Screens
import com.alessiocameroni.revomusicplayer.ui.screens.library.components.ItemDropDownMenu
import com.alessiocameroni.revomusicplayer.ui.screens.library.components.TopBarDropDownMenu
import com.alessiocameroni.revomusicplayer.ui.screens.library.playlists.libraryplaylists.components.AddPlaylistDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistsScreen(
    navController: NavController,
    navControllerBottomBar: NavHostController
) {
    val expandedMenu = remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val openDialog = remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.str_playlists)) },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate(Screens.SearchScreen.route) }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_search_24),
                            contentDescription = stringResource(id = R.string.desc_searchMenu)
                        )
                    }
                },
                actions = {
                    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                        IconButton(onClick = { expandedMenu.value = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                                contentDescription = stringResource(id = R.string.str_settings)
                            )
                        }

                        TopBarDropDownMenu(
                            expanded = expandedMenu,
                            navController = navController
                        )
                    }
                }, scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = { openDialog.value = true }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_playlist_add_24),
                    contentDescription = stringResource(id = R.string.desc_addPlaylist),
                    modifier = Modifier.size(FloatingActionButtonDefaults.LargeIconSize),
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = { padding ->
            if(openDialog.value) {
                AddPlaylistDialog(
                    modifier = Modifier,
                    openDialog = openDialog
                )
            }

            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(5) {
                    Row(
                        modifier = Modifier
                            .clickable {
                                navControllerBottomBar.navigate(
                                    PlaylistsScreens.PlaylistViewScreen.route
                                )
                            }
                    ) {
                        PixelyListItem(
                            modifier = Modifier,
                            headlineTextString = "Placeholder Playlist",
                            largeHeadline = false,
                            maxHeadlineLines = 1,
                            supportingTextString = "Placeholder Song Number and Time",
                            maxSupportingLines = 1,
                            leadingContent = {
                                Icon(
                                    painter =
                                    painterResource(id = R.drawable.ic_baseline_playlist_play_24),
                                    contentDescription =
                                    stringResource(id = R.string.str_playlists)
                                )
                            },
                            trailingContent = {
                                val expandedItemMenu = remember { mutableStateOf(false) }

                                Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                                    IconButton(onClick = { expandedItemMenu.value = true }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                                            contentDescription = stringResource(id = R.string.str_moreOptions)
                                        )
                                    }

                                    ItemDropDownMenu(
                                        expanded = expandedItemMenu,
                                        navController = navController
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    )
}