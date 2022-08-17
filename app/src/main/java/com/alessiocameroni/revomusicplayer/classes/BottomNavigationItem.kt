package com.alessiocameroni.revomusicplayer.classes

import androidx.compose.ui.graphics.painter.Painter

data class BottomNavigationItem(
    val name: String,
    val route: String,
    val iconOutlined: Painter,
    val iconFilled: Painter
)
