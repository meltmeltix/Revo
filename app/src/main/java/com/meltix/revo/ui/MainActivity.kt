package com.meltix.revo.ui

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.meltix.revo.ui.theme.RevoTheme
import com.meltix.revo.util.permissions.SetContentByPermission
import dagger.hilt.android.AndroidEntryPoint

val permissionsList = listOf(
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        android.Manifest.permission.READ_MEDIA_AUDIO
    } else { android.Manifest.permission.READ_EXTERNAL_STORAGE },
)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        var keepSplash = true
        val splashDelay = 300L

        super.onCreate(savedInstanceState)

        installSplashScreen().setKeepOnScreenCondition { keepSplash }
        Handler(Looper.getMainLooper()).postDelayed({ keepSplash = false }, splashDelay)
        enableEdgeToEdge()

        setContent {
            /*
             * TODO Since we want the user to choose which theme to use within the app,
             *  remember to add a viewmodel for the mainactivity, and add a preference
             *  based on UI state.
             *  Check https://github.com/android/nowinandroid/blob/main/app/src/main/java/com/google/samples/apps/nowinandroid/MainActivity.kt
             */

            RevoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SetContentByPermission()
                }
            }
        }
    }
}