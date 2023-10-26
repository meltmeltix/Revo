package com.meltix.revo.ui.screens.library.artistScreen

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.meltix.revo.data.classes.ContentState
import com.meltix.revo.ui.theme.RevoTheme
import com.meltix.revo.util.functions.findActivity

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun ArtistsScreen(
    rootNavController: NavController,
    libraryNavController: NavHostController,
    viewModel: ArtistViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowClass = calculateWindowSizeClass(activity)

    val contentState by viewModel.contentState.collectAsStateWithLifecycle(ContentState.LOADING)
    val artistList by viewModel.artists.collectAsStateWithLifecycle(emptyList())

    RevoTheme {
    
    }
}

/*
private fun LazyListScope.artistList(artists: List<Artist>, onMenuOptionClick: (String) -> Unit) {
    itemsIndexed(artists) { _, item ->
        PixelyListItem(
            modifier = Modifier
                .clickable { onMenuOptionClick(DetailsScreens.ArtistDetails.route + "/${item.artistId}") }
                .padding(vertical = 10.dp),
            headlineTextString = item.artist,
            largeHeadline = false,
            maxHeadlineLines = 1,
            leadingContent = {
                SmallImageContainer(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .clip(CircleShape),
                    painterPlaceholder = painterResource(id = R.drawable.ic_outlined_artist_24),
                    leadingUnit = {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(item.albumCoverUri)
                                .crossfade(true)
                                .build(),
                            contentDescription = stringResource(id = R.string.str_albumImage),
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                )
            },
            trailingContent = {
                val expanded = remember { mutableStateOf(false) }
            
                Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                    IconButton(onClick = { expanded.value = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                            contentDescription = stringResource(id = R.string.str_moreOptions)
                        )
                    }
                
                    RoundedDropDownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false }
                    ) {
                    
                    }
                }
            }
        )
    }
}*/
