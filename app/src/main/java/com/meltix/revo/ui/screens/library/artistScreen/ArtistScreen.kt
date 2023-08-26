package com.meltix.revo.ui.screens.library.artistScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.meltix.pixely_components.PixelyListItem
import com.meltix.revo.R
import com.meltix.revo.data.classes.ContentState
import com.meltix.revo.data.classes.artist.Artist
import com.meltix.revo.ui.components.ContentSelector
import com.meltix.revo.ui.components.LoadingContent
import com.meltix.revo.ui.components.NoContentMessage
import com.meltix.revo.ui.components.SmallImageContainer
import com.meltix.revo.ui.components.contentModifier
import com.meltix.revo.ui.components.scrollBehaviorOnWindowSize
import com.meltix.revo.ui.components.surfaceColorOnWindowSize
import com.meltix.revo.ui.navigation.DetailsScreens
import com.meltix.revo.util.functions.findActivity

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun ArtistsScreen(
    rootNavController: NavController,
    libraryNavController: NavHostController,
    viewModel: ArtistViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowClass = calculateWindowSizeClass(activity)
    val scrollBehavior = scrollBehaviorOnWindowSize(windowClass)

    val contentState by viewModel.contentState.collectAsStateWithLifecycle(ContentState.LOADING)
    val artistList by viewModel.artists.collectAsStateWithLifecycle(emptyList())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { ArtistTopActionBar(rootNavController, scrollBehavior, viewModel, windowClass) },
        containerColor = surfaceColorOnWindowSize(windowClass),
        content = { padding ->
            ContentSelector(
                state = contentState,
                loadingUnit = {
                    LoadingContent(
                        modifier = Modifier.contentModifier(windowClass, padding),
                        windowClass = windowClass,
                        headlineString = stringResource(id = R.string.str_loadingArtists)
                    )
                },
                failedUnit = {
                    NoContentMessage(
                        modifier = Modifier.contentModifier(windowClass, padding),
                        windowClass = windowClass,
                        headlineString = stringResource(id = R.string.str_tooQuietArtists),
                        infoString = stringResource(id = R.string.info_tooQuietArtists),
                        leadingIcon = painterResource(id = R.drawable.ic_outlined_person_off_24)
                    )
                },
                contentUnit = {
                    LazyColumn(
                        modifier = Modifier.contentModifier(windowClass, padding),
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        contentPadding = PaddingValues(bottom = 70.dp)
                    ) { artistList(artistList, libraryNavController) }
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
                    navControllerBottomBar.navigate(DetailsScreens.ArtistDetails.route + "/${item.artistId}")
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