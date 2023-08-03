package com.alessiocameroni.revomusicplayer.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alessiocameroni.revomusicplayer.ui.navigation.NavigationBottomNavBar
import com.alessiocameroni.revomusicplayer.ui.theme.RevoMusicPlayerTheme

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val navControllerBottomBar = rememberNavController()
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