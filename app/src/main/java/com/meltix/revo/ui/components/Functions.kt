package com.meltix.revo.ui.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.meltix.revo.R
import com.meltix.revo.data.classes.album.AlbumDuration
import com.meltix.revo.data.classes.album.AlbumSong
import com.meltix.revo.data.classes.album.HeaderLayout

@Composable
fun albumInfoBuilder(songs: List<AlbumSong>, albumDuration: AlbumDuration): String {
    return "${songs.size} " +
            pluralStringResource(id = R.plurals.str_songAmount, count = songs.size) + " · " +
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun albumDetailsTopAppBarColor(
    windowClass: WindowSizeClass,
    layout: HeaderLayout,
    firstVisibleItem: State<Boolean>
): TopAppBarColors {
    val transition = updateTransition(targetState = firstVisibleItem.value, label = "")
    val appBarColorFloat by transition.animateFloat(
        transitionSpec = { tween(1) },
        label = ""
    ) {
        when(it) {
            true -> 1f
            false -> 0f
        }
    }

    return when(windowClass.widthSizeClass) {
        WindowWidthSizeClass.Medium, WindowWidthSizeClass.Expanded ->
            when(windowClass.heightSizeClass) {
                WindowHeightSizeClass.Compact ->
                    when(layout) {
                        HeaderLayout.FRUIT_MUSIC -> TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme
                                .colorScheme.surfaceColorAtElevation(1.dp).copy(appBarColorFloat),
                            scrolledContainerColor = MaterialTheme
                                .colorScheme.surfaceColorAtElevation(1.dp).copy(appBarColorFloat)
                        )
                        else -> TopAppBarDefaults.topAppBarColors(
                            scrolledContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
                        )
                    }
                else ->
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                        scrolledContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
                    )
            }
        else ->
            when(layout) {
                HeaderLayout.FRUIT_MUSIC ->
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme
                            .colorScheme.surfaceColorAtElevation(3.dp).copy(appBarColorFloat),
                        scrolledContainerColor = MaterialTheme
                            .colorScheme.surfaceColorAtElevation(3.dp).copy(appBarColorFloat)
                    )
                else -> TopAppBarDefaults.topAppBarColors()
            }
    }
}

@Composable
fun albumDetailsTopElementsColor(
    windowClass: WindowSizeClass,
    layout: HeaderLayout,
    firstVisibleItem: State<Boolean>
): Color {
    val transition = updateTransition(targetState = firstVisibleItem.value, label = "")
    val actionsContainerColor by transition.animateColor(
        transitionSpec = { tween(1) },
        label = ""
    ) {
        when(it) {
            true -> when(windowClass.heightSizeClass) {
                WindowHeightSizeClass.Compact -> MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
                else -> MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
            }
            false -> MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
        }
    }

    return when(windowClass.widthSizeClass) {
        WindowWidthSizeClass.Medium, WindowWidthSizeClass.Expanded ->
            when(windowClass.heightSizeClass) {
                WindowHeightSizeClass.Compact -> when (layout) {
                    HeaderLayout.FRUIT_MUSIC -> actionsContainerColor
                    else -> IconButtonDefaults.iconButtonColors().containerColor
                }
                else -> IconButtonDefaults.iconButtonColors().containerColor
            }
        else ->
            when(layout) {
                HeaderLayout.FRUIT_MUSIC -> actionsContainerColor
                else -> IconButtonDefaults.iconButtonColors().containerColor
            }
    }
}