package com.alessiocameroni.revomusicplayer.mainscreens

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.SearchActivity
import com.alessiocameroni.revomusicplayer.SettingsActivity
import com.alessiocameroni.revomusicplayer.classes.LibraryItemData
import com.alessiocameroni.revomusicplayer.components.lists.OneColumnItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TracksScreen() {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val items by remember {
        mutableStateOf(
            (1..20).map {
                LibraryItemData(
                    stringTitle = "Main title",
                    stringSubtitle = "Subtitle"
                )
            }
        )
    }

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SmallTopAppBar(
                title = { Text(text = stringResource(id = R.string.str_tracks)) },
                navigationIcon = {
                    IconButton(onClick = {
                        context.startActivity(Intent(context, SearchActivity::class.java))
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_search_24),
                            contentDescription = stringResource(id = R.string.desc_searchmenu)
                        )
                    }
                },
                actions = {
                    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                                contentDescription = stringResource(id = R.string.str_settings)
                            )
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text(text = stringResource(id = R.string.str_sortby)) },
                                onClick = {  },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_baseline_sort_24),
                                        contentDescription = stringResource(id = R.string.desc_sortyby)
                                    )
                                }
                            )
                            DropdownMenuItem(
                                text = { Text(text = stringResource(id = R.string.str_gridtype)) },
                                onClick = {  },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_baseline_grid_on_24),
                                        contentDescription = stringResource(id = R.string.desc_gridtype)
                                    )
                                }
                            )
                            Divider()
                            DropdownMenuItem(
                                text = { Text(text = stringResource(id = R.string.str_settings)) },
                                onClick = {
                                    context.startActivity(Intent(context, SettingsActivity::class.java))
                                },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_outlined_settings_24),
                                        contentDescription = stringResource(id = R.string.desc_settings)
                                    )
                                }
                            )
                        }
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = padding,
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ){
                items(items.size) { i ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(22.dp))
                            .clickable {  },
                    ) {
                        OneColumnItem(
                            stringTitleItem = items[i].stringTitle,
                            stringSubtitleItem = items[i].stringSubtitle
                        )
                    }
                }
            }
        }

    )
}