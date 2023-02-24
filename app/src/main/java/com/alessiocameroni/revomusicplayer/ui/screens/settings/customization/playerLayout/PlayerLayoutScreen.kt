package com.alessiocameroni.revomusicplayer.ui.screens.settings.customization.playerLayout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alessiocameroni.pixely_components.PixelyListItem
import com.alessiocameroni.pixely_components.PixelySupportInfoText
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.ui.theme.RevoMusicPlayerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerLayoutScreen(
    navController: NavHostController,
    viewModel: PlayerLayoutViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val radioOptions = listOf(
        stringResource(id = R.string.str_left),
        stringResource(id = R.string.str_center),
        stringResource(id = R.string.str_right),
    )

    val (selectedOption, onOptionSelected) = remember {
        mutableStateOf(radioOptions[viewModel.playerLayout.value])
    }

    RevoMusicPlayerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    PlayerLayoutTopActionBar(
                        navController,
                        scrollBehavior
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
                                PixelyListItem(
                                    modifier = Modifier
                                        .selectable(
                                            selected = (text == selectedOption),
                                            onClick = {
                                                onOptionSelected(text)
                                                viewModel.saveSelection(
                                                    radioOptions.indexOf(text)
                                                )
                                            },
                                            role = Role.RadioButton
                                        ),
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
            )
        }
    }
}