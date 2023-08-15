package com.meltix.revo.data.classes

import androidx.compose.ui.graphics.painter.Painter

data class MainNavigationItem(
    val name: String,
    val route: String,
    val iconOutlined: Painter,
    val iconFilled: Painter
)
