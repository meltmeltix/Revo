package com.alessiocameroni.revomusicplayer.library.artists.artistsscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.library.components.LibraryDropDownMenu
import com.alessiocameroni.revomusicplayer.library.components.LibraryNoMenuListItem
import com.alessiocameroni.revomusicplayer.library.data.LibraryItemData
import com.alessiocameroni.revomusicplayer.navigation.ArtistsScreens
import com.alessiocameroni.revomusicplayer.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistsScreen(navController: NavController, navControllerBottomBar: NavHostController) {
    val expanded = remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val items by remember {
        mutableStateOf(
            (1..20).map {
                LibraryItemData(
                    stringTitle = "Artist Name",
                    stringSubtitle = "20 songs - 20 albums"
                )
            }
        )
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.str_artists)) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Screens.SearchScreen.route) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_search_24),
                            contentDescription = stringResource(id = R.string.desc_searchmenu)
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
            LazyVerticalGrid(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                columns = GridCells.Fixed(1),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(items.size) { i ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(22.dp))
                            .clickable {
                                navControllerBottomBar.navigate(ArtistsScreens.ArtistViewScreen.route)
                            },
                    ) {
                        LibraryNoMenuListItem(
                            modifier = Modifier
                                .fillMaxWidth(),
                            unitAlbumImage = null,
                            stringTitleItem = items[i].stringTitle,
                            stringSubtitleItem = items[i].stringSubtitle
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
                        LibraryNoMenuLargeGridItem(
                            modifier = Modifier
                                .fillMaxWidth(),
                            unitAlbumImage = null,
                            stringTitleItem = items[i].stringTitle,
                            stringSubtitleItem = items[i].stringSubtitle
                        )
                    }
                }
            }*/
        }
    )
}