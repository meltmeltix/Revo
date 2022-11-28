package com.alessiocameroni.revomusicplayer.library.artists.artistview.artistalbums

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import com.alessiocameroni.revomusicplayer.data.navigation.AlbumsScreens
import com.alessiocameroni.revomusicplayer.data.viewmodels.ArtistAlbumsViewModel
import com.alessiocameroni.revomusicplayer.library.components.LibraryListItem

@Composable
fun ArtistTabAlbums(
    artistId: Long,
    navControllerBottomBar: NavController,
    viewModel: ArtistAlbumsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val context = LocalContext.current
    val libraryAlbums = viewModel.libraryAlbums

    LaunchedEffect(Unit) { viewModel.initializeArtistAlbumList(context, artistId) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        itemsIndexed(libraryAlbums) { _, item ->
            Row(
                modifier = Modifier
                    .clickableRowItem()
                    .clickable {
                        navControllerBottomBar.navigate(AlbumsScreens.AlbumViewScreen.route)
                    },
            ) {
                LibraryListItem(
                    modifier = Modifier,
                    navControllerBottomBar = navControllerBottomBar,
                    painterPlaceholder = painterResource(id = R.drawable.ic_outlined_album_24),
                    stringMainTitle = item.albumTitle,
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
                    menuEnabled = false,
                    stringViewAlbumRoute = null,
                    stringViewArtistRoute = null,
                    itemAddToPlaylist = false
                )
            }
        }
    }
}