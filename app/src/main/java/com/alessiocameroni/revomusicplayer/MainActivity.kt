package com.alessiocameroni.revomusicplayer

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

/*
@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen() }
        composable("tracks") { TracksScreen() }
        composable("albums") { AlbumsScreen() }
        composable("playlists") { PlaylistsScreen() }
        composable("spotify") { SpotifyFavsScreen() }
    }
}
*/

@Composable
fun BottomNavBar() {
    val context = LocalContext.current

    NavigationBar {
        NavigationBarItem(
            selected = true,
            onClick = { Toast.makeText(context, "This is a VERY cool toast", Toast.LENGTH_SHORT).show() },
            icon = { Icon(painter = painterResource(id = R.drawable.ic_outline_home_24), contentDescription = null) },
            label = { Text(text = stringResource(id = R.string.str_home)) }
        )

        NavigationBarItem(
            selected = true,
            onClick = { Toast.makeText(context, "This is a VERY cool toast", Toast.LENGTH_SHORT).show() },
            icon = { Icon(painter = painterResource(id = R.drawable.ic_outline_music_note_24), contentDescription = null) },
            label = { Text(text = stringResource(id = R.string.str_tracks)) }
        )

        NavigationBarItem(
            selected = true,
            onClick = { Toast.makeText(context, "This is a VERY cool toast", Toast.LENGTH_SHORT).show() },
            icon = { Icon(painter = painterResource(id = R.drawable.ic_outline_album_24), contentDescription = null) },
            label = { Text(text = stringResource(id = R.string.str_albums)) }
        )

        NavigationBarItem(
            selected = true,
            onClick = { Toast.makeText(context, "This is a VERY cool toast", Toast.LENGTH_SHORT).show() },
            icon = { Icon(painter = painterResource(id = R.drawable.ic_outline_playlist_play_24), contentDescription = null) },
            label = { Text(text = stringResource(id = R.string.str_playlists)) }
        )

        NavigationBarItem(
            selected = true,
            onClick = { Toast.makeText(context, "This is a VERY cool toast", Toast.LENGTH_SHORT).show() },
            icon = { Icon(painter = painterResource(id = R.drawable.ic_spotify_favourite_heart_24), contentDescription = null) },
            label = { Text(text = stringResource(id = R.string.str_spoitfy)) }
        )
    }
}
/*
@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Home Screen")
    }
}

@Composable
fun TracksScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Tracks Screen")
    }
}

@Composable
fun AlbumsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Albums Screen")
    }
}

@Composable
fun PlaylistsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Playlists Screen")
    }
}

@Composable
fun SpotifyFavsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Spotify Screen")
    }
}
*/