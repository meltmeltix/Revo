package com.alessiocameroni.revomusicplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.alessiocameroni.revomusicplayer.navigation.Navigation
import com.alessiocameroni.revomusicplayer.ui.theme.RevoMusicPlayerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RevoMusicPlayerTheme {
                Navigation()
            }
        }
    }
}