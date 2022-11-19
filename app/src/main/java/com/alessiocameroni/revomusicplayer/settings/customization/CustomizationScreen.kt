package com.alessiocameroni.revomusicplayer.settings.customization

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.data.modifiers.clickableRowItem
import com.alessiocameroni.revomusicplayer.data.navigation.CustomizationSettingsScreens
import com.alessiocameroni.revomusicplayer.data.viewmodels.CustomizationViewModel
import com.alessiocameroni.revomusicplayer.settings.components.SectionTitle
import com.alessiocameroni.revomusicplayer.settings.components.SettingsTextItem
import com.alessiocameroni.revomusicplayer.ui.theme.RevoMusicPlayerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomizationScreen(
    navController: NavController,
    viewModel: CustomizationViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    RevoMusicPlayerTheme {
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
                            SectionTitle(
                                stringTitle = "User Interface",
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }

                        item {
                            SettingsTextItem(
                                modifier = Modifier
                                    .clickableRowItem()
                                    .clickable {
                                        navController.navigate(
                                            CustomizationSettingsScreens.PlayerLayoutScreen.route
                                        )
                                    },
                                stringMainTitle = stringResource(id = R.string.str_layoutPlayer),
                                stringSubtitle = stringResource(id = R.string.desc_layoutPlayer)
                                /*TODO(Put "dynamic" string here with layout, and both strings)*/
                            )
                        }
                    }
                }
            )
        }
    }
}