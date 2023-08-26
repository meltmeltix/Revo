package com.meltix.revo.ui.screens.settings.customization.albumDetailsLayout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.meltix.pixely_components.PixelyListItem
import com.meltix.revo.R
import com.meltix.revo.data.classes.album.HeaderLayout
import com.meltix.revo.util.functions.selectAlbumViewHeaderLayoutString
import com.meltix.revo.util.functions.selectAlbumViewHeaderLayoutSupportingString

// Scaffold components
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumViewLayoutTopActionBar(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior
) {
    LargeTopAppBar(
        title = { Text(text = stringResource(id = R.string.str_albumViewLayout)) },
        navigationIcon = {
            IconButton(
                onClick = { navController.navigateUp() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                    contentDescription = stringResource(id = R.string.str_back)
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun LayoutSelector(
    options: Array<HeaderLayout>,
    selected: HeaderLayout,
    onSelected: (HeaderLayout) -> Unit
) {
    options.forEach { option ->
        PixelyListItem(
            modifier = Modifier
                .selectable(
                    selected = (option == selected),
                    onClick = { onSelected(option) },
                    role = Role.RadioButton
                ),
            headlineTextString = selectAlbumViewHeaderLayoutString(option),
            supportingTextString = selectAlbumViewHeaderLayoutSupportingString(option),
            leadingContent = { RadioButton(selected = (option == selected), onClick = null) }
        )
    }
}

// Screen components
@Composable
fun AlbumViewLayoutHeaderPreview(
    modifier: Modifier = Modifier,
    windowClass: WindowSizeClass,
    selectedOption: HeaderLayout
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .fillMaxWidth()
            .height(300.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        val darkTheme = isSystemInDarkTheme()
        val imageModifier = Modifier.height(280.dp).fillMaxWidth()

        when(windowClass.widthSizeClass) {
            WindowWidthSizeClass.Medium -> {
                when(selectedOption) {
                    HeaderLayout.REVO -> {
                        when(darkTheme) {
                            true -> {
                                Image(
                                    modifier = imageModifier,
                                    painter = painterResource(id = R.drawable.ill_revo_medium_dark),
                                    contentDescription = stringResource(id = R.string.app_name)
                                )
                            }
                            false -> {
                                Image(
                                    modifier = imageModifier,
                                    painter = painterResource(id = R.drawable.ill_revo_medium_light),
                                    contentDescription = stringResource(id = R.string.app_name)
                                )
                            }
                        }
                    }
                    HeaderLayout.FRUIT_MUSIC -> {
                        when(darkTheme) {
                            true -> {
                                Image(
                                    modifier = imageModifier,
                                    painter = painterResource(id = R.drawable.ill_fruit_medium_dark),
                                    contentDescription = stringResource(id = R.string.str_fruitMusicLayout)
                                )
                            }
                            false -> {
                                Image(
                                    modifier = imageModifier,
                                    painter = painterResource(id = R.drawable.ill_fruit_medium_light),
                                    contentDescription = stringResource(id = R.string.str_fruitMusicLayout)
                                )
                            }
                        }
                    }
                    HeaderLayout.MINIMAL -> {
                        when(darkTheme) {
                            true -> {
                                Image(
                                    modifier = imageModifier,
                                    painter = painterResource(id = R.drawable.ill_minimal_medium_dark),
                                    contentDescription = stringResource(id = R.string.str_minimalLayout)
                                )
                            }
                            false -> {
                                Image(
                                    modifier = imageModifier,
                                    painter = painterResource(id = R.drawable.ill_minimal_medium_light),
                                    contentDescription = stringResource(id = R.string.str_minimalLayout)
                                )
                            }
                        }
                    }
                }
            }
            else -> {
                when(selectedOption) {
                    HeaderLayout.REVO -> {
                        when(darkTheme) {
                            true -> {
                                Image(
                                    modifier = imageModifier,
                                    painter = painterResource(id = R.drawable.ill_revo_compact_dark),
                                    contentDescription = stringResource(id = R.string.app_name)
                                )
                            }
                            false -> {
                                Image(
                                    modifier = imageModifier,
                                    painter = painterResource(id = R.drawable.ill_revo_compact_light),
                                    contentDescription = stringResource(id = R.string.app_name)
                                )
                            }
                        }
                    }
                    HeaderLayout.FRUIT_MUSIC -> {
                        when(darkTheme) {
                            true -> {
                                Image(
                                    modifier = imageModifier,
                                    painter = painterResource(id = R.drawable.ill_fruit_compact_dark),
                                    contentDescription = stringResource(id = R.string.str_fruitMusicLayout)
                                )
                            }
                            false -> {
                                Image(
                                    modifier = imageModifier,
                                    painter = painterResource(id = R.drawable.ill_fruit_compact_light),
                                    contentDescription = stringResource(id = R.string.str_fruitMusicLayout)
                                )
                            }
                        }
                    }
                    HeaderLayout.MINIMAL -> {
                        when(darkTheme) {
                            true -> {
                                Image(
                                    modifier = imageModifier,
                                    painter = painterResource(id = R.drawable.ill_minimal_compact_dark),
                                    contentDescription = stringResource(id = R.string.str_minimalLayout)
                                )
                            }
                            false -> {
                                Image(
                                    modifier = imageModifier,
                                    painter = painterResource(id = R.drawable.ill_minimal_compact_light),
                                    contentDescription = stringResource(id = R.string.str_minimalLayout)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}