package com.meltix.revo.ui.screens.library.albumScreen.albumDetailsScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.meltix.pixely_components.PixelyListItem
import com.meltix.revo.R
import com.meltix.revo.data.classes.ContentState
import com.meltix.revo.data.classes.album.AlbumDetails
import com.meltix.revo.data.classes.album.AlbumSong
import com.meltix.revo.data.classes.album.HeaderLayout
import com.meltix.revo.ui.components.ContentSelector
import com.meltix.revo.ui.components.contentModifier
import com.meltix.revo.util.functions.findActivity


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun AlbumViewScreen(
    albumId: Long,
    rootNavController: NavController,
    libraryNavController: NavController,
    viewModel: AlbumDetailsViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowClass = calculateWindowSizeClass(activity)
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val scrollState = rememberLazyListState()
    val firstVisibleItem = remember { derivedStateOf { scrollState.firstVisibleItemIndex > 0 } }
    val contentState by viewModel.contentState.collectAsStateWithLifecycle(ContentState.LOADING)
    val headerLayout by viewModel.headerLayout.collectAsStateWithLifecycle(HeaderLayout.REVO)
    val contentScale = when(headerLayout) {
        HeaderLayout.REVO -> ContentScale.FillBounds
        HeaderLayout.FRUIT_MUSIC -> ContentScale.FillHeight
        HeaderLayout.MINIMAL -> ContentScale.FillWidth
    }
    val albumDetails by viewModel.albumDetails.collectAsStateWithLifecycle(
        AlbumDetails("Unknown Album", 0, "Unknown Artist", null)
    )
    val songList by viewModel.songs.collectAsStateWithLifecycle(emptyList())

    LaunchedEffect(Unit) { viewModel.initializeAlbumData(albumId) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        content = { padding ->
            Box(modifier = Modifier.fillMaxSize()) {
                // TODO: Improve content loading units
                ContentSelector(
                    state = contentState,
                    loadingUnit = {
                        // TODO: Add Loading Unit
                    },
                    failedUnit = {
                        // TODO: Add Failed Unit
                    },
                    contentUnit = {
                        LazyColumn(
                            modifier = Modifier.contentModifier(windowClass, padding),
                            verticalArrangement = Arrangement.spacedBy(2.dp),
                            state = scrollState
                        ) {

                        }
                    }
                )

                AlbumDetailsTopActionBar(
                    albumDetails = albumDetails,
                    rootNavController = rootNavController,
                    libraryNavController = libraryNavController,
                    viewModel = viewModel,
                    firstVisibleItem = firstVisibleItem
                )
            }
        }
    )
}

/*item {
    AlbumViewHeader(
        layout = headerLayout,
        albumDetails = albumDetails,
        viewModel = viewModel,
        libraryNavController = libraryNavController,
        leadingUnit = {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(albumDetails.coverUri)
                    .crossfade(true)
                    .build(),
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = contentScale,
                contentDescription = stringResource(id = R.string.str_albumImage),
            )
        }
    )
}

item {
    PixelySectionTitle(
        stringTitle = stringResource(id = R.string.str_songs),
        horizontalContentPadding = 15.dp
    )
}

albumSongsList(songList)*/

private fun LazyListScope.albumSongsList(songs: List<AlbumSong>) {
    itemsIndexed(items = songs) { key, item ->
        key(key) {
            Row(modifier = Modifier.clickable {  }) {
                PixelyListItem(
                    headlineTextString = item.songTitle,
                    largeHeadline = false,
                    maxHeadlineLines = 1,
                    leadingContent = {
                        Text(
                            text = if(item.track == 0) "-" else item.track.toString(),
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

                                AlbumDetailsItemDropDownMenu(
                                    expanded = expandedItemMenu
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}