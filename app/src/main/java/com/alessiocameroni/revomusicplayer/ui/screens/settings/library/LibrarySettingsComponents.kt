package com.alessiocameroni.revomusicplayer.ui.screens.settings.library

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.alessiocameroni.revomusicplayer.R

/**
 * Scaffold Components
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LibrarySettingsTopActionBar(
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
                    contentDescription = stringResource(id = R.string.desc_back)
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

/**
 * Screen Components
 */
