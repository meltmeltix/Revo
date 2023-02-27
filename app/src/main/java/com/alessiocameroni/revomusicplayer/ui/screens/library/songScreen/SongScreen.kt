package com.alessiocameroni.revomusicplayer.ui.screens.library.songScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alessiocameroni.pixely_components.PixelyListItem
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.data.classes.SongData
import com.alessiocameroni.revomusicplayer.ui.components.SmallImageContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongsScreen(
    navController: NavController,
    navControllerBottomBar: NavController,
    viewModel: SongViewModel = hiltViewModel(),
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val librarySongs = viewModel.librarySongs
    val context = LocalContext.current

    val selectedSortType by remember { viewModel.sortingType }
    val selectedSortOrder by remember { viewModel.sortingOrder }

    LaunchedEffect(Unit) { viewModel.initializeSongList(context) }

    listSort(
        librarySongs,
        selectedSortOrder,
        selectedSortType,
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SongTopActionBar(
                navController,
                scrollBehavior,
                viewModel,
            )
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                contentPadding = PaddingValues(bottom = 70.dp)
            ) {
                itemsIndexed(items = librarySongs) { key, item ->
                    key(key) {
                        Row(modifier = Modifier.clickable{ }) {
                            PixelyListItem(
                                headlineTextString = item.songTitle,
                                largeHeadline = false,
                                maxHeadlineLines = 1,
                                supportingTextString = item.artist,
                                maxSupportingLines = 1,
                                leadingContent = {
                                    SmallImageContainer(
                                        modifier = Modifier.padding(horizontal = 5.dp),
                                        painterPlaceholder =
                                        painterResource(id = R.drawable.ic_baseline_music_note_24),
                                        leadingUnit = {
                                            AsyncImage(
                                                model = ImageRequest.Builder(LocalContext.current)
                                                    .data(item.albumCoverUri)
                                                    .crossfade(true)
                                                    .build(),
                                                contentDescription = stringResource(id = R.string.desc_albumImage)
                                            )
                                        }
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

                                        SongItemDropDownMenu(
                                            expanded = expandedItemMenu,
                                            navControllerBottomBar = navControllerBottomBar,
                                            albumId = item.albumId,
                                            artistId = item.artistId
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}

private fun listSort(
    librarySongs: SnapshotStateList<SongData>,
    selectedSortOrder: Int,
    selectedSortType: Int
) {
    when(selectedSortOrder) {
        0 -> {
            when(selectedSortType) {
                0 -> librarySongs.sortBy { it.songTitle }
                1 -> librarySongs.sortBy { it.artist }
                2 -> librarySongs.sortBy { it.album }
                3 -> librarySongs.sortBy { it.duration }
                4 -> librarySongs.sortBy { it.dateAdded }
            }
        }
        1 -> {
            when(selectedSortType) {
                0 -> librarySongs.sortByDescending { it.songTitle }
                1 -> librarySongs.sortByDescending { it.artist }
                2 -> librarySongs.sortByDescending { it.album }
                3 -> librarySongs.sortByDescending { it.duration }
                4 -> librarySongs.sortByDescending { it.dateAdded }
            }
        }
    }
}