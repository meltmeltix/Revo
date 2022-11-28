package com.alessiocameroni.revomusicplayer.library.artists.artistview.artistsongs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.data.modifiers.clickableRowItem
import com.alessiocameroni.revomusicplayer.data.viewmodels.ArtistSongsViewModel
import com.alessiocameroni.revomusicplayer.library.components.LibraryListItem

@Composable
fun ArtistTabSongs(
    artistId: Long,
    navControllerBottomBar: NavController,
    viewModel: ArtistSongsViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {
    val context = LocalContext.current
    val librarySongs = viewModel.librarySongs

    LaunchedEffect(Unit) { viewModel.initializeArtistSongList(context, artistId) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 4.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        itemsIndexed(items = librarySongs) { _, item ->
            Row(
                modifier = Modifier
                    .clickableRowItem()
                    .clickable { },
            ) {
                LibraryListItem(
                    modifier = Modifier,
                    navControllerBottomBar = navControllerBottomBar,
                    painterPlaceholder = painterResource(id = R.drawable.ic_baseline_music_note_24),
                    stringMainTitle = item.songTitle,
                    stringSubtitle = item.artist,
                    leadingUnit = {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(item.albumCoverUri)
                                .crossfade(true)
                                .build(),
                            contentDescription = stringResource(id = R.string.desc_albumImage)
                        )
                    },
                    menuEnabled = true,
                    stringViewAlbumRoute = null,
                    stringViewArtistRoute = null,
                    itemAddToPlaylist = true
                )
            }
        }
    }
}