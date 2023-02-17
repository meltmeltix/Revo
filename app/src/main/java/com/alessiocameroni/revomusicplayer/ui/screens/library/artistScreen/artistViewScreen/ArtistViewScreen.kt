package com.alessiocameroni.revomusicplayer.ui.screens.library.artistScreen.artistViewScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alessiocameroni.pixely_components.PixelyListItem
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.data.classes.ArtistAlbumData
import com.alessiocameroni.revomusicplayer.ui.components.SmallImageContainer
import com.alessiocameroni.revomusicplayer.ui.navigation.NavigationScreens
import com.alessiocameroni.revomusicplayer.ui.screens.library.ItemDropDownMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistViewScreen(
    artistId: Long,
    navController: NavController,
    navControllerBottomBar: NavHostController,
    viewModel: ArtistViewViewModel = viewModel()
) {
    val content = LocalContext.current
    val expanded = remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val scrollState = rememberLazyListState()
    val textVisibility =
        remember { derivedStateOf { scrollState.firstVisibleItemIndex > 0 } }
    val artistAlbums = viewModel.artistAlbums

    LaunchedEffect(Unit) {
        viewModel.initializeArtistLists(content, artistId)
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ArtistViewTopActionBar(
                modifier = Modifier,
                navControllerBottomBar = navControllerBottomBar,
                expanded = expanded,
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
                    ArtistViewHeader()
                }

                item {
                    ArtistViewSectionTitle(
                        stringTitle = stringResource(id = R.string.str_albums)
                    )
                }

                item {
                    RowAlbumList(
                        artistAlbums,
                        navControllerBottomBar
                    )
                }
                
                item {
                    ArtistViewSectionTitle(
                        stringTitle = stringResource(id = R.string.str_songs)
                    )
                }

                items(20) {
                    PixelyListItem(
                        headlineTextString = "songTitle",
                        largeHeadline = false,
                        maxHeadlineLines = 1,
                        supportingTextString = "artist",
                        maxSupportingLines = 1,
                        leadingContent = {
                            SmallImageContainer(
                                modifier = Modifier.padding(horizontal = 5.dp),
                                painterPlaceholder =
                                painterResource(id = R.drawable.ic_baseline_music_note_24),
                                leadingUnit = {
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
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
    )
}

@Composable
fun RowAlbumList(
    artistAlbums: MutableList<ArtistAlbumData>,
    navControllerBottomBar: NavHostController
) {
    LazyRow(
        contentPadding = PaddingValues(start = 5.dp)
    ) {
        itemsIndexed(items = artistAlbums) { _, item ->
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
                        contentDescription = stringResource(id = R.string.desc_albumImage),
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

/*
private fun LazyListScope.albumList() {
    items(20) {
        ArtistViewHorizontalListItem()
    }
}*/
