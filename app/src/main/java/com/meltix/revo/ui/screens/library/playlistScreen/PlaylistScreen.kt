package com.meltix.revo.ui.screens.library.playlistScreen

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.meltix.revo.util.functions.findActivity

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun PlaylistsScreen(
    rootNavController: NavController,
    libraryNavController: NavHostController,
    viewModel: PlaylistViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowClass = calculateWindowSizeClass(activity)

    
}

/*
private fun LazyListScope.playlistList(onMenuOptionClick: (String) -> Unit) {
    items(2) {
        PixelyListItem(
            modifier = Modifier.clickable { onMenuOptionClick(PlaylistDetails.route) },
            headlineTextString = "Title",
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
}*/
