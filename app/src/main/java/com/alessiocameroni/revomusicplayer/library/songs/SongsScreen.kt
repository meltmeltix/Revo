package com.alessiocameroni.revomusicplayer.library.songs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.library.main.behavior.LibrarySongsViewModel
import com.alessiocameroni.revomusicplayer.library.main.components.LibraryDropDownMenu
import com.alessiocameroni.revomusicplayer.library.main.components.LibraryListItem
import com.alessiocameroni.revomusicplayer.library.main.components.NoSongsScreen
import com.alessiocameroni.revomusicplayer.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongsScreen(
    navController: NavController,
    viewModel: LibrarySongsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val expanded = remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val librarySongs = viewModel.librarySongs
    val context = LocalContext.current

    LaunchedEffect(Unit) { viewModel.initializeListIfNeeded(context) }

    if(librarySongs.size == 0) {
        NoSongsScreen(navController = navController, expanded = expanded)
    } else {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.str_songs)) },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate(Screens.SearchScreen.route) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_search_24),
                                contentDescription = stringResource(id = R.string.desc_searchMenu)
                            )
                        }
                    },
                    actions = {
                        Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                            IconButton(onClick = { expanded.value = true }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                                    contentDescription = stringResource(id = R.string.str_settings)
                                )
                            }

                            LibraryDropDownMenu(
                                navController = navController,
                                expanded = expanded,
                                itemSortBy = true,
                                itemGridType = true,
                                itemOpenSpotify = false,
                                itemSettings = true
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
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                itemsIndexed(items = librarySongs) { i, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(22.dp))
                            .clickable { },
                    ) {
                        LibraryListItem(
                            modifier = Modifier
                                .fillMaxWidth(),
                            painterIcon = painterResource(id = R.drawable.ic_baseline_music_note_24),
                            unitAlbumImage = {  },
                            stringTitleItem = item.songTitle,
                            stringSubtitleItem = item.artist,
                            unitMenuItems = {
                                DropdownMenuItem(
                                    text = {
                                        Text(text = stringResource(id = R.string.str_addToPlaylist))
                                    },
                                    onClick = { },
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_baseline_playlist_add_24),
                                            contentDescription = stringResource(id = R.string.str_addToPlaylist)
                                        )
                                    }
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}