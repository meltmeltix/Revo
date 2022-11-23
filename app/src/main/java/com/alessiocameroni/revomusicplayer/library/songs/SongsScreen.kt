package com.alessiocameroni.revomusicplayer.library.songs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.data.modifiers.clickableRowItem
import com.alessiocameroni.revomusicplayer.data.navigation.Screens
import com.alessiocameroni.revomusicplayer.library.components.LibraryDropDownMenu
import com.alessiocameroni.revomusicplayer.library.components.LibraryListItem
import com.alessiocameroni.revomusicplayer.library.components.NestedGridTypeMenu
import com.alessiocameroni.revomusicplayer.library.songs.viewmodels.LibrarySongsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongsScreen(
    navController: NavController,
    viewModel: LibrarySongsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val expandedMenu = remember { mutableStateOf(false) }
    val expandedNestedMenu = remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val librarySongs = viewModel.librarySongs

    val context = LocalContext.current

    LaunchedEffect(Unit) { viewModel.initializeListIfNeeded(context) }

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
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
                        IconButton(onClick = { expandedMenu.value = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                                contentDescription = stringResource(id = R.string.str_settings)
                            )
                        }

                        LibraryDropDownMenu(
                            navController = navController,
                            expandedMenu = expandedMenu,
                            expandedNestedMenu = expandedNestedMenu,
                            itemSortBy = true,
                            itemGridType = true,
                            itemOpenSpotify = false,
                            itemSettings = true
                        )

                        NestedGridTypeMenu(
                            expandedNestedMenu = expandedNestedMenu
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
                itemsIndexed(items = librarySongs) { _, item ->
                    Row(
                        modifier = Modifier
                            .clickableRowItem()
                            .clickable { },
                    ) {
                        LibraryListItem(
                            modifier = Modifier
                                .fillMaxWidth(),
                            painterIcon = painterResource(id = R.drawable.ic_baseline_music_note_24),
                            unitAlbumImage = {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(item.albumCoverUri)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = stringResource(id = R.string.desc_albumImage)
                                )
                            },
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
    )
}