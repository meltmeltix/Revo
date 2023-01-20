package com.alessiocameroni.revomusicplayer.settings.other.applanguage

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alessiocameroni.pixely_components.PixelyListItem
import com.alessiocameroni.pixely_components.PixelySupportInfoText
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.data.modifiers.clickableRowItem
import com.alessiocameroni.revomusicplayer.ui.theme.RevoMusicPlayerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppLanguageScreen(navController: NavController) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val context = LocalContext.current
    val changeLanguageEnabled = remember {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
    }

    RevoMusicPlayerTheme{
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    LargeTopAppBar(
                        title = { Text(text = stringResource(id = R.string.str_appLanguage)) },
                        navigationIcon = {
                            IconButton(
                                onClick = { navController.navigateUp() }
                            ) {
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
                    LazyColumn(
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        item {
                            PixelyListItem(
                                modifier = Modifier
                                    .clickableRowItem()
                                    .clickable(
                                        enabled = changeLanguageEnabled
                                    ) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                            val intent = Intent(Settings.ACTION_APP_LOCALE_SETTINGS)
                                            intent.data =
                                                Uri.parse("package:com.alessiocameroni.revomusicplayer")
                                            context.startActivity(intent)
                                        }
                                    },
                                stringHeadlineText = stringResource(id = R.string.app_launguage),
                                trailingUnit = {
                                    if(changeLanguageEnabled) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_baseline_arrow_forward_24),
                                            contentDescription = stringResource(id = R.string.desc_appLanguage)
                                        )
                                    }
                                }
                            )
                        }

                        item {
                            PixelySupportInfoText(
                                modifier = Modifier,
                                stringText =
                                    stringResource(id = R.string.desc_infoAppLanguage) + "\n\n" +
                                    stringResource(id = R.string.desc_infoSystemAppLanguage),
                                painterInfoIcon = painterResource(id = R.drawable.ic_outlined_info_24),
                                descriptionInfoIcon = stringResource(id = R.string.desc_infoSystemAppLanguage)
                            )
                        }
                    }
                }
            )
        }
    }
}