package com.alessiocameroni.revomusicplayer.data.modifiers

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

fun Modifier.clickableRowItem(): Modifier =
    fillMaxWidth()
    .clip(RoundedCornerShape(22.dp))