package com.alessiocameroni.revomusicplayer.ui.screens.library.albumScreen.albumViewScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.alessiocameroni.pixely_components.PixelySectionTitle
import com.alessiocameroni.revomusicplayer.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumViewScreen(
    navController: NavController,
    navControllerBottomBar: NavHostController,
) {
    val expanded = remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {  },
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
                }, scrollBehavior = scrollBehavior
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

                }

                item {
                    PixelySectionTitle(
                        modifier = Modifier, 
                        stringTitle = stringResource(id = R.string.str_songs)
                    )
                }
            }
        }
    )
}