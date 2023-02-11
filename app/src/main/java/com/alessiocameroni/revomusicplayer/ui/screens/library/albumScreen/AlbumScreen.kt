package com.alessiocameroni.revomusicplayer.ui.screens.library.albumScreen

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
import com.alessiocameroni.revomusicplayer.ui.navigation.AlbumsScreens
import com.alessiocameroni.revomusicplayer.ui.navigation.Screens
import com.alessiocameroni.revomusicplayer.ui.screens.library.ItemDropDownMenu
import com.alessiocameroni.revomusicplayer.ui.components.SmallImageContainer
import com.alessiocameroni.revomusicplayer.ui.screens.library.TopBarDropDownMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumsScreen(
    navController: NavController,
    navControllerBottomBar: NavHostController,
    viewModel: AlbumViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val expandedMenu = remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
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

                        TopBarDropDownMenu(
                            expanded = expandedMenu,
                            navController = navController
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
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                itemsIndexed(libraryAlbums) { _, item ->
                    Row(
                        modifier = Modifier
                            .clickable {
                                navControllerBottomBar.navigate(
                                    AlbumsScreens.AlbumViewScreen.route
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
                                            contentDescription = stringResource(id = R.string.desc_albumImage)
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

                                    ItemDropDownMenu(
                                        expanded = expandedItemMenu,
                                        navController = navController
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    )
}