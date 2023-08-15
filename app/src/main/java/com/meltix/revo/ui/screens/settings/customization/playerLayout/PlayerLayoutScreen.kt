package com.meltix.revo.ui.screens.settings.customization.playerLayout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.meltix.pixely_components.PixelySupportInfoText
import com.meltix.revo.R
import com.meltix.revo.data.classes.player.PlayerLayout
import com.meltix.revo.ui.theme.RevoMusicPlayerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerLayoutScreen(
    navController: NavHostController,
    viewModel: PlayerLayoutViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val selectedLayout by viewModel.playerLayout.collectAsStateWithLifecycle(PlayerLayout.CENTER)

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
                            PlayerLayoutPreviewHeader(selectedOption = selectedLayout)
                        }

                        Row( modifier = Modifier ) {
                            PixelySupportInfoText(
                                stringText = stringResource(id = R.string.info_layoutPlayer)
                            )
                        }
                        
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
            )
        }
    }
}