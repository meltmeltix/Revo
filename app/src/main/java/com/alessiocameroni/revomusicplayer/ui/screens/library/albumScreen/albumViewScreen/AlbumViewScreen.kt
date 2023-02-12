package com.alessiocameroni.revomusicplayer.ui.screens.library.albumScreen.albumViewScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import com.alessiocameroni.pixely_components.PixelyListItem
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.ui.components.SmallImageContainer
import com.alessiocameroni.revomusicplayer.ui.screens.library.ItemDropDownMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumViewScreen(
    navController: NavController,
    navControllerBottomBar: NavHostController,
) {
    val expanded = remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val scrollState = rememberLazyListState()
    val textVisibility = remember {
        derivedStateOf {
            scrollState.firstVisibleItemIndex > 0
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    AnimatedVisibility(
                        visible = textVisibility.value,
                        enter = fadeIn(
                            animationSpec = tween(100)
                        ),
                        exit = fadeOut(
                            animationSpec = tween(100)
                        )
                    ) {
                        Text(text = "Album Title")
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
                    }
                }, scrollBehavior = scrollBehavior
            )
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                state = scrollState
            ) {
                item {
                    AlbumViewHeader(
                        headlineTextString = null,
                        supportingTextString = null,
                        leadingUnit = {  }
                    )
                }

                item {
                    SectionTitleBar(
                        stringTitle = stringResource(id = R.string.str_songs)
                    )
                }

                items(10) {
                    Row(
                        modifier = Modifier
                            .clickable { },
                    ) {
                        PixelyListItem(
                            headlineTextString = "Placeholder Song",
                            largeHeadline = false,
                            maxHeadlineLines = 1,
                            supportingTextString = "Placeholder Artist",
                            maxSupportingLines = 1,
                            leadingContent = {
                                SmallImageContainer(
                                    modifier = Modifier.padding(horizontal = 5.dp),
                                    painterPlaceholder =
                                    painterResource(id = R.drawable.ic_baseline_music_note_24),
                                    leadingUnit = {  }
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