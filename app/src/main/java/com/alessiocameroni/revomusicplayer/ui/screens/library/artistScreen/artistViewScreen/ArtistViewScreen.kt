package com.alessiocameroni.revomusicplayer.ui.screens.library.artistScreen.artistViewScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.alessiocameroni.revomusicplayer.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistViewScreen(
    navController: NavController,
    navControllerBottomBar: NavHostController,
) {
    val expanded = remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ArtistViewTopAppBar(
                modifier = Modifier,
                navControllerBottomBar = navControllerBottomBar,
                expanded = expanded,
                scrollBehavior = scrollBehavior
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {

            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArtistViewTopAppBar(
    modifier: Modifier,
    navControllerBottomBar: NavHostController,
    expanded: MutableState<Boolean>,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    TopAppBar(
        title = {  },
        modifier = modifier,
        navigationIcon = {
            IconButton(
                onClick = { navControllerBottomBar.navigateUp() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                    contentDescription = stringResource(id = R.string.desc_back)
                )
            }
        },
        actions = {
            Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                IconButton(onClick = { expanded.value = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                        contentDescription = stringResource(id = R.string.str_settings)
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior,
    )
}