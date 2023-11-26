package com.meltix.revo.ui.screens.search

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.navigation.NavController

@Composable
fun SearchScreen(
    navController: NavController
) {
    SearchScreen()
}

@Composable
private fun SearchScreen() {

}

@Preview(
    showBackground = true,
    showSystemUi = true,
    wallpaper = Wallpapers.RED_DOMINATED_EXAMPLE,
    device = Devices.PIXEL_7,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun SearchScreenPreview() {

}