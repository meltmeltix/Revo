package com.alessiocameroni.revomusicplayer

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alessiocameroni.revomusicplayer.components.lists.SettingsMainItem
import com.alessiocameroni.revomusicplayer.ui.theme.RevoMusicPlayerTheme

class SettingsActivity : ComponentActivity() {
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
                        },
                        content = { padding ->
                            Column(
                                modifier = Modifier
                                    .padding(padding)
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(5.dp)
                            ){
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(22.dp))
                                        .clickable {  }
                                ) {
                                    SettingsMainItem(
                                        painterIcon = painterResource(id = R.drawable.ic_outlined_palette_24),
                                        stringTitleItem = stringResource(id = R.string.str_looks),
                                        stringSubtitleItem = stringResource(id = R.string.desc_looks)
                                    )
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(22.dp))
                                        .clickable {  }
                                ) {
                                    SettingsMainItem(
                                        painterIcon = painterResource(id = R.drawable.ic_outlined_info_24),
                                        stringTitleItem = stringResource(id = R.string.str_about),
                                        stringSubtitleItem = stringResource(id = R.string.desc_about)
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

