package com.meltix.revo.ui.screens.settings.customization.playerLayout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.meltix.pixely_components.PixelyListItem
import com.meltix.revo.R
import com.meltix.revo.data.classes.player.PlayerLayout
import com.meltix.revo.util.functions.selectPlayerLayoutString

// Scaffold components
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerLayoutTopActionBar(
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior
) {
    LargeTopAppBar(
        title = { Text(text = stringResource(id = R.string.str_layoutPlayer)) },
        navigationIcon = {
            IconButton(
                onClick = { navController.navigateUp() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                    contentDescription = stringResource(id = R.string.str_back)
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

// Screen components
@Composable
fun PlayerLayoutPreviewHeader(
    modifier: Modifier = Modifier,
    selectedOption: PlayerLayout,
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .fillMaxWidth()
            .height(250.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        val darkTheme = isSystemInDarkTheme()
        val imageModifier = Modifier
            .width(240.dp)
            .height(200.dp)

        when(selectedOption) {
            PlayerLayout.LEFT -> {
                when(darkTheme) {
                    true -> {
                        Image(
                            modifier = imageModifier,
                            painter = painterResource(id = R.drawable.ill_dark_left_player_controls),
                            contentDescription = stringResource(id = R.string.str_left)
                        )
                    }
                    false -> {
                        Image(
                            modifier = imageModifier,
                            painter = painterResource(id = R.drawable.ill_light_left_player_controls),
                            contentDescription = stringResource(id = R.string.str_left)
                        )
                    }
                }
            }
            PlayerLayout.CENTER -> {
                when(darkTheme) {
                    true -> {
                        Image(
                            modifier = imageModifier,
                            painter = painterResource(id = R.drawable.ill_dark_center_player_controls),
                            contentDescription = stringResource(id = R.string.str_center)
                        )
                    }
                    false -> {
                        Image(
                            modifier = imageModifier,
                            painter = painterResource(id = R.drawable.ill_light_center_player_controls),
                            contentDescription = stringResource(id = R.string.str_center)
                        )
                    }
                }
            }
            PlayerLayout.RIGHT -> {
                when(darkTheme) {
                    true -> {
                        Image(
                            modifier = imageModifier,
                            painter = painterResource(id = R.drawable.ill_dark_right_player_controls),
                            contentDescription = stringResource(id = R.string.str_right)
                        )
                    }
                    false -> {
                        Image(
                            modifier = imageModifier,
                            painter = painterResource(id = R.drawable.ill_light_right_player_controls),
                            contentDescription = stringResource(id = R.string.str_right)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LayoutSelector(
    options: Array<PlayerLayout>,
    selected: PlayerLayout,
    onSelected: (PlayerLayout) -> Unit,
) {
    options.forEach { option ->
        PixelyListItem(
            modifier = Modifier
                .selectable(
                    selected = (option == selected),
                    onClick = { onSelected(option) },
                    role = Role.RadioButton
                ),
            headlineTextString = selectPlayerLayoutString(option),
            leadingContent = { RadioButton(selected = (option == selected), onClick = null) }
        )
    }
}