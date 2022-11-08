package com.alessiocameroni.revomusicplayer.library.albums.libraryalbums

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.library.main.behavior.LibraryAlbumsViewModel
import com.alessiocameroni.revomusicplayer.library.main.components.LibraryDropDownMenu
import com.alessiocameroni.revomusicplayer.library.main.components.LibraryNoMenuListItem
import com.alessiocameroni.revomusicplayer.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumsScreen(
    navController: NavController,
    navControllerBottomBar: NavHostController,
    viewModel: LibraryAlbumsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val expanded = remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val libraryAlbums = viewModel.libraryAlbums
    val context = LocalContext.current

    LaunchedEffect(Unit) { viewModel.initializeListIfNeeded(context) }

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
                }, scrollBehavior = scrollBehavior
            )
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                itemsIndexed(libraryAlbums) { index, item ->
                    /*LaunchedEffect(Unit) {
                        viewModel.loadBitmapIfNeeded(context, index)
                    }*/

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(22.dp))
                            .clickable { },
                    ) {
                        LibraryNoMenuListItem(
                            modifier = Modifier
                                .fillMaxWidth(),
                            unitAlbumImage = {
                                /*AsyncImage(
                                    model = item.albumCover,
                                    contentDescription = "Image"
                                )*/
                            },
                            stringTitleItem = item.albumTitle,
                            stringSubtitleItem = item.artist
                        )
                    }
                }
            }

            /*LazyVerticalGrid(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                columns = GridCells.Adaptive(190.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ){
                items(items.size) { i ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(22.dp))
                            .clickable { },
                    ) {
                        LibraryLargeGridItem(
                            modifier = Modifier
                                .fillMaxWidth(),
                            unitAlbumImage = null,
                            stringTitleItem = items[i].stringTitle,
                            stringSubtitleItem = items[i].stringSubtitle,
                            unitMenuItems = null
                        )
                    }
                }
            }*/
        }
    )
}