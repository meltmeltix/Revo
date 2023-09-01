package com.meltix.revo.ui.screens.library.songScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.meltix.pixely_components.PixelyListItem
import com.meltix.pixely_components.RoundedDropDownMenu
import com.meltix.revo.R
import com.meltix.revo.data.classes.ContentState
import com.meltix.revo.data.classes.song.Song
import com.meltix.revo.ui.components.LoadingContent
import com.meltix.revo.ui.components.NoContentMessage
import com.meltix.revo.ui.components.SmallImageContainer
import com.meltix.revo.ui.navigation.DetailsScreens
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
        SongsLayout(
            windowClass = windowClass,
            viewModel = viewModel,
            onRefresh = {},
            onNavigate = { rootNavController.navigate(it) },
            contentState = contentState,
            loadingContent = {
                LoadingContent(
                    modifier = Modifier.fillMaxSize(),
                    headlineString = stringResource(id = R.string.str_loadingSongs)
                )
            },
            noContent = {
                NoContentMessage(
                    modifier = Modifier.fillMaxSize(),
                    leadingIcon = painterResource(id = R.drawable.ic_baseline_music_off_24),
                    headlineString = stringResource(id = R.string.str_tooQuietSongs),
                    infoString = stringResource(id = R.string.info_tooQuietSongs)
                )
            }
        ) { songList(songs = songList) { libraryNavController.navigate(it) } }
    }
}

private fun LazyListScope.songList(songs: List<Song>, onMenuOptionClick: (String) -> Unit) {
    itemsIndexed(items = songs) { key, item ->
        key(key) {
            Row(modifier = Modifier.clickable{ }) {
                PixelyListItem(
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
    }
}