package com.meltix.revo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

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

@Composable
fun CollapsingLayout(
    modifier: Modifier = Modifier,
    topContent: @Composable BoxScope.() -> Unit,
    bodyContent: @Composable BoxScope.() -> Unit
) {
    var collapsingTopHeight by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(0f) }
    
    fun calculateOffset(delta: Float): Offset {
        val oldOffset = offset
        val newOffset = (oldOffset + delta).coerceIn(-collapsingTopHeight, 0f)
        offset = newOffset
        return Offset(0f, newOffset - oldOffset)
    }
    
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset =
                when {
                    available.y >= 0 -> Offset.Zero
                    offset == -collapsingTopHeight -> Offset.Zero
                    else -> calculateOffset(available.y)
                }
            
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset =
                when {
                    available.y <= 0 -> Offset.Zero
                    offset == 0f -> Offset.Zero
                    else -> calculateOffset(available.y)
                }
        }
    }
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
        Box(
            modifier = Modifier
                .onSizeChanged { size -> collapsingTopHeight = size.height.toFloat() }
                .offset { IntOffset(x = 0, y = offset.roundToInt()) },
            content = topContent
        )
        
        Box(
            modifier = Modifier
                .offset{ IntOffset(x = 0, y = (collapsingTopHeight + offset).roundToInt()) },
            content = bodyContent
        )
    }
}