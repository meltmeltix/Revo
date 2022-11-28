package com.alessiocameroni.revomusicplayer.library.artists.artistview.artistalbums

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.alessiocameroni.revomusicplayer.data.viewmodels.AlbumsViewModel

@Composable
fun ArtistTabAlbums(
    artistId: Long,
    viewModel: AlbumsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) { viewModel.initializeArtistAlbumList(context, artistId) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {

    }
}