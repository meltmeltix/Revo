package com.meltix.revo.ui.screens.library.playlistScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.meltix.pixely_components.PixelyListItem
import com.meltix.revo.R
import com.meltix.revo.ui.components.SmallImageContainer
import com.meltix.revo.ui.components.contentModifier
import com.meltix.revo.ui.components.scrollBehaviorOnWindowSize
import com.meltix.revo.ui.components.surfaceColorOnWindowSize
import com.meltix.revo.ui.navigation.DetailsScreens.PlaylistDetails
import com.meltix.revo.util.functions.findActivity

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun PlaylistsScreen(
    rootNavController: NavController,
    libraryNavController: NavHostController,
    viewModel: PlaylistViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowClass = calculateWindowSizeClass(activity)
    val scrollBehavior = scrollBehaviorOnWindowSize(windowClass)

    val openDialog = remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { PlaylistTopActionbar(rootNavController, scrollBehavior, viewModel, windowClass) },
        floatingActionButton = { AddPlaylistFAB(openDialog) },
        floatingActionButtonPosition = FabPosition.Center,
        containerColor = surfaceColorOnWindowSize(windowClass),
        content = { padding ->
            if(openDialog.value) {
                AddPlaylistDialog(
                    modifier = Modifier,
                    openDialog = openDialog
                )
            }

            LazyColumn(
                modifier = Modifier.contentModifier(windowClass, padding),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                contentPadding = PaddingValues(bottom = 128.dp + 70.dp),
            ) {
                items(2) {
                    Row(modifier = Modifier
                        .clickable { libraryNavController.navigate(PlaylistDetails.route) }
                    ) {
                        PixelyListItem(
                            headlineTextString = "Placeholder",
                            largeHeadline = false,
                            maxHeadlineLines = 1,
                            supportingTextString = "Placeholder",
                            maxSupportingLines = 1,
                            leadingContent = {
                                SmallImageContainer(
                                    modifier = Modifier
                                        .padding(horizontal = 5.dp)
                                        .clip(CircleShape),
                                    painterPlaceholder =
                                        painterResource(id = R.drawable.ic_baseline_playlist_play_24)
                                ) {

                                }

                                Icon(
                                    painter =
                                    painterResource(id = R.drawable.ic_baseline_playlist_play_24),
                                    contentDescription =
                                    stringResource(id = R.string.str_playlists)
                                )
                            },
                            trailingContent = {
                                val expandedItemMenu = remember { mutableStateOf(false) }

                                Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                                    IconButton(onClick = { expandedItemMenu.value = true }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                                            contentDescription = stringResource(id = R.string.str_moreOptions)
                                        )
                                    }

                                    PlaylistItemDropDownMenu(expanded = expandedItemMenu)
                                }
                            }
                        )
                    }
                }
            }
        }
    )
}