package com.alessiocameroni.revomusicplayer.library.albums.libraryalbums

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alessiocameroni.pixely_components.PixelyListItem
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.data.navigation.AlbumsScreens
import com.alessiocameroni.revomusicplayer.data.navigation.Screens
import com.alessiocameroni.revomusicplayer.data.viewmodels.AlbumsViewModel
import com.alessiocameroni.revomusicplayer.library.components.SmallImageContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumsScreen(
    navController: NavController,
    navControllerBottomBar: NavHostController,
    viewModel: AlbumsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val expandedMenu = remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val libraryAlbums = viewModel.libraryAlbums
    val context = LocalContext.current

    LaunchedEffect(Unit) { viewModel.initializeAlbumList(context) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.str_albums)) },
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
                    }
                }, scrollBehavior = scrollBehavior
            )
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                itemsIndexed(libraryAlbums) { _, item ->
                    Row(
                        modifier = Modifier
                            .clickable {
                                navControllerBottomBar.navigate(
                                    AlbumsScreens.AlbumViewScreen.route +
                                            "/${item.albumId}" +
                                            "/${item.albumTitle}"
                                )
                            },
                    ) {
                        PixelyListItem(
                            modifier = Modifier,
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
                                            contentDescription = stringResource(id = R.string.desc_albumImage)
                                        )
                                    },
                                )
                            },
                            trailingContent = {
                                IconButton(onClick = {  }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                                        contentDescription = stringResource(id = R.string.str_moreOptions)
                                    )
                                }
                            }
                        )

                        /*LibraryListItem(
                            modifier = Modifier,
                            navControllerBottomBar = navControllerBottomBar,
                            painterPlaceholder = painterResource(id = R.drawable.ic_outlined_album_24),
                            stringMainTitle = item.albumTitle,
                            stringSubtitle = item.artist,
                            leadingUnit = {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(item.albumCoverUri)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = stringResource(id = R.string.desc_albumImage)
                                )
                            },
                            menuEnabled = true,
                            stringViewAlbumRoute = null,
                            stringViewArtistRoute =
                                ArtistsScreens.ArtistViewScreen.route +
                                    "/${item.artistId}" +
                                    "/${item.artist}",
                            itemAddToPlaylist = false
                        )*/
                    }
                }
            }
        }
    )
}