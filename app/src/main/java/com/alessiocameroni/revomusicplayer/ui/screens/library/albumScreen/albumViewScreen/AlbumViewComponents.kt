package com.alessiocameroni.revomusicplayer.ui.screens.library.albumScreen.albumViewScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.alessiocameroni.pixely_components.RoundedDropDownMenu
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.ui.navigation.NavigationScreens
import com.alessiocameroni.revomusicplayer.ui.navigation.Screens

/**
 * Scaffold components
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumViewTopActionBar(
    navController: NavController,
    navControllerBottomBar: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior,
    textVisibility: State<Boolean>,
    artistId: Long,
    albumTitleString: String?,
) {
    val expandedMenu = remember { mutableStateOf(false) }
    val albumTitle: String = albumTitleString ?: "Album Title"

    TopAppBar(
        title = {
            AnimatedVisibility(
                visible = textVisibility.value,
                enter = fadeIn(animationSpec = tween(100)),
                exit = fadeOut(animationSpec = tween(100))
            ) {
                Text(
                    text = albumTitle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
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

                AlbumViewTopBarDropDownMenu(
                    expanded = expandedMenu,
                    navController = navController,
                    navControllerBottomBar = navControllerBottomBar,
                    artistId = artistId
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun AlbumViewTopBarDropDownMenu(
    expanded: MutableState<Boolean>,
    navController: NavController,
    navControllerBottomBar: NavHostController,
    artistId: Long
) {
    RoundedDropDownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false }
    ) {
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.str_goToArtist)) },
            onClick = {
                navControllerBottomBar.navigate(
                    NavigationScreens.ArtistViewScreen.route +
                        "/$artistId"
                )
                expanded.value = false
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_outlined_go_to_artist_24), 
                    contentDescription = stringResource(id = R.string.str_goToArtist)
                )
            }
        )

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


/**
 * Header components
 */
@Composable
fun AlbumViewHeader(
    navControllerBottomBar: NavHostController,
    albumTitleString: String? = null,
    albumArtistId: Long,
    albumArtistString: String? = null,
    albumSongAmount: Int?,
    albumHoursAmount: Int? = null,
    albumMinutesAmount: Int? = null,
    albumSecondsAmount: Int? = null,
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

            leadingUnit()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.35f))
            )

            HeaderText(
                navControllerBottomBar = navControllerBottomBar,
                albumTitleString = albumTitleString,
                albumArtistId = albumArtistId,
                albumArtistString = albumArtistString,
                albumSongAmount = albumSongAmount,
                albumHoursAmount = albumHoursAmount,
                albumMinutesAmount = albumMinutesAmount,
                albumSecondsAmount = albumSecondsAmount
            )
        }

        HeaderButtons()
    }
}

@Composable
private fun HeaderText(
    navControllerBottomBar: NavHostController,
    albumTitleString: String?,
    albumArtistId: Long,
    albumArtistString: String?,
    albumSongAmount: Int?,
    albumHoursAmount: Int?,
    albumMinutesAmount: Int?,
    albumSecondsAmount: Int?,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val albumTitle: String = albumTitleString ?: "Album Title"
    val artistName: String = albumArtistString ?: "Artist Name"
    val songAmount: Int = albumSongAmount ?: 0
    val hoursAmount: Int = albumHoursAmount ?: 0
    val minutesAmount: Int = albumMinutesAmount ?: 0
    val secondsAmount: Int = albumSecondsAmount ?: 0
    val albumInfo =
        "$songAmount " +
        pluralStringResource(id = R.plurals.str_songAmount, count = songAmount) +
        " · " +
        when {
            hoursAmount > 0 -> {
                "$hoursAmount " +
                        pluralStringResource(id = R.plurals.str_hourAmountAbbr, count = hoursAmount) +
                        " $minutesAmount " +
                        pluralStringResource(id = R.plurals.str_minutesAmountAbbr, count = minutesAmount)
            }
            else -> {
                "$minutesAmount " +
                        pluralStringResource(id = R.plurals.str_minutesAmountAbbr, count = minutesAmount) +
                        " $secondsAmount " +
                        stringResource(id = R.string.str_secondsAmountAbbr)
            }
        }

    Row(
        modifier = Modifier
            .padding(25.dp)
            .fillMaxSize(),
        verticalAlignment = Alignment.Bottom
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = albumTitle,
                modifier = Modifier,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = artistName,
                modifier = Modifier
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        navControllerBottomBar.navigate(
                            NavigationScreens.ArtistViewScreen.route +
                                "/$albumArtistId"
                        )
                    },
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = albumInfo,
                modifier = Modifier,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
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
@Composable
fun AlbumViewItemDropDownMenu(
    expanded: MutableState<Boolean>
) {
    RoundedDropDownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false }
    ) {

    }
}