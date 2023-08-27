package com.meltix.revo.data.classes.settings

import androidx.compose.ui.graphics.painter.Painter

class MainSettingsItem(
    val headlineText: String,
    val supportingText: String,
    val icon: Painter,
    val route: String
)