package com.meltix.revo.ui.screens.settings.customization.playerLayout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.meltix.pixely_components.PixelySupportInfoText
import com.meltix.revo.R
import com.meltix.revo.data.classes.player.PlayerLayout
import com.meltix.revo.ui.theme.RevoTheme
import com.meltix.revo.util.functions.findActivity

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun PlayerLayoutScreen(
    navController: NavController,
    viewModel: PlayerLayoutViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowClass = calculateWindowSizeClass(activity)
    
    val selectedLayout by viewModel.playerLayout.collectAsStateWithLifecycle(PlayerLayout.CENTER)

    RevoTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            PlayerLytLayout(
                windowClass = windowClass,
                onBackButtonClick = { navController.navigateUp() }
            ) { itemList(viewModel, selectedLayout) }
        }
    }
}

private fun LazyListScope.itemList(viewModel: PlayerLayoutViewModel, selectedLayout: PlayerLayout) {
    item {
        PlayerLayoutPreviewHeader(
            modifier = Modifier.padding(horizontal = 15.dp),
            selectedOption = selectedLayout
        )
    }
    
    item { PixelySupportInfoText(stringText = stringResource(id = R.string.info_layoutPlayer)) }
    
    item {
        Column(
            modifier = Modifier.selectableGroup(),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            LayoutSelector(
                options = PlayerLayout.values(),
                selected = selectedLayout,
                onSelected = { viewModel.setLayout(it) },
            )
        }
    }
}