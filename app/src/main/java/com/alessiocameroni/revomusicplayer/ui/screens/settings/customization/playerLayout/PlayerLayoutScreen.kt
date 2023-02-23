package com.alessiocameroni.revomusicplayer.ui.screens.settings.customization.playerLayout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.alessiocameroni.pixely_components.PixelyListItem
import com.alessiocameroni.pixely_components.PixelySupportInfoText
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.data.repositories.CustomizationPrefsRepository
import com.alessiocameroni.revomusicplayer.ui.theme.RevoMusicPlayerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerLayoutScreen(
    navController: NavHostController,
    viewModel: PlayerLayoutViewModel = viewModel(
        factory = PlayerLayoutViewModelFactory(
            CustomizationPrefsRepository(LocalContext.current)
        )
    )
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val radioOptions = listOf(
        stringResource(id = R.string.str_left),
        stringResource(id = R.string.str_center),
        stringResource(id = R.string.str_right),
    )

    LaunchedEffect(Unit) {
        viewModel.initializeDataStore()
    }

    val (selectedOption, onOptionSelected) = remember {
        mutableStateOf(radioOptions[viewModel.playerLayout.value ?: 0])
    }

    RevoMusicPlayerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    LargeTopAppBar(
                        title = { Text(text = stringResource(id = R.string.str_layoutPlayer)) },
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
                    Column(
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Row( modifier = Modifier.padding(horizontal = 15.dp) ) {
                            PlayerLayoutPreviewHeader(selectedOption = selectedOption)
                        }

                        Row( modifier = Modifier ) {
                            PixelySupportInfoText(
                                stringText = stringResource(id = R.string.desc_layoutPlayer)
                            )
                        }
                        
                        Column(
                            modifier = Modifier.selectableGroup(),
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            radioOptions.forEach { text ->
                                Row(
                                    modifier = Modifier
                                        .selectable(
                                            selected = (text == selectedOption),
                                            onClick = {
                                                onOptionSelected(text)
                                            },
                                            role = Role.RadioButton
                                        ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    PixelyListItem(
                                        headlineTextString = text,
                                        leadingContent = {
                                            RadioButton(
                                                selected = (text == selectedOption),
                                                onClick = null
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}