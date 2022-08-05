package com.alessiocameroni.revomusicplayer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alessiocameroni.revomusicplayer.uiElements.BottomNavigationItem

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            
            Scaffold(
                topBar = { TopApplicationBar() },
                bottomBar = {
                    BottomNavigationBar(
                        items = listOf(
                            BottomNavigationItem(
                                name = stringResource(id = R.string.str_home),
                                route = "home",
                                icon = painterResource(id = R.drawable.ic_outline_home_24)
                            ),
                            BottomNavigationItem(
                                name = stringResource(id = R.string.str_tracks),
                                route = "tracks",
                                icon = painterResource(id = R.drawable.ic_outline_music_note_24)
                            ),
                            BottomNavigationItem(
                                name = stringResource(id = R.string.str_albums),
                                route = "albums",
                                icon = painterResource(id = R.drawable.ic_outline_album_24)
                            ),
                            BottomNavigationItem(
                                name = stringResource(id = R.string.str_playlists),
                                route = "playlists",
                                icon = painterResource(id = R.drawable.ic_outline_playlist_play_24)
                            ),
                            BottomNavigationItem(
                                name = stringResource(id = R.string.str_spoitfy),
                                route = "spotify",
                                icon = painterResource(id = R.drawable.ic_spotify_favourite_heart_24)
                            ),
                        ),
                        navController = navController,
                        onItemClick = {
                            navController.navigate(it.route)
                        }
                    )
                },
                content = {
                    Navigation(navController = navController)
                }
            )
        }
    }
}

//Top Navigation

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TopApplicationBar() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    SmallTopAppBar(
        title = { Text(
            stringResource(id = R.string.str_home),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        ) },
        actions = {
            IconButton(
                onClick = {  }
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(id = R.string.desc_topmenu)
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

/*
    Scaffold(

        content = { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val list = (0..75).map { it.toString() }
                items(count = list.size) {
                    Text(
                        text = list[it],
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
            }
        }
    )
}*/

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
                icon = { Icon(
                        painter = item.icon,
                        contentDescription = item.name
                    )
                },
                label = { Text(text = item.name) }
            )
        }
    }
}


//Screens

@Preview(showBackground = true)
@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "HomeScreen")
    }
}

@Preview(showBackground = true)
@Composable
fun TracksScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "TracksScreen")
    }
}

@Preview(showBackground = true)
@Composable
fun AlbumsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "AlbumsScreen")
    }
}

@Preview(showBackground = true)
@Composable
fun PlaylistsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "PlaylistsScreen")
    }
}

@Preview(showBackground = true)
@Composable
fun SpotifyFavoritesScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "SpotifyFavoritesScreen")
    }
}
