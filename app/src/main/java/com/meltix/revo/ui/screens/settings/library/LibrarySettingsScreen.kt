package com.meltix.revo.ui.screens.settings.library

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.meltix.pixely_components.PixelyListItem
import com.meltix.pixely_components.PixelySectionTitle
import com.meltix.revo.R
import com.meltix.revo.ui.theme.RevoTheme
import com.meltix.revo.util.functions.findActivity

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun LibrarySettingsScreen(
    navController: NavController,
    viewModel: LibrarySettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowClass = calculateWindowSizeClass(activity)

    RevoTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LibrarySettingsLayout(
                windowWidthClass = windowClass.widthSizeClass,
                onNavigateUp = { navController.navigateUp() },
            ) { itemList(viewModel) }
        }
    }
}

private fun LazyListScope.itemList(viewModel: LibrarySettingsViewModel) {
    item { PixelySectionTitle(stringTitle = stringResource(id = R.string.str_spotify)) }
    
    item {
        val checked by viewModel.spotifyEnabledState.collectAsStateWithLifecycle(false)
    
        PixelyListItem(
            modifier = Modifier.selectable(selected = checked, onClick = { viewModel.setSpotifyVisibility(!checked) }),
            headlineTextString = stringResource(id = R.string.str_showSpotifyIntegration),
            supportingTextString = stringResource(id = R.string.desc_showSpotifyIntegration),
            trailingContent = { Switch(checked = checked, onCheckedChange = null) },
            trailingContentDivider = true
        )
    }
}