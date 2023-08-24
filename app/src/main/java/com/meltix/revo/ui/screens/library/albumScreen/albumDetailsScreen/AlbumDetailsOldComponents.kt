package com.meltix.revo.ui.screens.library.albumScreen.albumDetailsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.meltix.revo.R
import com.meltix.revo.data.classes.album.AlbumDetails
import com.meltix.revo.data.classes.album.AlbumDuration
import com.meltix.revo.data.classes.album.HeaderLayout
import com.meltix.revo.ui.navigation.DetailsScreens
import com.meltix.revo.ui.theme.Grey90
import com.meltix.revo.ui.theme.Grey99

@Composable
private fun FruitMusicHeader(
    layout: HeaderLayout,
    albumDetails: AlbumDetails,
    leadingUnit: @Composable () -> Unit?,
    libraryNavController: NavController,
    viewModel: AlbumDetailsViewModel,
) {
    val gradientStartY = with(LocalDensity.current) { 140.dp.toPx() }

    Column(modifier = Modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .aspectRatio(0.82f),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(120.dp),
                painter = painterResource(id = R.drawable.ic_outlined_album_24),
                contentDescription = stringResource(id = R.string.str_albumImage),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )

            leadingUnit()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f)
                            ),
                            startY = gradientStartY
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .padding(25.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                HeaderText(
                    albumDetails = albumDetails,
                    libraryNavController = libraryNavController,
                    viewModel = viewModel
                )

                HeaderButtons(
                    layout = layout,
                    onPlayClick = {  },
                    onShuffleClick = {  }
                )
            }
        }
    }
}

@Composable
private fun MinimalMusicHeader(
    layout: HeaderLayout,
    albumDetails: AlbumDetails,
    leadingUnit: @Composable () -> Unit?,
    libraryNavController: NavController,
    viewModel: AlbumDetailsViewModel,
) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .padding(top = 70.dp)
                .clip(MaterialTheme.shapes.extraLarge)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .aspectRatio(3.6f),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = R.drawable.ic_outlined_album_24),
                contentDescription = stringResource(id = R.string.str_albumImage),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )

            leadingUnit()
        }

        HeaderText(
            modifier = Modifier.padding(
                start = 20.dp,
                bottom = 8.dp,
                end = 20.dp,
            ),
            albumDetails = albumDetails,
            libraryNavController = libraryNavController,
            viewModel = viewModel,
            disableWhiteColor = true
        )

        HeaderButtons(
            layout = layout,
            onPlayClick = {  },
            onShuffleClick = {  }
        )
    }
}

@Composable
private fun HeaderText(
    modifier: Modifier = Modifier,
    albumDetails: AlbumDetails,
    libraryNavController: NavController,
    viewModel: AlbumDetailsViewModel,
    disableWhiteColor: Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val textColor = when(disableWhiteColor) {
        true -> MaterialTheme.colorScheme.onSurface
        false -> Grey99
    }
    val supportingTextColor = when(disableWhiteColor) {
        true -> MaterialTheme.colorScheme.onSurfaceVariant
        false -> Grey90
    }
    val songs by viewModel.songs.collectAsStateWithLifecycle(emptyList())
    val albumDuration by viewModel.albumDuration.collectAsStateWithLifecycle(
        AlbumDuration( 0, 0, 0, 0)
    )
    val albumInfo =
        "${songs.size} " +
            pluralStringResource(id = R.plurals.str_songAmount, count = songs.size) +
            " · " +
            when {
                albumDuration.hours > 0 -> {
                    "${albumDuration.hours} " +
                            pluralStringResource(id = R.plurals.str_hourAmountAbbr, count = albumDuration.hours) +
                            " ${albumDuration.minutes} " +
                            pluralStringResource(id = R.plurals.str_minutesAmountAbbr, count = albumDuration.minutes)
                }
                else -> {
                    "${albumDuration.minutes} " +
                            pluralStringResource(id = R.plurals.str_minutesAmountAbbr, count = albumDuration.minutes) +
                            " ${albumDuration.seconds} " +
                            stringResource(id = R.string.str_secondsAmountAbbr)
                }
            }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = albumDetails.title,
            modifier = Modifier,
            style = MaterialTheme.typography.headlineMedium,
            color = textColor,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = albumDetails.artistName,
            modifier = Modifier
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    libraryNavController.navigate(DetailsScreens.ArtistDetails.route + "/${albumDetails.artistId}")
                },
            color = supportingTextColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = albumInfo,
            modifier = Modifier,
            color = supportingTextColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun HeaderButtons(
    layout: HeaderLayout,
    onPlayClick: () -> Unit,
    onShuffleClick: () -> Unit
) {
    when(layout) {
        HeaderLayout.FRUIT_MUSIC -> {
            Row(
                modifier = Modifier.padding(top = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                ElevatedButton(
                    onClick = onPlayClick,
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

                ElevatedButton(
                    onClick = onShuffleClick,
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
        else -> {
            Row(
                modifier = Modifier.padding(horizontal = 15.dp),
                horizontalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                FilledTonalButton(
                    onClick = onPlayClick,
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
                    onClick = onShuffleClick,
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
    }
}