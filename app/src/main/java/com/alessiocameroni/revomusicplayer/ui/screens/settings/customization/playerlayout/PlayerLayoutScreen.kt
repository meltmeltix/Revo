package com.alessiocameroni.revomusicplayer.ui.screens.settings.customization.playerlayout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.layoutId
import androidx.navigation.NavHostController
import com.alessiocameroni.pixely_components.PixelyListItem
import com.alessiocameroni.pixely_components.PixelySupportInfoText
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.ui.theme.RevoMusicPlayerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerLayoutScreen(navController: NavHostController) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val radioOptions = listOf(
        stringResource(id = R.string.str_left),
        stringResource(id = R.string.str_center),
        stringResource(id = R.string.str_right),
    )

    val (selectedOption, onOptionSelected) = remember {
        mutableStateOf(radioOptions[1])
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
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 25.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(shape = RoundedCornerShape(12.dp))
                                    .background(MaterialTheme.colorScheme.surfaceVariant)
                                    .fillMaxWidth()
                                    .height(250.dp),
                                contentAlignment = Alignment.TopCenter
                            ) {
                                PlayerLayoutImagePreview(
                                    modifier = Modifier
                                        .layoutId("ImagePreview")
                                        .width(240.dp)
                                        .height(200.dp),
                                    selectedOption = selectedOption
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            PixelySupportInfoText(
                                modifier = Modifier,
                                stringText = stringResource(id = R.string.desc_layoutPlayer)
                            )
                        }
                        
                        Column(
                            modifier = Modifier
                                .layoutId("ColumnSelection")
                                .fillMaxWidth()
                                .selectableGroup(),
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            radioOptions.forEach { text ->
                                Row(
                                    modifier = Modifier
                                        .selectable(
                                            selected = (text == selectedOption),
                                            onClick = { onOptionSelected(text) },
                                            role = Role.RadioButton
                                        ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    PixelyListItem(
                                        modifier = Modifier,
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

@Composable
fun PlayerLayoutImagePreview(
    modifier: Modifier,
    selectedOption: String
) {
    val darkTheme = isSystemInDarkTheme()

    when {
        darkTheme && selectedOption == stringResource(id = R.string.str_left) -> {
            Image(
                modifier = modifier,
                painter = painterResource(id = R.drawable.ill_dark_left_player_controls),
                contentDescription = "String"
            )
        }
        darkTheme && selectedOption == stringResource(id = R.string.str_center) -> {
            Image(
                modifier = modifier,
                painter = painterResource(id = R.drawable.ill_dark_center_player_controls),
                contentDescription = "String"
            )
        }
        darkTheme && selectedOption == stringResource(id = R.string.str_right) -> {
            Image(
                modifier = modifier,
                painter = painterResource(id = R.drawable.ill_dark_right_player_controls),
                contentDescription = "String"
            )
        }
        !darkTheme && selectedOption == stringResource(id = R.string.str_left) -> {
            Image(
                modifier = modifier,
                painter = painterResource(id = R.drawable.ill_light_left_player_controls),
                contentDescription = "String"
            )
        }
        !darkTheme && selectedOption == stringResource(id = R.string.str_center) -> {
            Image(
                modifier = modifier,
                painter = painterResource(id = R.drawable.ill_light_center_player_controls),
                contentDescription = "String"
            )
        }
        !darkTheme && selectedOption == stringResource(id = R.string.str_right) -> {
            Image(
                modifier = modifier,
                painter = painterResource(id = R.drawable.ill_light_right_player_controls),
                contentDescription = "String"
            )
        }
    }
}