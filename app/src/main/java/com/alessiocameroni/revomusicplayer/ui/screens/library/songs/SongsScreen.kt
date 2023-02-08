package com.alessiocameroni.revomusicplayer.ui.screens.library.songs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alessiocameroni.pixely_components.PixelyListItem
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.data.navigation.Screens
import com.alessiocameroni.revomusicplayer.data.viewmodels.SongsViewModel
import com.alessiocameroni.revomusicplayer.ui.screens.library.components.ItemDropDownMenu
import com.alessiocameroni.revomusicplayer.data.components.SmallImageContainer
import com.alessiocameroni.revomusicplayer.ui.screens.library.components.TopBarDropDownMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongsScreen(
    navController: NavController,
    viewModel: SongsViewModel = viewModel(),
    navControllerBottomBar: NavController
) {
    val expandedMenu = remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val librarySongs = viewModel.librarySongs
    val context = LocalContext.current

    LaunchedEffect(Unit) { viewModel.initializeSongList(context) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
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

                        TopBarDropDownMenu(
                            expanded = expandedMenu,
                            navController = navController
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                itemsIndexed(items = librarySongs) { _, item ->
                    Row(
                        modifier = Modifier
                            .clickable { },
                    ) {
                        PixelyListItem(
                            modifier = Modifier,
                            headlineTextString = item.songTitle,
                            largeHeadline = false,
                            maxHeadlineLines = 1,
                            supportingTextString = item.artist,
                            maxSupportingLines = 1,
                            leadingContent = {
                                SmallImageContainer(
                                    modifier = Modifier.padding(horizontal = 5.dp),
                                    painterPlaceholder =
                                        painterResource(id = R.drawable.ic_baseline_music_note_24),
                                    leadingUnit = {
                                        AsyncImage(
                                            model = ImageRequest.Builder(LocalContext.current)
                                                .data(item.albumCoverUri)
                                                .crossfade(true)
                                                .build(),
                                            contentDescription = stringResource(id = R.string.desc_albumImage)
                                        )
                                    }
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