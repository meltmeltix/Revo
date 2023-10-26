package com.meltix.revo.ui.screens.library.songScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.meltix.revo.data.classes.ContentState
import com.meltix.revo.ui.theme.RevoTheme
import com.meltix.revo.util.functions.findActivity

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun SongsScreen(
    rootNavController: NavController,
    libraryNavController: NavController,
    viewModel: SongViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowClass = calculateWindowSizeClass(activity)

    val contentState by viewModel.contentState.collectAsStateWithLifecycle(ContentState.LOADING)
    val songList by viewModel.songs.collectAsStateWithLifecycle(emptyList())

    RevoTheme {
    
    }
}

/*
private fun LazyListScope.songList(songs: List<Song>, onMenuOptionClick: (String) -> Unit) {
    itemsIndexed(items = songs) { key, item ->
        key(key) {
            PixelyListItem(
                modifier = Modifier.clickable {  },
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
                                contentDescription = stringResource(id = R.string.str_albumImage)
                            )
                        }
                    )
                },
                trailingContent = {
                    val expanded = remember { mutableStateOf(false) }
            
                    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                        IconButton(onClick = { expanded.value = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                                contentDescription = stringResource(id = R.string.str_moreOptions)
                            )
                        }
                
                        RoundedDropDownMenu(
                            expanded = expanded.value,
                            onDismissRequest = { expanded.value = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text(text = stringResource(id = R.string.str_goToAlbum)) },
                                onClick = {
                                    onMenuOptionClick(DetailsScreens.AlbumDetails.route + "/${item.albumId}")
                                    expanded.value = false
                                }
                            )
                    
                            DropdownMenuItem(
                                text = { Text(text = stringResource(id = R.string.str_goToArtist)) },
                                onClick = {
                                    onMenuOptionClick(DetailsScreens.ArtistDetails.route + "/${item.artistId}")
                                    expanded.value = false
                                }
                            )
                        }
                    }
                }
            )
        }
    }
}*/
