package com.alessiocameroni.revomusicplayer

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alessiocameroni.revomusicplayer.ui.theme.RevoMusicPlayerTheme
import com.alessiocameroni.revomusicplayer.uiElements.BottomNavigationItem

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RevoMusicPlayerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(
                                items = listOf(
                                    BottomNavigationItem(
                                        name = stringResource(id = R.string.str_home),
                                        route = "home",
                                        iconOutlined = painterResource(id = R.drawable.ic_outlined_home_24),
                                        iconFilled = painterResource(id = R.drawable.ic_filled_home_24)
                                    ),
                                    BottomNavigationItem(
                                        name = stringResource(id = R.string.str_tracks),
                                        route = "tracks",
                                        iconOutlined = painterResource(id = R.drawable.ic_outlined_music_note_24),
                                        iconFilled = painterResource(id = R.drawable.ic_filled_music_note_24)
                                    ),
                                    BottomNavigationItem(
                                        name = stringResource(id = R.string.str_albums),
                                        route = "albums",
                                        iconOutlined = painterResource(id = R.drawable.ic_outlined_album_24),
                                        iconFilled = painterResource(id = R.drawable.ic_filled_album_24)
                                    ),
                                    BottomNavigationItem(
                                        name = stringResource(id = R.string.str_playlists),
                                        route = "playlists",
                                        iconOutlined = painterResource(id = R.drawable.ic_outlined_playlist_play_24),
                                        iconFilled = painterResource(id = R.drawable.ic_filled_playlist_play_24)
                                    ),
                                    BottomNavigationItem(
                                        name = stringResource(id = R.string.str_spoitfy),
                                        route = "spotify",
                                        iconOutlined = painterResource(id = R.drawable.ic_outlined_spotify_24),
                                        painterResource(id = R.drawable.ic_filled_spotify_24)
                                    ),
                                ),
                                navController = navController,
                                onItemClick = {
                                    navController.navigate(it.route)
                                }
                            )
                        },
                        content = { padding ->
                            Column(modifier = Modifier.padding(padding)){
                                Navigation(navController = navController)
                            }
                        }
                    )
                }
            }
        }
    }
}


//Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text(text = stringResource(id = R.string.str_home)) },
                navigationIcon = {
                    IconButton(onClick = {
                        context.startActivity(Intent(context, SearchActivity::class.java))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(id = R.string.desc_searchmenu)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        context.startActivity(Intent(context, SettingsActivity::class.java))
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_outlined_settings_24),
                            contentDescription = stringResource(id = R.string.str_settings)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)){

            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TracksScreen() {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text(text = stringResource(id = R.string.str_tracks)) },
                navigationIcon = {
                    IconButton(onClick = {
                        context.startActivity(Intent(context, SearchActivity::class.java))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(id = R.string.desc_searchmenu)
                        )
                    }
                },
                actions = {
                    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
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
                            MenuDefaults.Divider()
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
            Column(modifier = Modifier.padding(padding)){

            }
        }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumsScreen() {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text(text = stringResource(id = R.string.str_albums)) },
                navigationIcon = {
                    IconButton(onClick = {
                        context.startActivity(Intent(context, SearchActivity::class.java))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(id = R.string.desc_searchmenu)
                        )
                    }
                },
                actions = {
                    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
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
                            MenuDefaults.Divider()
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
            Column(modifier = Modifier.padding(padding)){

            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistsScreen() {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text(text = stringResource(id = R.string.str_playlists)) },
                navigationIcon = {
                    IconButton(onClick = {
                        context.startActivity(Intent(context, SearchActivity::class.java))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(id = R.string.desc_searchmenu)
                        )
                    }
                },
                actions = {
                    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
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
                            MenuDefaults.Divider()
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
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = { /* do something */ }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_playlist_add_24),
                    contentDescription = stringResource(id = R.string.desc_addplaylist),
                    modifier = Modifier.size(FloatingActionButtonDefaults.LargeIconSize),
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = { padding ->
            Column(modifier = Modifier.padding(padding)){

            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpotifyFavoritesScreen() {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text(text = stringResource(id = R.string.str_spoitfy)) },
                navigationIcon = {
                    IconButton(onClick = {
                        context.startActivity(Intent(context, SearchActivity::class.java))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(id = R.string.desc_searchmenu)
                        )
                    }
                },
                actions = {
                    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
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
                            MenuDefaults.Divider()
                            DropdownMenuItem(
                                text = { Text(text = stringResource(id = R.string.str_openspotify)) },
                                onClick = {  },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_launch_spotify_24px),
                                        contentDescription = stringResource(id = R.string.desc_openspotify)
                                    )
                                }
                            )
                            MenuDefaults.Divider()
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
            Column(modifier = Modifier.padding(padding)){

            }
        }
    )
}


//Bottom Navigation

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen() }
        composable("tracks") { TracksScreen() }
        composable("albums") { AlbumsScreen() }
        composable("playlists") { PlaylistsScreen() }
        composable("spotify") { SpotifyFavoritesScreen() }
    }
}

@Composable
fun BottomNavigationBar(
    items: List<BottomNavigationItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavigationItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()

    NavigationBar(
        modifier = modifier,
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            
            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(item) },
                icon = {
                    when {
                        selected -> Icon(
                            painter = item.iconFilled,
                            contentDescription = item.name
                        )
                        else -> Icon(
                            painter = item.iconOutlined,
                            contentDescription = item.name
                        )
                    }
                },
                label = { Text(text = item.name) }
            )
        }
    }
}


//Other functions
