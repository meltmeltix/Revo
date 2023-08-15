package com.meltix.revo.util.functions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.meltix.revo.R
import com.meltix.revo.data.classes.album.HeaderLayout
import com.meltix.revo.data.classes.player.PlayerLayout
import com.meltix.revo.data.classes.preferences.SortingOrder
import com.meltix.revo.data.classes.preferences.SortingType

@Composable
fun selectSortingTypeString(option: SortingType): String =
    when(option) {
        SortingType.TITLE -> stringResource(id = R.string.str_name)
        SortingType.TRACK -> stringResource(id = R.string.str_trackNumber)
        SortingType.ARTIST -> stringResource(id = R.string.str_artist)
        SortingType.ALBUM -> stringResource(id = R.string.str_album)
        SortingType.YEAR -> stringResource(id = R.string.str_year)
        SortingType.NUMBER_OF_TRACKS -> stringResource(id = R.string.str_trackNumber)
        SortingType.DURATION -> stringResource(id = R.string.str_duration)
        SortingType.DATE_ADDED -> stringResource(id = R.string.str_dateAdded)
    }

@Composable
fun selectSortingOrderString(option: SortingOrder): String =
    when(option) {
        SortingOrder.ASCENDING -> stringResource(id = R.string.str_ascending)
        SortingOrder.DESCENDING -> stringResource(id = R.string.str_descending)
    }

@Composable
fun selectPlayerLayoutString(option: PlayerLayout): String =
    when(option) {
        PlayerLayout.LEFT -> stringResource(id = R.string.str_left)
        PlayerLayout.CENTER -> stringResource(id = R.string.str_center)
        PlayerLayout.RIGHT -> stringResource(id = R.string.str_right)
    }

@Composable
fun selectAlbumViewHeaderLayoutString(option: HeaderLayout): String =
    when(option) {
        HeaderLayout.REVO -> stringResource(id = R.string.app_name)
        HeaderLayout.FRUIT_MUSIC -> stringResource(id = R.string.str_fruitMusicLayout)
        HeaderLayout.MINIMAL -> stringResource(id = R.string.str_minimalLayout)
    }

@Composable
fun selectAlbumViewHeaderLayoutSupportingString(option: HeaderLayout): String =
    when(option) {
        HeaderLayout.REVO -> stringResource(id = R.string.info_defaultAlbumViewLayout)
        HeaderLayout.FRUIT_MUSIC -> stringResource(id = R.string.info_fruitMusicLayout)
        HeaderLayout.MINIMAL -> stringResource(id = R.string.info_minimalLayout)
    }