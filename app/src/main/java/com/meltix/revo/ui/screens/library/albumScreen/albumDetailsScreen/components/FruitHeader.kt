package com.meltix.revo.ui.screens.library.albumScreen.albumDetailsScreen.components

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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.meltix.revo.R
import com.meltix.revo.data.classes.album.AlbumDetails
import com.meltix.revo.data.classes.album.AlbumDuration
import com.meltix.revo.ui.components.albumInfoBuilder
import com.meltix.revo.ui.navigation.DetailsScreens
import com.meltix.revo.ui.screens.library.albumScreen.albumDetailsScreen.AlbumDetailsViewModel
import com.meltix.revo.ui.theme.Grey90
import com.meltix.revo.ui.theme.Grey99

@Composable
fun FruitHeader(
    viewModel: AlbumDetailsViewModel,
    navController: NavController,
    windowClass: WindowSizeClass,
    leadingUnit: @Composable () -> Unit?,
    albumDetails: AlbumDetails,
) {
    when(windowClass.widthSizeClass) {
        WindowWidthSizeClass.Compact ->
            CompactHeader(
                viewModel = viewModel,
                navController = navController,
                leadingUnit = leadingUnit,
                albumDetails = albumDetails
            )
        WindowWidthSizeClass.Medium ->
            MediumHeader(
                viewModel = viewModel,
                navController = navController,
                leadingUnit = leadingUnit,
                albumDetails = albumDetails
            )
        WindowWidthSizeClass.Expanded ->
            when(windowClass.heightSizeClass) {
                WindowHeightSizeClass.Compact ->
                    MediumHeader(
                        viewModel = viewModel,
                        navController = navController,
                        leadingUnit = leadingUnit,
                        albumDetails = albumDetails
                    )
            }
    }
}

@Composable
private fun CompactHeader(
    viewModel: AlbumDetailsViewModel,
    navController: NavController,
    leadingUnit: @Composable () -> Unit?,
    albumDetails: AlbumDetails,
) {
    val gradientStartY = with(LocalDensity.current) { 100.dp.toPx() }

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
                verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.Bottom)
            ) {
                HeaderText(
                    modifier = Modifier,
                    viewModel = viewModel,
                    navController = navController,
                    windowClass = WindowWidthSizeClass.Compact,
                    albumDetails = albumDetails
                )

                HeaderButtons(
                    modifier = Modifier,
                    windowClass = WindowWidthSizeClass.Compact,
                    onPlayClick = {  },
                    onShuffleClick = {  }
                )
            }
        }
    }
}

@Composable
private fun MediumHeader(
    viewModel: AlbumDetailsViewModel,
    navController: NavController,
    leadingUnit: @Composable () -> Unit?,
    albumDetails: AlbumDetails,
) {
    val gradientStartY = with(LocalDensity.current) { 100.dp.toPx() }

    Row(horizontalArrangement = Arrangement.spacedBy(15.dp)) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(290.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(80.dp),
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
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                            startY = gradientStartY
                        )
                    ),
                contentAlignment = Alignment.BottomStart
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    HeaderText(
                        modifier = Modifier.weight(1f),
                        viewModel = viewModel,
                        navController = navController,
                        windowClass = WindowWidthSizeClass.Medium,
                        albumDetails = albumDetails
                    )

                    HeaderButtons(
                        modifier = Modifier,
                        windowClass = WindowWidthSizeClass.Medium,
                        onPlayClick = {  },
                        onShuffleClick = {  }
                    )
                }
            }
        }
    }
}

@Composable
private fun HeaderText(
    modifier: Modifier,
    viewModel: AlbumDetailsViewModel,
    navController: NavController,
    windowClass: WindowWidthSizeClass,
    albumDetails: AlbumDetails
) {
    val interactionSource = remember { MutableInteractionSource() }
    val headlineStyle = when(windowClass) {
        WindowWidthSizeClass.Medium -> MaterialTheme.typography.headlineSmall
        else -> MaterialTheme.typography.headlineMedium
    }
    val songs by viewModel.songs.collectAsStateWithLifecycle(emptyList())
    val albumDuration by viewModel.albumDuration
        .collectAsStateWithLifecycle(AlbumDuration( 0, 0, 0, 0))
    val albumInfo = albumInfoBuilder(songs, albumDuration)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = albumDetails.title,
            modifier = Modifier,
            color = Grey99,
            style = headlineStyle,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = albumDetails.artistName,
            modifier = Modifier.clickable(interactionSource, null) {
                navController.navigate(DetailsScreens.ArtistDetails.route + "/${albumDetails.artistId}")
            },
            color = Grey90,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = albumInfo,
            modifier = Modifier,
            color = Grey90,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun HeaderButtons(
    modifier: Modifier,
    windowClass: WindowWidthSizeClass,
    onPlayClick: () -> Unit,
    onShuffleClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        when(windowClass) {
            WindowWidthSizeClass.Compact -> {
                ElevatedButton(
                    onClick = onPlayClick,
                    modifier = Modifier
                        .weight(0.5f)
                        .height(45.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_play_arrow_24),
                        contentDescription = stringResource(id = R.string.str_play),
                        modifier = Modifier.size(18.dp)
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
                        modifier = Modifier.size(18.dp)
                    )

                    Text(text = stringResource(id = R.string.str_shuffle))
                }
            }
            else -> {
                FilledTonalIconButton(
                    modifier = Modifier.size(50.dp),
                    onClick = onShuffleClick
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_play_arrow_24),
                        contentDescription = stringResource(id = R.string.str_play),
                        modifier = Modifier.size(18.dp)
                    )
                }

                FilledTonalIconButton(
                    modifier = Modifier
                        .height(50.dp)
                        .width(120.dp),
                    onClick = onShuffleClick
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_shuffle_24),
                        contentDescription = stringResource(id = R.string.str_shuffle),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}