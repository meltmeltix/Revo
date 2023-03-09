package com.alessiocameroni.revomusicplayer.ui.screens.library.albumScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
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
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alessiocameroni.pixely_components.PixelyListItem
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.data.classes.Album
import com.alessiocameroni.revomusicplayer.ui.components.SmallImageContainer
import com.alessiocameroni.revomusicplayer.ui.navigation.NavigationScreens
import androidx.compose.runtime.getValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumsScreen(
    navController: NavController,
    navControllerBottomBar: NavHostController,
    viewModel: AlbumViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val selectedSortType by remember { viewModel.sortingType }
    val selectedSortOrder by remember { viewModel.sortingOrder }
    val albumList = viewModel.libraryAlbums

    listSort(albumList, selectedSortOrder, selectedSortType)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { AlbumTopActionBar(navController, scrollBehavior, viewModel) },
        content = { padding ->
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

private fun LazyListScope.albumList(
    albums: SnapshotStateList<Album>,
    navControllerBottomBar: NavHostController
) {
    itemsIndexed(albums) { _, item ->
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

private fun listSort(
    albums: SnapshotStateList<Album>,
    sortOrder: Int,
    sortType: Int
) {
    when(sortOrder) {
        0 -> {
            when(sortType) {
                0 -> albums.sortBy { it.albumTitle }
                1 -> albums.sortBy { it.artist }
                2 -> albums.sortBy { it.year }
                3 -> albums.sortBy { it.numberOfSongs }
            }
        }
        1 -> {
            when(sortType) {
                0 -> albums.sortByDescending { it.albumTitle }
                1 -> albums.sortByDescending { it.artist }
                2 -> albums.sortByDescending { it.year }
                3 -> albums.sortByDescending { it.numberOfSongs }
            }
        }
    }
}