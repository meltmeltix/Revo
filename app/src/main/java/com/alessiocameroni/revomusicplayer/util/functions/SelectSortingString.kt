package com.alessiocameroni.revomusicplayer.util.functions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.data.classes.preferences.SortingType

@Composable
fun selectSortingString(option: SortingType): String =
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