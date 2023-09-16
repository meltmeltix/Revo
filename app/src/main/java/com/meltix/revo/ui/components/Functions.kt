package com.meltix.revo.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import com.meltix.revo.R
import com.meltix.revo.data.classes.album.AlbumDuration
import com.meltix.revo.data.classes.album.AlbumSong

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