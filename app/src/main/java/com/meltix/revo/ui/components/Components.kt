package com.meltix.revo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.meltix.revo.R

@Composable
fun SmallImageContainer(
    modifier: Modifier,
    painterPlaceholder: Painter,
    leadingUnit: @Composable () -> Unit?,
) {
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .size(60.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterPlaceholder,
            contentDescription = stringResource(id = R.string.str_albumImage),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )

        leadingUnit()
    }
}

@Composable
fun LargeImageContainer(
    modifier: Modifier = Modifier,
    painterPlaceholder: Painter,
    leadingUnit: @Composable () -> Unit?,
) {
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .size(110.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterPlaceholder,
            contentDescription = stringResource(id = R.string.str_albumImage),
            modifier = Modifier.size(35.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )

        leadingUnit()
    }
}