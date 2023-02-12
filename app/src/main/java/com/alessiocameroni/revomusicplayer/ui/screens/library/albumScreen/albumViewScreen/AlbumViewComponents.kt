package com.alessiocameroni.revomusicplayer.ui.screens.library.albumScreen.albumViewScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alessiocameroni.revomusicplayer.R

/**
 * Header components
 */
@Composable
internal fun AlbumViewHeader(
    albumTitleString: String? = null,
    albumArtistString: String? = null,
    albumInfoString: String? = null,
    leadingUnit: @Composable () -> Unit?,
) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .clip(MaterialTheme.shapes.extraLarge)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .aspectRatio(1f),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(120.dp),
                painter = painterResource(id = R.drawable.ic_outlined_album_24),
                contentDescription = stringResource(id = R.string.desc_albumImage),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.15f))
            )

            HeaderText(
                albumTitleString = albumTitleString,
                albumArtistString = albumArtistString,
                albumInfoString = albumInfoString
            )

            leadingUnit()
        }

        HeaderButtons()
    }
}

@Composable
private fun HeaderText(
    albumTitleString: String?,
    albumArtistString: String?,
    albumInfoString: String?
) {
    val albumTitle: String = albumTitleString ?: "Album Title"
    val artistName: String = albumArtistString ?: "Artist Name"
    val albumInfo: String = albumInfoString ?: "0 Songs - 00:00 Minutes"

    Row(
        modifier = Modifier
            .padding(25.dp)
            .fillMaxSize(),
        verticalAlignment = Alignment.Bottom
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = albumTitle,
                modifier = Modifier,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                maxLines = 2,
            )

            Text(
                text = artistName,
                modifier = Modifier,
                color = Color.White,
                maxLines = 1,
            )

            Text(
                text = albumInfo,
                modifier = Modifier,
                color = Color.White,
                maxLines = 1,
            )
        }
    }
}

@Composable
private fun HeaderButtons() {
    Row(
        modifier = Modifier
            .padding(horizontal = 15.dp),
        horizontalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        FilledTonalButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .weight(0.5f)
                .height(45.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_play_arrow_24), 
                contentDescription = stringResource(id = R.string.str_play),
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(18.dp)
            )

            Text(text = stringResource(id = R.string.str_play))
        }
        
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .weight(0.5f)
                .height(45.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_shuffle_24),
                contentDescription = stringResource(id = R.string.str_shuffle),
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(18.dp)
            )

            Text(text = stringResource(id = R.string.str_shuffle))
        }
    }
}


/**
 * Screen components
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TopActionBar(
    navControllerBottomBar: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior,

    textVisibility: State<Boolean>,
    albumTitleString: String
) {
    val expanded = remember { mutableStateOf(false) }

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
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
internal fun AlbumViewSectionTitle(
    stringTitle: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(horizontal = 15.dp)
            .padding(top = 20.dp)
    ) {
        Text(
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.primary,
            text = stringTitle,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
