package com.meltix.revo.ui.screens.library.playlistScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.meltix.revo.ui.navigation.DetailsScreens.PlaylistDetails
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

    PlaylistLayout(
        windowClass = windowClass,
        viewModel = viewModel,
        onNavigate = { rootNavController.navigate(it) }
    ) { playlistList { libraryNavController.navigate(it) } }
}

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
}