package com.alessiocameroni.revomusicplayer.ui.screens.library.albumScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alessiocameroni.pixely_components.PixelyListItem
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.data.classes.ContentState
import com.alessiocameroni.revomusicplayer.data.classes.album.Album
import com.alessiocameroni.revomusicplayer.ui.components.ContentSelector
import com.alessiocameroni.revomusicplayer.ui.components.LoadingContent
import com.alessiocameroni.revomusicplayer.ui.components.NoContentMessage
import com.alessiocameroni.revomusicplayer.ui.components.SmallImageContainer
import com.alessiocameroni.revomusicplayer.ui.navigation.NavigationScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumsScreen(
    navController: NavController,
    navControllerBottomBar: NavHostController,
    viewModel: AlbumViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val contentState by viewModel.contentState.collectAsStateWithLifecycle(ContentState.LOADING)
    val albumList by viewModel.albums.collectAsStateWithLifecycle(emptyList())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { AlbumTopActionBar(navController, scrollBehavior, viewModel) },
        content = { padding ->
            ContentSelector(
                state = contentState,
                loadingUnit = {
                    LoadingContent(
                        padding = padding,
                        headlineString = stringResource(id = R.string.str_loadingAlbums)
                    )
                },
                failedUnit = {
                    NoContentMessage(
                        padding = padding,
                        leadingIcon = painterResource(id = R.drawable.ic_outlined_no_album_24),
                        headlineString = stringResource(id = R.string.str_tooQuietAlbums),
                        infoString = stringResource(id = R.string.info_tooQuietAlbums)
                    )
                },
                contentUnit = {
                    LazyColumn(
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        contentPadding = PaddingValues(bottom = 70.dp)
                    ) { albumList(albumList, navControllerBottomBar) }
                }
            )
        }
    )
}

private fun LazyListScope.albumList(
    albums: List<Album>,
    navControllerBottomBar: NavHostController
) {
    itemsIndexed(albums) { key, item ->
        key(key) {
            Row(
                modifier = Modifier
                    .clickable {
                        navControllerBottomBar.navigate(
                            NavigationScreens.AlbumViewScreen.route +
                                    "/${item.albumId}"
                        )
                    },
            ) {
                PixelyListItem(
                    headlineTextString = item.albumTitle,
                    largeHeadline = false,
                    maxHeadlineLines = 1,
                    supportingTextString = item.artist,
                    maxSupportingLines = 1,
                    leadingContent = {
                        SmallImageContainer(
                            modifier = Modifier.padding(horizontal = 5.dp),
                            painterPlaceholder =
                            painterResource(id = R.drawable.ic_outlined_album_24),
                            leadingUnit = {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(item.albumCoverUri)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = stringResource(id = R.string.str_albumImage)
                                )
                            },
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

                            AlbumItemDropDownMenu(
                                expanded = expandedItemMenu,
                                navControllerBottomBar = navControllerBottomBar,
                                artistId = item.artistId
                            )
                        }
                    }
                )
            }
        }
    }
}