package com.meltix.revo.ui.screens.library.artistScreen.artistViewScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.meltix.revo.data.classes.ContentState
import com.meltix.revo.data.classes.artist.ArtistDetails

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistViewScreen(
    artistId: Long,
    rootNavController: NavController,
    libraryNavController: NavController,
    viewModel: ArtistViewViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val scrollState = rememberLazyListState()
    val textVisibility = remember { derivedStateOf { scrollState.firstVisibleItemIndex > 0 } }
    val contentState by viewModel.contentState.collectAsStateWithLifecycle(ContentState.LOADING)
    val artistDetails by viewModel.artistDetails.collectAsStateWithLifecycle(
        ArtistDetails("Unknown Artist", 0, 0)
    )
    val artistCover by viewModel.artistCover.collectAsStateWithLifecycle(null)
    val albumList by viewModel.albums.collectAsStateWithLifecycle(emptyList())
    val songList by viewModel.songs.collectAsStateWithLifecycle(emptyList())

    LaunchedEffect(Unit) { viewModel.initializeArtistData(artistId) }

    
}

/*
@Composable
private fun RowArtistAlbumList(
    albums: List<ArtistAlbum>,
    navControllerBottomBar: NavController
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
                            DetailsScreens.AlbumDetails.route + "/${item.albumId}"
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
    songs: List<ArtistSong>,
    navControllerBottomBar: NavController
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
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 14.sp,
                            maxLines = 1,
                            style = MaterialTheme.typography.bodyMedium,
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
}*/
