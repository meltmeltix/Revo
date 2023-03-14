package com.alessiocameroni.revomusicplayer.ui.screens.settings.library

import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.alessiocameroni.pixely_components.PixelyListItem
import com.alessiocameroni.revomusicplayer.R

// Scaffold components
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibrarySettingsTopActionBar(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior
) {
    LargeTopAppBar(
        title = { Text(text = stringResource(id = R.string.str_library)) },
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
fun SpotifyVisibilitySelection(
    checked: Boolean,
    onChecked: (Boolean) -> Unit,
    viewModel: LibrarySettingsViewModel
) {
    PixelyListItem(
        modifier = Modifier
            .selectable(
                selected = checked,
                onClick = {
                    onChecked(!checked)
                    viewModel.setSpotifyVisibility(
                        !checked
                    )
                }
            ),
        headlineTextString = stringResource(id = R.string.str_showSpotifyIntegration),
        supportingTextString = stringResource(id = R.string.desc_showSpotifyIntegration),
        trailingContent = { Switch(checked = checked, onCheckedChange = null) },
        trailingContentDivider = true
    )
}