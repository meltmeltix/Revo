package com.alessiocameroni.revomusicplayer.ui.screens.library.albumScreen

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
import androidx.navigation.NavHostController
import com.alessiocameroni.pixely_components.RoundedDropDownMenu
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.ui.navigation.NavigationScreens
import com.alessiocameroni.revomusicplayer.ui.navigation.Screens
import com.alessiocameroni.revomusicplayer.ui.screens.library.TopBarDropDownMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumTopActionBar(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior
) {
    val expandedMenu = remember { mutableStateOf(false) }

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

                TopBarDropDownMenu(
                    expanded = expandedMenu,
                    navController = navController
                )
            }
        }, scrollBehavior = scrollBehavior
    )
}

@Composable
fun AlbumItemDropDownMenu(
    expanded: MutableState<Boolean>,
    navControllerBottomBar: NavHostController,
    artistId: Long
) {
    RoundedDropDownMenu(
        expanded = expanded.value, 
        onDismissRequest = { expanded.value = false }
    ) {
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