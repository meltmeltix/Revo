package com.alessiocameroni.revomusicplayer.ui.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alessiocameroni.revomusicplayer.ui.navigation.NavigationBottomNavBar
import com.alessiocameroni.revomusicplayer.ui.theme.RevoMusicPlayerTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val navControllerBottomBar = rememberAnimatedNavController()
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    val spotifyVisibilityState by remember { viewModel.spotifyEnabledState }

    RevoMusicPlayerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                bottomBar = {
                    BottomContent(
                        navController,
                        navControllerBottomBar,
                        spotifyVisibilityState
                    )
                },
                content = { padding ->
                    Column(modifier = Modifier
                        .padding(
                            bottom =
                                padding.calculateBottomPadding() -
                                systemBarsPadding.calculateBottomPadding() -
                                70.dp
                        )
                    ){
                        NavigationBottomNavBar(
                            navControllerApp = navController,
                            navControllerBottomBar = navControllerBottomBar
                        )
                    }
                }
            )
        }
    }
}