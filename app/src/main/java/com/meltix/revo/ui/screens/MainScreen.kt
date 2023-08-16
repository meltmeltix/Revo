package com.meltix.revo.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.meltix.revo.ui.components.panelSurfaceModifier
import com.meltix.revo.ui.components.surfaceColorOnWindowSize
import com.meltix.revo.ui.navigation.NavigationLibrary
import com.meltix.revo.ui.theme.RevoTheme
import com.meltix.revo.util.functions.findActivity

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowClass = calculateWindowSizeClass(activity)
    val navControllerMainNavigation = rememberNavController()

    val spotifyItemState by viewModel.spotifyEnabledState.collectAsStateWithLifecycle(false)

    RevoTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = surfaceColorOnWindowSize(windowClass)
        ) {
            Box {
                Surface(
                    modifier = Modifier.panelSurfaceModifier(windowClass),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    MainScaffold(
                        content = { _ ->
                            NavigationLibrary(
                                navControllerApp = navController,
                                navControllerMain = navControllerMainNavigation
                            )
                        }
                    )
                }

                MainNavigation(
                    windowClass = windowClass,
                    navController = navControllerMainNavigation,
                    spotifyItemState = spotifyItemState
                )
            }
        }
    }
}

/*
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

        }
    }
)
*/