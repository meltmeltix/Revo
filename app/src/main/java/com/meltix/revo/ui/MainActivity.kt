package com.meltix.revo.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.meltix.revo.data.classes.library.LibraryNavigationItem
import com.meltix.revo.data.classes.UiState
import com.meltix.revo.ui.navigation.RootNavigation
import com.meltix.revo.ui.navigation.RootScreens
import com.meltix.revo.ui.theme.RevoTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

val permissionsList = listOf(
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        android.Manifest.permission.READ_MEDIA_AUDIO
    } else { android.Manifest.permission.READ_EXTERNAL_STORAGE },
)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel: MainActivityViewModel by viewModels()
    
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        
        var destinationsList: List<LibraryNavigationItem> by mutableStateOf(emptyList())
        var uiState: UiState by mutableStateOf(UiState.LOADING)
        
        // Update uiState
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.destinations
                    .onEach { destinationsList = it }
                    .collect()
            }
        }
        
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .onEach { uiState = it }
                    .collect()
            }
        }

        // Keep the splash screen on-screen until the UI is loaded
        splashScreen.setKeepOnScreenCondition {
            when(uiState) {
                UiState.LOADING -> true
                UiState.SUCCESS -> false
            }
        }
        
        enableEdgeToEdge()

        setContent {
            /*
             * TODO Since we want the user to choose which theme to use within the app,
             *  remember to add a viewmodel for the mainactivity, and add a preference
             *  based on UI state.
             *  Check https://github.com/android/nowinandroid/blob/main/app/src/main/kotlin/com/google/samples/apps/nowinandroid/MainActivity.kt
             */

            RevoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RootNavigation(
                        destinationsList = destinationsList,
                        startDestination = RootScreens.Main.route
                    )
                }
            }
        }
    }
}