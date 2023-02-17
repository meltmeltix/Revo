package com.alessiocameroni.revomusicplayer.ui.screens.library.artistScreen.artistViewScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.ui.components.LargeImageContainer
import com.alessiocameroni.revomusicplayer.ui.components.SmallImageContainer
import com.alessiocameroni.revomusicplayer.ui.navigation.Screens

/**
 * Scaffold components
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ArtistViewTopActionBar(
    navController: NavController,
    navControllerBottomBar: NavHostController,
    artistName: String?,
    artistAlbumAmount: LiveData<Int>,
    artistSongAmount: LiveData<Int>,
    scrollBehavior: TopAppBarScrollBehavior,
    textVisibility: State<Boolean>,
) {
    val expandedMenu = remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            AnimatedVisibility(
                visible = textVisibility.value,
                enter = fadeIn(animationSpec = tween(100)),
                exit = fadeOut(animationSpec = tween(100))
            ) {
                ArtistInfoText(
                    largeText = false,
                    artistName = artistName,
                    artistAlbumAmount = artistAlbumAmount,
                    artistSongAmount = artistSongAmount
                )
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
                IconButton(onClick = { expandedMenu.value = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                        contentDescription = stringResource(id = R.string.str_settings)
                    )
                }

                ArtistViewDropDownMenu(
                    expanded = expandedMenu,
                    navController = navController,
                )
            }
        },
        scrollBehavior = scrollBehavior,
    )
}

@Composable
private fun ArtistViewDropDownMenu(
    expanded: MutableState<Boolean>,
    navController: NavController,
) {
    MaterialTheme(shapes = MaterialTheme.shapes.copy(extraSmall = MaterialTheme.shapes.large)) {
        DropdownMenu(
            modifier = Modifier.widthIn(min = 180.dp),
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            Divider()

            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.str_settings)) },
                onClick = {
                    navController.navigate(Screens.SettingsScreen.route)
                    expanded.value = false
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outlined_settings_24),
                        contentDescription = stringResource(id = R.string.desc_settings)
                    )
                }
            )
        }
    }
}


/**
 * Header components
 */
@Composable
internal fun ArtistViewHeader(
    artistName: String?,
    artistAlbumAmount: LiveData<Int>,
    artistSongAmount: LiveData<Int>,
    leadingUnit: @Composable () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Row(
            modifier = Modifier
                .height(72.dp)
                .padding(
                    horizontal = 10.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SmallImageContainer(
                modifier = Modifier
                    .padding(start = 5.dp)
                    .clip(CircleShape),
                painterPlaceholder = painterResource(id = R.drawable.ic_outlined_artist_24)
            ) {
                leadingUnit()
            }

            ArtistInfoText(
                largeText = true,
                artistName,
                artistAlbumAmount,
                artistSongAmount
            )
        }

        HeaderButtons()
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
            onClick = {  },
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
            onClick = {  },
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
 * Text components
 */
@Composable
private fun ArtistInfoText(
    largeText: Boolean,
    artistName: String?,
    artistAlbumAmount: LiveData<Int>,
    artistSongAmount: LiveData<Int>
) {
    val nameString: String = artistName ?: "Artist Name"
    val albumAmount: Int = artistAlbumAmount.value ?: 0
    val songAmount: Int = artistSongAmount.value ?: 0

    val artistInfo =
        "$albumAmount " +
        pluralStringResource(
            id = R.plurals.str_albumAmount,
            count = albumAmount
        ) +
        " · " +
        "$songAmount " +
        pluralStringResource(
            id = R.plurals.str_songAmount,
            count = songAmount
        )

    if(largeText) {
        Column(
            modifier = Modifier.padding(start = 15.dp)
        ) {
            Text(
                text = nameString,
                modifier = Modifier,
                style = MaterialTheme.typography.headlineMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = artistInfo,
                modifier = Modifier,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    } else {
        Column {
            Text(
                text = nameString,
                modifier = Modifier,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = artistInfo,
                modifier = Modifier,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


/**
 * Screen components
 */
@Composable
internal fun ArtistViewSectionTitle(
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

@Composable
internal fun ArtistViewHorizontalListItem(
    albumTitle: String,
    leadingUnit: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .widthIn(max = 130.dp)
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        LargeImageContainer(
            painterPlaceholder =
                painterResource(id = R.drawable.ic_outlined_album_24)
        ) { leadingUnit() }

        Text(
            text = albumTitle,
            modifier = Modifier,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

