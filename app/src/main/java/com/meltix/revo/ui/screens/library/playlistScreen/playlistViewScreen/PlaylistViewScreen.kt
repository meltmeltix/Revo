package com.meltix.revo.ui.screens.library.playlistScreen.playlistViewScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.meltix.revo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistViewScreen(
    navController: NavController,
    navControllerBottomBar: NavHostController,
) {
    val expanded = remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            PlaylistViewTopAppBar(
                modifier = Modifier,
                navControllerBottomBar = navControllerBottomBar,
                expanded = expanded,
                scrollBehavior = scrollBehavior
            )
        },
        content = { padding ->
            LazyVerticalGrid(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                columns = GridCells.Fixed(1),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ){

            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlaylistViewTopAppBar(
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
                    contentDescription = stringResource(id = R.string.str_back)
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
        scrollBehavior = scrollBehavior
    )
}