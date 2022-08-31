package com.alessiocameroni.revomusicplayer.library.spotify

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
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.library.components.LibraryDropDownMenu
import com.alessiocameroni.revomusicplayer.library.components.OneColumnListItem
import com.alessiocameroni.revomusicplayer.library.data.LibraryItemData
import com.alessiocameroni.revomusicplayer.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpotifyFavoritesScreen(navController: NavController) {
    val numColumns by remember { mutableStateOf(1) }
    val expanded = remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val items by remember {
        mutableStateOf(
            (1..20).map {
                LibraryItemData(
                    stringTitle = "Song Title",
                    stringSubtitle = "Song Artist"
                )
            }
        )
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SmallTopAppBar(

                title = { Text(text = stringResource(id = R.string.str_spoitfy)) },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate(Screens.SearchScreen.route) }
                    ) {
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
                            itemOpenSpotify = true,
                            itemSettings = true
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = { padding ->
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize(),
                columns = GridCells.Fixed(numColumns),
                contentPadding = padding,
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ){
                items(items.size) { i ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(22.dp))
                            .clickable { },
                    ) {
                        OneColumnListItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(84.dp),
                            unitAlbumImage = null,
                            stringTitleItem = items[i].stringTitle,
                            stringSubtitleItem = items[i].stringSubtitle,
                            unitMenuItems = {
                                DropdownMenuItem(
                                    text = {
                                        Text(text = stringResource(id = R.string.str_addtoplaylist))
                                    },
                                    onClick = { /*TODO*/ },
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_baseline_playlist_add_24),
                                            contentDescription = stringResource(id = R.string.desc_addtoplaylist)
                                        )
                                    }
                                )
                            }
                        )
                    }
                }
            }
        }
    )
}