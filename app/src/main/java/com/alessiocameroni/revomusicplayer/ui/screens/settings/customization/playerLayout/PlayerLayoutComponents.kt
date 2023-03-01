package com.alessiocameroni.revomusicplayer.ui.screens.settings.customization.playerLayout

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
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alessiocameroni.pixely_components.PixelyListItem
import com.alessiocameroni.revomusicplayer.R

/**
 * Scaffold components
 */
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

/**
 * Screen Components
 */
@Composable
fun PlayerLayoutPreviewHeader(
    modifier: Modifier = Modifier,
    selectedOption: String,
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

        when {
            darkTheme && selectedOption == stringResource(id = R.string.str_left) -> {
                Image(
                    modifier = imageModifier,
                    painter = painterResource(id = R.drawable.ill_dark_left_player_controls),
                    contentDescription = "String"
                )
            }
            darkTheme && selectedOption == stringResource(id = R.string.str_center) -> {
                Image(
                    modifier = imageModifier,
                    painter = painterResource(id = R.drawable.ill_dark_center_player_controls),
                    contentDescription = "String"
                )
            }
            darkTheme && selectedOption == stringResource(id = R.string.str_right) -> {
                Image(
                    modifier = imageModifier,
                    painter = painterResource(id = R.drawable.ill_dark_right_player_controls),
                    contentDescription = "String"
                )
            }
            !darkTheme && selectedOption == stringResource(id = R.string.str_left) -> {
                Image(
                    modifier = imageModifier,
                    painter = painterResource(id = R.drawable.ill_light_left_player_controls),
                    contentDescription = "String"
                )
            }
            !darkTheme && selectedOption == stringResource(id = R.string.str_center) -> {
                Image(
                    modifier = imageModifier,
                    painter = painterResource(id = R.drawable.ill_light_center_player_controls),
                    contentDescription = "String"
                )
            }
            !darkTheme && selectedOption == stringResource(id = R.string.str_right) -> {
                Image(
                    modifier = imageModifier,
                    painter = painterResource(id = R.drawable.ill_light_right_player_controls),
                    contentDescription = "String"
                )
            }
        }
    }
}

@Composable
fun LayoutSelector(
    options: List<String>,
    selected: String,
    onSelected: (String) -> Unit,
    viewModel: PlayerLayoutViewModel
) {
    options.forEach { text ->
        key(text) {
            PixelyListItem(
                modifier = Modifier
                    .selectable(
                        selected = (text == selected),
                        onClick = {
                            onSelected(text)
                            viewModel.setLayout(
                                options.indexOf(text)
                            )
                        },
                        role = Role.RadioButton
                    ),
                headlineTextString = text,
                leadingContent = {
                    RadioButton(
                        selected = (text == selected),
                        onClick = null
                    )
                }
            )
        }
    }
}