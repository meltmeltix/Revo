package com.alessiocameroni.revomusicplayer.library.playlists.playlistview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.library.components.LibraryListItem
import com.alessiocameroni.revomusicplayer.library.data.LibraryItemData
import com.alessiocameroni.revomusicplayer.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistViewScreen(
    navController: NavController,
    navControllerBottomBar: NavHostController,
) {
    val expanded = remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

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
        topBar = {
            SmallTopAppBar(
                title = { Text(text = "Playlist Title")},
                navigationIcon = {
                    IconButton(
                        onClick = { navControllerBottomBar.navigateUp() }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                            contentDescription = stringResource(id = R.string.desc_back)
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

                        PlaylistViewDropDownMenu(
                            navController = navController,
                            expanded = expanded
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = { padding ->
            LazyVerticalGrid(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                columns = GridCells.Fixed(1),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ){
                items(items.size) { i ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(22.dp))
                            .clickable { },
                    ) {
                        LibraryListItem(
                            modifier = Modifier
                                .fillMaxWidth(),
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

@Composable
fun PlaylistViewDropDownMenu(
    navController: NavController,
    expanded: MutableState<Boolean>,
) {
    MaterialTheme(shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(16.dp))) {
        DropdownMenu(
            modifier = Modifier.width(180.dp),
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            var expandedGrid by remember { mutableStateOf(false) }

            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.str_sortby)) },
                onClick = {   },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_sort_24),
                        contentDescription = stringResource(id = R.string.desc_sortyby)
                    )
                }
            )

            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.str_gridtype)) },
                onClick = { expandedGrid = true },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_grid_on_24),
                        contentDescription = stringResource(id = R.string.desc_gridtype)
                    )
                }
            )

            MaterialTheme(shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(16.dp))) {

            }

            Divider()

            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.str_renameplaylist)) },
                onClick = {  },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outlined_drive_file_rename_24),
                        contentDescription = stringResource(id = R.string.desc_renameplaylist)
                    )
                }
            )

            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.str_deleteplaylist)) },
                onClick = {  },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_playlist_remove_24),
                        contentDescription = stringResource(id = R.string.desc_deleteplaylist)
                    )
                }
            )

            Divider()

            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.str_settings)) },
                onClick = {
                    navController.navigate(Screens.SettingsScreen.route)
                    expanded.value = false
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outlined_settings_24),
                        contentDescription = stringResource(id = R.string.desc_settings)
                    )
                }
            )
        }
    }
}