package com.alessiocameroni.revomusicplayer.library.playlists.libraryplaylists

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.library.components.LibraryTopBarDropDownMenu
import com.alessiocameroni.revomusicplayer.data.navigation.Screens
import com.alessiocameroni.revomusicplayer.library.playlists.libraryplaylists.components.AddPlaylistDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistsScreen(
    navController: NavController,
    navControllerBottomBar: NavHostController
) {
    val expandedMenu = remember { mutableStateOf(false) }
    val expandedNestedMenu = remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val openDialog = remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.str_playlists)) },
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

                        LibraryTopBarDropDownMenu(
                            navController = navController,
                            expandedMenu = expandedMenu,
                            expandedNestedMenu = expandedNestedMenu,
                            itemSortBy = true,
                            itemGridType = false,
                            itemOpenSpotify = false,
                            itemSettings = true
                        )
                    }
                }, scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = { openDialog.value = true }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_playlist_add_24),
                    contentDescription = stringResource(id = R.string.desc_addPlaylist),
                    modifier = Modifier.size(FloatingActionButtonDefaults.LargeIconSize),
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = { padding ->
            if(openDialog.value) {
                AddPlaylistDialog(
                    modifier = Modifier,
                    openDialog = openDialog
                )
            }

            LazyVerticalGrid(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                columns = GridCells.Fixed(1),
                contentPadding = PaddingValues(bottom = 128.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ){

            }
        }
    )
}