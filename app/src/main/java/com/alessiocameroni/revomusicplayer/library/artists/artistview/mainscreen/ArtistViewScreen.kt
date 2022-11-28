package com.alessiocameroni.revomusicplayer.library.artists.artistview.mainscreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.library.artists.artistview.artistalbums.ArtistTabAlbums
import com.alessiocameroni.revomusicplayer.library.artists.artistview.artistsongs.ArtistTabSongs
import com.alessiocameroni.revomusicplayer.library.artists.artistview.mainscreen.data.tabs.ArtistTabsItemData
import com.alessiocameroni.revomusicplayer.library.components.ViewsDropDownMenu
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun ArtistViewScreen(
    navController: NavController,
    navControllerBottomBar: NavHostController,
    artistId: Long?,
    artist: String?,
) {
    val expanded = remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val pagerState = rememberPagerState(0)
    val tabs = listOf(
        ArtistTabsItemData(
            name = stringResource(id = R.string.str_songs),
            screen = {
                if (artistId != null) {
                    ArtistTabSongs(artistId)
                }
            }
        ),
        ArtistTabsItemData(
            name = stringResource(id = R.string.str_albums),
            screen = {
                if (artistId != null) {
                    ArtistTabAlbums(artistId)
                }
            }
        )
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    if (artist != null) {
                        Text(text = artist)
                    } else {
                        Text(text = "Artist")
                    }
                },
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

                        ViewsDropDownMenu(
                            navController = navController,
                            expanded = expanded,
                            itemSortBy = true,
                            itemGridType = true,
                            itemRename = false,
                            itemDelete = true,
                            itemSettings = true
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    scrolledContainerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                ArtistViewTabs(
                    modifier = Modifier,
                    pagerState = pagerState,
                    tabs = tabs
                )

                ArtistViewTabContent(
                    tabs = tabs,
                    pagerState = pagerState
                )
            }
        }
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ArtistViewTabs(
    modifier: Modifier,
    pagerState: PagerState,
    tabs: List<ArtistTabsItemData>
) {
    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        modifier = modifier
    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                text = { Text(text = tab.name) },
                selected = pagerState.currentPage == index,
                onClick = { scope.launch { pagerState.animateScrollToPage(index) } }
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ArtistViewTabContent(
    tabs: List<ArtistTabsItemData>,
    pagerState: PagerState
) {
    HorizontalPager(count = tabs.size, state = pagerState) { page ->
        tabs[page].screen()
    }
}
