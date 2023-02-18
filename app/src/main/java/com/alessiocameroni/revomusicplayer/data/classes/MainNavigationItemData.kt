package com.alessiocameroni.revomusicplayer.data.classes

import androidx.compose.ui.graphics.painter.Painter

data class MainNavigationItemData(
    val name: String,
    val route: String,
    val iconOutlined: Painter,
    val iconFilled: Painter
)
