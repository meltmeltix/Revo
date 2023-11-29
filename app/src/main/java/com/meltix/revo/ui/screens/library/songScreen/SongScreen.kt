package com.meltix.revo.ui.screens.library.songScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.meltix.revo.data.classes.ContentState
import com.meltix.revo.data.classes.song.Song
import com.meltix.revo.ui.theme.RevoTheme
import com.meltix.revo.util.functions.WindowType
import com.meltix.revo.util.functions.defineWindowType
import com.meltix.revo.util.functions.findActivity

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun SongsScreen(
    rootNavController: NavController,
    libraryNavController: NavController,
    viewModel: SongViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowType = defineWindowType(calculateWindowSizeClass(activity))

    val contentState by viewModel.contentState.collectAsStateWithLifecycle(ContentState.LOADING)
    val songList by viewModel.songs.collectAsStateWithLifecycle(emptyList())

    RevoTheme {
        SongsScreen(
            contentState = contentState,
            songList = songList,
            windowType = windowType
        )
    }
}

@Composable
private fun SongsScreen(contentState: ContentState, songList: List<Song>, windowType: WindowType) {

}