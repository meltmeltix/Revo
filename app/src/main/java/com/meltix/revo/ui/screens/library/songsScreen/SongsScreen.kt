package com.meltix.revo.ui.screens.library.songsScreen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.meltix.revo.data.classes.ContentState
import com.meltix.revo.data.classes.song.Song
import com.meltix.revo.ui.theme.RevoTheme
import com.meltix.revo.util.functions.defineWindowType
import com.meltix.revo.util.functions.findActivity

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun SongsScreen(
    rootNavController: NavController,
    libraryNavController: NavController,
    viewModel: SongsViewModel = hiltViewModel(),
    contentPadding: PaddingValues,
) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowType = defineWindowType(calculateWindowSizeClass(activity))

    val contentState by viewModel.contentState.collectAsStateWithLifecycle(ContentState.LOADING)
    val songList by viewModel.songs.collectAsStateWithLifecycle(emptyList())
    
    SongsScreen(
        contentState = contentState,
        contentPadding = contentPadding,
        songList = songList
    )
}

@Composable
private fun SongsScreen(
    contentState: ContentState,
    contentPadding: PaddingValues,
    songList: List<Song>
) {
    RevoTheme {
        SongsLayout(
            contentPadding = contentPadding,
            list = songList
        )
    }
}