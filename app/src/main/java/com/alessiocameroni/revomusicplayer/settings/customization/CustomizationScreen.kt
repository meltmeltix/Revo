package com.alessiocameroni.revomusicplayer.settings.customization

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.navigation.Screens
import com.alessiocameroni.revomusicplayer.settings.customization.components.PlayerLayoutDialog
import com.alessiocameroni.revomusicplayer.settings.main.components.SectionTitle
import com.alessiocameroni.revomusicplayer.settings.main.components.SettingsActionItem
import com.alessiocameroni.revomusicplayer.ui.theme.RevoMusicPlayerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LooksScreen(navController: NavController) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    RevoMusicPlayerTheme{
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    LargeTopAppBar(
                        title = { Text(text = stringResource(id = R.string.str_customization)) },
                        navigationIcon = {
                            IconButton(
                                onClick = { navController.navigate(Screens.SettingsScreen.route) }
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
                    Column(
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ){
                        Row( modifier = Modifier.fillMaxWidth() ) {
                            SectionTitle(
                                stringTitle = "User Interface",
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }

                        Row( modifier = Modifier.fillMaxWidth() ) {
                            val openDialog = remember { mutableStateOf(false) }

                            SettingsActionItem(
                                stringTitleItem = stringResource(id = R.string.str_layoutplayer),
                                stringSubtitleItem = stringResource(id = R.string.desc_layoutplayer),
                                unitAction = {  },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(22.dp))
                                    .clickable { openDialog.value = true }
                            )

                            if (openDialog.value) {
                                PlayerLayoutDialog(
                                    modifier = Modifier
                                        .clip(shape = RoundedCornerShape(24.dp))
                                        .width(560.dp),
                                    openDialog = openDialog
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}