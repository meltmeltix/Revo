package com.alessiocameroni.revomusicplayer

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.alessiocameroni.revomusicplayer.ui.theme.RevoMusicPlayerTheme

class SettingsActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val activity = (LocalContext.current as? Activity)
            val context = LocalContext.current
            val decayAnimationSpec = rememberSplineBasedDecay<Float>()
            val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
                decayAnimationSpec,
                rememberTopAppBarState()
            )

            RevoMusicPlayerTheme{
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                        topBar = {
                            LargeTopAppBar(
                                title = { Text(text = stringResource(id = R.string.str_settings)) },
                                navigationIcon = {
                                    IconButton(onClick = { activity?.finish() }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                                            contentDescription = stringResource(id = R.string.desc_back)
                                        )
                                    }
                                },
                                scrollBehavior = scrollBehavior
                            )
                        }
                    ) {

                    }
                }
            }
        }
    }


}

