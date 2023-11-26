package com.meltix.revo.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import com.meltix.revo.R
import com.meltix.revo.data.classes.album.AlbumDuration
import com.meltix.revo.data.classes.album.AlbumSong

@Composable
fun albumInfoBuilder(songs: List<AlbumSong>, duration: AlbumDuration): String {
    return pluralStringResource(id = R.plurals.songAmount, count = songs.size) + " · " +
            when {
                duration.hours > 0 -> {
                    pluralStringResource(id = R.plurals.hourAmount, count = duration.hours) + " " +
                    pluralStringResource(id = R.plurals.minutesAmount, count = duration.minutes)
                }
                else -> {
                    pluralStringResource(id = R.plurals.minutesAmount, count = duration.minutes) + " " +
                    stringResource(id = R.string.secondsAmount, duration.seconds)
                }
            }
}