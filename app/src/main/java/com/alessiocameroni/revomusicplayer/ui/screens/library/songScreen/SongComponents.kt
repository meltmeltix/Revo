package com.alessiocameroni.revomusicplayer.ui.screens.library.songScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.alessiocameroni.pixely_components.RoundedDropDownMenu
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.ui.navigation.NavigationScreens
import com.alessiocameroni.revomusicplayer.ui.navigation.Screens

/**
 * Scaffold components
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongTopActionBar(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    val expandedMenu = remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = stringResource(id = R.string.str_songs)) },
        navigationIcon = {
            IconButton(onClick = { navController.navigate(Screens.SearchScreen.route) }) {
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

                SongTopBarDropDownMenu(
                    expandedMenu,
                    navController
                )
            }
        },
        scrollBehavior = scrollBehavior,
    )
}

@Composable
fun SongTopBarDropDownMenu(
    expanded: MutableState<Boolean>, 
    navController: NavController
) {
    RoundedDropDownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false }
    ) {
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.str_sortBy)) }, 
            onClick = { /*TODO*/ },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_sort_24), 
                    contentDescription = stringResource(id = R.string.desc_sortBy)
                )
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_right_24), 
                    contentDescription = stringResource(id = R.string.str_moreOptions)
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

/**
 * Screen components
 */
@Composable
fun SongItemDropDownMenu(
    expanded: MutableState<Boolean>,
    navControllerBottomBar: NavController,
    albumId: Long,
    artistId: Long,
) {
    RoundedDropDownMenu(
        expanded = expanded.value, 
        onDismissRequest = { expanded.value = false }
    ) {
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.str_goToAlbum)) },
            onClick = {
                navControllerBottomBar.navigate(
                    NavigationScreens.AlbumViewScreen.route + "/$albumId"
                )
                expanded.value = false
            },
            leadingIcon = { 
                Icon(
                    painter = painterResource(id = R.drawable.ic_outlined_go_to_album_24), 
                    contentDescription = stringResource(id = R.string.str_goToAlbum)
                )
            }
        )

        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.str_goToArtist)) },
            onClick = {
                navControllerBottomBar.navigate(
                    NavigationScreens.ArtistViewScreen.route + "/$artistId"
                )
                expanded.value = false
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_outlined_go_to_artist_24),
                    contentDescription = stringResource(id = R.string.str_goToArtist)
                )
            }
        )
    }
    
    
}