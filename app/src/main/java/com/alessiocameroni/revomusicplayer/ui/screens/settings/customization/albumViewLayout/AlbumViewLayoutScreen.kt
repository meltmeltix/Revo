package com.alessiocameroni.revomusicplayer.ui.screens.settings.customization.albumViewLayout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.alessiocameroni.pixely_components.PixelySupportInfoText
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.data.classes.album.HeaderLayout
import com.alessiocameroni.revomusicplayer.ui.theme.RevoMusicPlayerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumViewLayoutScreen(
    navController: NavController,
    viewModel: AlbumViewLayoutViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val selectedLayout by viewModel.headerLayout.collectAsStateWithLifecycle(HeaderLayout.REVO)

    RevoMusicPlayerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    AlbumViewLayoutTopActionBar(
                        navController,
                        scrollBehavior
                    )
                },
                content = { padding ->
                    LazyColumn(
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        item {
                            AlbumViewLayoutHeaderPreview(
                                modifier = Modifier.padding(horizontal = 15.dp),
                                selectedOption = selectedLayout
                            )
                        }

                        item {
                            Row(modifier = Modifier) {
                                PixelySupportInfoText(
                                    stringText = stringResource(id = R.string.info_albumViewLayout)
                                )
                            }
                        }

                        item {
                            Column(
                                modifier = Modifier.selectableGroup(),
                                verticalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                LayoutSelector(
                                    options = HeaderLayout.values(),
                                    selected = selectedLayout,
                                    onSelected = { viewModel.setHeaderLayout(it) },
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}