package com.alessiocameroni.revomusicplayer.ui.screens.library.artistScreen.artistViewScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alessiocameroni.pixely_components.PixelyListItem
import com.alessiocameroni.pixely_components.PixelySectionTitle
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.data.classes.ArtistAlbumEntity
import com.alessiocameroni.revomusicplayer.data.classes.ArtistSongEntity
import com.alessiocameroni.revomusicplayer.ui.navigation.NavigationScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistViewScreen(
    artistId: Long,
    navController: NavController,
    navControllerBottomBar: NavHostController,
    viewModel: ArtistViewViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val scrollState = rememberLazyListState()
    val textVisibility = remember { derivedStateOf { scrollState.firstVisibleItemIndex > 0 } }

    val selectedSortType by remember { viewModel.sortingType }
    val selectedSortOrder by remember { viewModel.sortingOrder }
    val albumList = viewModel.albumList
    val songList = viewModel.songList

    LaunchedEffect(Unit) {
        viewModel.initializeArtistDetails(artistId)
        viewModel.initializeAlbumList(artistId)
        viewModel.initializeSongList(artistId)
    }
    listSort(songList, selectedSortOrder, selectedSortType)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ArtistViewTopActionBar(
                navController = navController,
                navControllerBottomBar = navControllerBottomBar,
                viewModel = viewModel,
                scrollBehavior = scrollBehavior,
                textVisibility = textVisibility
            )
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                state = scrollState
            ) {
                item {
                    ArtistViewHeader(viewModel = viewModel) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(viewModel.artistPictureUri.value)
                                .crossfade(true)
                                .build(),
                            contentDescription = stringResource(id = R.string.str_albumImage),
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                item {
                    AnimatedVisibility(
                        visible = albumList.isNotEmpty(),
                        enter = fadeIn()
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(2.dp),
                        ) {
                            PixelySectionTitle(
                                stringTitle = stringResource(id = R.string.str_albums),
                                horizontalContentPadding = 15.dp,
                            )

                            RowArtistAlbumList(albumList, navControllerBottomBar)
                        }
                    }
                }

                item { ArtistViewSongSectionTitle(viewModel = viewModel) }

                artistSongList(
                    songList,
                    navControllerBottomBar
                )
            }
        }
    )
}

@Composable
private fun RowArtistAlbumList(
    albums: MutableList<ArtistAlbumEntity>,
    navControllerBottomBar: NavHostController
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 5.dp)
    ) {
        itemsIndexed(items = albums) { _, item ->
            Column(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.large)
                    .clickable {
                        navControllerBottomBar.navigate(
                            NavigationScreens.AlbumViewScreen.route +
                                    "/${item.albumId}"
                        )
                    },
            ) {
                ArtistViewHorizontalListItem(
                    albumTitle = item.albumTitle
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(item.albumCoverUri)
                            .crossfade(true)
                            .build(),
                        contentDescription = stringResource(id = R.string.str_albumImage),
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

private fun LazyListScope.artistSongList(
    songs: MutableList<ArtistSongEntity>,
    navControllerBottomBar: NavHostController
) {
    itemsIndexed(items = songs) { _, item ->
        Row(
            modifier = Modifier
                .clickable {  },
        ) {
            PixelyListItem(
                headlineTextString = item.songTitle,
                largeHeadline = false,
                maxHeadlineLines = 1,
                supportingTextString = item.album,
                maxSupportingLines = 1,
                leadingContent = {
                    Text(
                        text = item.track ?: "-",
                        modifier = Modifier
                            .widthIn(max = 40.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Clip,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                trailingContent = {
                    val expandedItemMenu = remember { mutableStateOf(false) }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.fixedDuration ?: "00:00",
                            maxLines = 1,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                            IconButton(onClick = { expandedItemMenu.value = true }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                                    contentDescription = stringResource(id = R.string.str_moreOptions)
                                )
                            }

                            ArtistViewItemDropDownMenu(
                                expanded = expandedItemMenu,
                                navControllerBottomBar = navControllerBottomBar,
                                albumId = item.albumId
                            )
                        }
                    }
                }
            )
        }
    }
}

private fun listSort(
    songs: MutableList<ArtistSongEntity>,
    sortOrder: Int,
    sortType: Int
) {
    when(sortOrder) {
        0 -> {
            when(sortType) {
                0 -> songs.sortBy { it.songTitle }
                1 -> songs.sortBy { it.track }
                2 -> songs.sortBy { it.duration }
                3 -> songs.sortBy { it.album }
            }
        }
        1 -> {
            when(sortType) {
                0 -> songs.sortByDescending { it.songTitle }
                1 -> songs.sortByDescending { it.track }
                2 -> songs.sortByDescending { it.duration }
                3 -> songs.sortByDescending { it.album }
            }
        }
    }
}