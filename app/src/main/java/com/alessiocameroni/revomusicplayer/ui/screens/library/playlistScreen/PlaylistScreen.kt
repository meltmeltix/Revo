package com.alessiocameroni.revomusicplayer.ui.screens.library.playlistScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.alessiocameroni.pixely_components.PixelyListItem
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.ui.components.SmallImageContainer
import com.alessiocameroni.revomusicplayer.ui.navigation.NavigationScreens.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistsScreen(
    navController: NavController,
    navControllerBottomBar: NavHostController,
    viewModel: PlaylistViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val openDialog = remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { PlaylistTopActionbar(navController, scrollBehavior) },
        floatingActionButton = { AddPlaylistFAB(openDialog) },
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
                verticalArrangement = Arrangement.spacedBy(2.dp),
                contentPadding = PaddingValues(bottom = 128.dp + 70.dp),
            ) {
                items(2) {
                    Row(modifier = Modifier
                        .clickable { navControllerBottomBar.navigate(PlaylistViewScreen.route) }
                    ) {
                        PixelyListItem(
                            headlineTextString = "Placeholder",
                            largeHeadline = false,
                            maxHeadlineLines = 1,
                            supportingTextString = "Placeholder",
                            maxSupportingLines = 1,
                            leadingContent = {
                                SmallImageContainer(
                                    modifier = Modifier
                                        .padding(horizontal = 5.dp)
                                        .clip(CircleShape),
                                    painterPlaceholder =
                                        painterResource(id = R.drawable.ic_baseline_playlist_play_24)
                                ) {

                                }

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

                                    PlaylistItemDropDownMenu(expanded = expandedItemMenu)
                                }
                            }
                        )
                    }
                }
            }
        }
    )
}