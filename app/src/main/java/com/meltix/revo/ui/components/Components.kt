package com.meltix.revo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun ImageContainer(
    modifier: Modifier = Modifier,
    placeholder: @Composable () -> Unit = {  },
    content: @Composable () -> Unit = {  }
) {
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .defaultMinSize(48.dp)
            .aspectRatio(1f)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        placeholder()
        content()
    }
}