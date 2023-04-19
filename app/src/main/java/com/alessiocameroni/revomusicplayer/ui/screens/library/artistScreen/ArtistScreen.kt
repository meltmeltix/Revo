package com.alessiocameroni.revomusicplayer.ui.screens.library.artistScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alessiocameroni.pixely_components.PixelyListItem
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.data.classes.ContentState
import com.alessiocameroni.revomusicplayer.data.classes.artist.Artist
import com.alessiocameroni.revomusicplayer.ui.components.*
import com.alessiocameroni.revomusicplayer.ui.navigation.NavigationScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistsScreen(
    navController: NavController,
    navControllerBottomBar: NavHostController,
    viewModel: ArtistViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val contentState by viewModel.contentState.collectAsStateWithLifecycle(ContentState.LOADING)
    val artistList by viewModel.artists.collectAsStateWithLifecycle(emptyList())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { ArtistTopActionBar(navController, scrollBehavior, viewModel) },
        content = { padding ->
            ContentSelector(
                state = contentState,
                loadingUnit = {
                    LoadingContent(
                        padding = padding,
                        headlineString = stringResource(id = R.string.str_loadingArtists)
                    )
                },
                failedUnit = {
                    NoContentMessage(
                        padding = padding,
                        leadingIcon = painterResource(id = R.drawable.outlined_person_off_24),
                        headlineString = stringResource(id = R.string.str_tooQuietArtists),
                        infoString = stringResource(id = R.string.info_tooQuietArtists)
                    )
                },
                contentUnit = {
                    LazyColumn(
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        contentPadding = PaddingValues(bottom = 70.dp)
                    ) { artistList(artistList, navControllerBottomBar) }
                }
            )
        }
    )
}

private fun LazyListScope.artistList(
    artists: List<Artist>,
    navControllerBottomBar: NavHostController
) {
    itemsIndexed(artists) { _, item ->
        Row(
            modifier = Modifier
                .clickable {
                    navControllerBottomBar.navigate(
                        NavigationScreens.ArtistViewScreen.route +
                                "/${item.artistId}"
                    )
                },
        ) {
            PixelyListItem(
                modifier = Modifier
                    .padding(vertical = 10.dp),
                headlineTextString = item.artist,
                largeHeadline = false,
                maxHeadlineLines = 1,
                leadingContent = {
                    SmallImageContainer(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .clip(CircleShape),
                        painterPlaceholder = painterResource(id = R.drawable.ic_outlined_artist_24),
                        leadingUnit = {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(item.albumCoverUri)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = stringResource(id = R.string.str_albumImage),
                                modifier = Modifier.fillMaxSize()
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

                        ArtistItemDropDownMenu(
                            expanded = expandedItemMenu
                        )
                    }
                }
            )
        }
    }
}