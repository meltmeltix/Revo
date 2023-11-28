package com.meltix.revo.ui.screens.library.albumScreen.albumDetailsScreen

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.meltix.revo.data.classes.ContentState
import com.meltix.revo.data.classes.album.AlbumDetails
import com.meltix.revo.data.classes.album.HeaderLayout
import com.meltix.revo.util.functions.findActivity


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun AlbumViewScreen(
    albumId: Long,
    rootNavController: NavController,
    libraryNavController: NavController,
    viewModel: AlbumDetailsViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowClass = calculateWindowSizeClass(activity)
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val scrollState = rememberLazyListState()
    val firstVisibleItem = remember { derivedStateOf { scrollState.firstVisibleItemIndex > 0 } }

    val contentState by viewModel.contentState.collectAsStateWithLifecycle(ContentState.LOADING)
    val headerLayout by viewModel.headerLayout.collectAsStateWithLifecycle(HeaderLayout.REVO)
    val albumDetails by viewModel.albumDetails.collectAsStateWithLifecycle(
        AlbumDetails("Unknown Album", 0, "Unknown Artist", null)
    )
    val songList by viewModel.songs.collectAsStateWithLifecycle(emptyList())

    LaunchedEffect(Unit) { viewModel.initializeAlbumData(albumId) }

    
}

/*
private fun LazyListScope.albumSongsList(songs: List<AlbumSong>) {
    itemsIndexed(items = songs) { key, item ->
        key(key) {
            Row(modifier = Modifier.clickable {  }) {
                PixelyListItem(
                    headlineTextString = item.songTitle,
                    largeHeadline = false,
                    maxHeadlineLines = 1,
                    leadingContent = {
                        Text(
                            text = if(item.track == 0) "-" else item.track.toString(),
                            modifier = Modifier
                                .widthIn(max = 40.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Clip,
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    trailingContent = {
                        val expandedItemMenu = remember { mutableStateOf(false) }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = item.fixedDuration ?: "00:00",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 14.sp,
                                maxLines = 1,
                                style = MaterialTheme.typography.bodyMedium,
                            )

                            Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                                IconButton(onClick = { expandedItemMenu.value = true }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                                        contentDescription = stringResource(id = R.string.str_moreOptions)
                                    )
                                }

                                AlbumDetailsItemDropDownMenu(
                                    expanded = expandedItemMenu
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}*/
