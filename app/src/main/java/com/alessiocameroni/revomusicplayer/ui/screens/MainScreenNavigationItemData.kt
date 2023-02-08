package com.alessiocameroni.revomusicplayer.ui.screens

import androidx.compose.ui.graphics.painter.Painter

data class MainScreenNavigationItemData(
    val name: String,
    val route: String,
    val iconOutlined: Painter,
    val iconFilled: Painter
)
