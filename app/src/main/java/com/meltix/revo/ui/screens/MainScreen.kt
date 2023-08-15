package com.meltix.revo.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.meltix.revo.ui.theme.RevoTheme

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    RevoTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                MainScaffold()

                MainNavigation()
            }
        }
    }
}

/*val navControllerBottomBar = rememberNavController()
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    val spotifyVisibilityState by remember { viewModel.spotifyEnabledState }*/

/*Scaffold(
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
)*/