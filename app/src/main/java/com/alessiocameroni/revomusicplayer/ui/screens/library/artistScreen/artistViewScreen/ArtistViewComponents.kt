package com.alessiocameroni.revomusicplayer.ui.screens.library.artistScreen.artistViewScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.ui.components.LargeImageContainer
import com.alessiocameroni.revomusicplayer.ui.components.SmallImageContainer

/**
 * Scaffold components
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ArtistViewTopActionBar(
    modifier: Modifier,
    navControllerBottomBar: NavHostController,
    expanded: MutableState<Boolean>,
    scrollBehavior: TopAppBarScrollBehavior,
    textVisibility: State<Boolean>,
) {
    TopAppBar(
        title = {
            AnimatedVisibility(
                visible = textVisibility.value,
                enter = fadeIn(animationSpec = tween(100)),
                exit = fadeOut(animationSpec = tween(100))
            ) {
                Column {
                    Text(
                        text = "Artist name",
                        modifier = Modifier,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = "Artist info",
                        modifier = Modifier,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        },
        modifier = modifier,
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
        scrollBehavior = scrollBehavior,
    )
}


/**
 * Header components
 */
@Composable
internal fun ArtistViewHeader() {
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

            }

            Column(
                modifier = Modifier
                    .padding(start = 15.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "Artist name",
                    modifier = Modifier,
                    style = MaterialTheme.typography.headlineMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "Artist info",
                    modifier = Modifier,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
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

