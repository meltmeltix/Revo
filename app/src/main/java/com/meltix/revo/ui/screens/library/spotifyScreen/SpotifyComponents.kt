package com.meltix.revo.ui.screens.library.spotifyScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.meltix.pixely_components.RoundedDropDownMenu
import com.meltix.revo.R
import com.meltix.revo.ui.components.topAppBarColorOnWindowSize
import com.meltix.revo.ui.components.topAppBarInsetsOnWindowsSize
import com.meltix.revo.ui.navigation.RootScreens

// Scaffold components
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpotifyTopActionBar(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior,
    windowClass: WindowSizeClass
) {
    val expandedMenu = remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = stringResource(id = R.string.str_spotify)) },
        actions = {
            IconButton(
                onClick = { navController.navigate(RootScreens.Search.route) }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_search_24),
                    contentDescription = stringResource(id = R.string.str_search)
                )
            }

            Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                IconButton(onClick = { expandedMenu.value = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                        contentDescription = stringResource(id = R.string.str_settings)
                    )
                }

                TopBarDropDownMenu(
                    expanded = expandedMenu,
                    navController = navController
                )
            }
        },
        windowInsets = topAppBarInsetsOnWindowsSize(windowClass),
        colors = topAppBarColorOnWindowSize(windowClass),
        scrollBehavior = scrollBehavior,
    )
}

@Composable
private fun TopBarDropDownMenu(
    expanded: MutableState<Boolean>,
    navController: NavController
) {
    RoundedDropDownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false }
    ) {
        DropdownMenuItem(
            text = {
                Text(text = stringResource(id = R.string.str_settings))
            },
            onClick = {
                navController.navigate(RootScreens.SettingsGraph.route)
                expanded.value = false
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_outlined_settings_24),
                    contentDescription = stringResource(id = R.string.str_settings)
                )
            }
        )
    }
}