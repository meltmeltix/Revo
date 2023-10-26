package com.meltix.revo.ui.screens.library.albumScreen

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
fun AlbumsScreen(
    rootNavController: NavController,
    libraryNavController: NavHostController,
    viewModel: AlbumViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val windowClass = calculateWindowSizeClass(activity)

    val contentState by viewModel.contentState.collectAsStateWithLifecycle(ContentState.LOADING)
    val albumList by viewModel.albums.collectAsStateWithLifecycle(emptyList())

    RevoTheme {
    
    }
}

/*
private fun LazyListScope.albumList(albums: List<Album>, onMenuOptionClick: (String) -> Unit) {
    itemsIndexed(albums) { key, item ->
        key(key) {
            PixelyListItem(
                modifier = Modifier
                    .clickable{ onMenuOptionClick(DetailsScreens.AlbumDetails.route + "/${item.albumId}") },
                headlineTextString = item.albumTitle,
                largeHeadline = false,
                maxHeadlineLines = 1,
                supportingTextString = item.artist,
                maxSupportingLines = 1,
                leadingContent = {
                    SmallImageContainer(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        painterPlaceholder =
                        painterResource(id = R.drawable.ic_outlined_album_24),
                        leadingUnit = {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(item.albumCoverUri)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = stringResource(id = R.string.str_albumImage)
                            )
                        },
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
                            DropdownMenuItem(
                                text = { Text(text = stringResource(id = R.string.str_goToArtist)) },
                                onClick = {
                                    onMenuOptionClick(DetailsScreens.ArtistDetails.route + "/$item.artistId")
                                    expanded.value = false
                                }
                            )
                        }
                    }
                }
            )
        }
    }
}*/
