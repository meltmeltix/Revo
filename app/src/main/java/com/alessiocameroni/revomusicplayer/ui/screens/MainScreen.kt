package com.alessiocameroni.revomusicplayer.ui.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alessiocameroni.revomusicplayer.ui.navigation.NavigationBottomNavBar
import com.alessiocameroni.revomusicplayer.ui.theme.RevoMusicPlayerTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen(navController: NavController) {
    val navControllerBottomBar = rememberAnimatedNavController()
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()

    RevoMusicPlayerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                bottomBar = {
                    BottomContent(
                        navController,
                        navControllerBottomBar
                    )
                },
                content = { padding ->
                    Column(modifier = Modifier
                        .padding(
                            bottom =
                                padding.calculateBottomPadding() -
                                systemBarsPadding.calculateBottomPadding() -
                                80.dp
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