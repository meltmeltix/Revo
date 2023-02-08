package com.alessiocameroni.revomusicplayer.ui.screens.library.artists.libraryartists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
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
import com.alessiocameroni.pixely_components.PixelyListItem
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.data.navigation.ArtistsScreens
import com.alessiocameroni.revomusicplayer.data.navigation.Screens
import com.alessiocameroni.revomusicplayer.data.viewmodels.ArtistsViewModel
import com.alessiocameroni.revomusicplayer.data.components.SmallImageContainer
import com.alessiocameroni.revomusicplayer.ui.screens.library.components.TopBarDropDownMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistsScreen(
    navController: NavController,
    navControllerBottomBar: NavHostController,
    viewModel: ArtistsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val expandedMenu = remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val libraryArtists = viewModel.libraryArtists
    val context = LocalContext.current

    LaunchedEffect(Unit) { viewModel.initializeArtistList(context) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.str_artists)) },
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
                itemsIndexed(libraryArtists) { _, item ->
                    Row(
                        modifier = Modifier
                            .clickable {
                                navControllerBottomBar.navigate(
                                    ArtistsScreens.ArtistViewScreen.route
                                )
                            },
                    ) {
                        PixelyListItem(
                            modifier = Modifier,
                            headlineTextString = item.artist,
                            largeHeadline = false,
                            maxHeadlineLines = 1,
                            supportingTextString = "Placeholder",
                            maxSupportingLines = 1,
                            leadingContent = {
                                SmallImageContainer(
                                    modifier = Modifier
                                        .padding(horizontal = 5.dp)
                                        .clip(CircleShape),
                                    painterPlaceholder =
                                        painterResource(id = R.drawable.ic_outlined_artist_24),
                                    leadingUnit = {  }
                                )
                            }
                        )
                    }
                }
            }
        }
    )
}