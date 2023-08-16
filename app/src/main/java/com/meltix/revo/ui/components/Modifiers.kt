package com.meltix.revo.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.navigationPaddingOnWindow(windowClass: WindowSizeClass): Modifier = composed {
    val systemCutoutPadding = WindowInsets.displayCutout.asPaddingValues()

    when(windowClass.widthSizeClass) {
        WindowWidthSizeClass.Medium, WindowWidthSizeClass.Expanded ->
            Modifier.padding(
                start = systemCutoutPadding.calculateStartPadding(LayoutDirection.Ltr),
                end = systemCutoutPadding.calculateEndPadding(LayoutDirection.Ltr)
            )
        else ->
            Modifier.padding(0.dp)
    }
}

@Composable
fun Modifier.contentModifierOnWindow(windowClass: WindowSizeClass): Modifier = composed {
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    val systemCutoutPadding = WindowInsets.displayCutout.asPaddingValues()

    when(windowClass.widthSizeClass) {
        WindowWidthSizeClass.Medium, WindowWidthSizeClass.Expanded ->
            when(windowClass.heightSizeClass) {
                WindowHeightSizeClass.Compact, WindowHeightSizeClass.Medium ->
                    Modifier
                        .padding( PaddingValues(
                            start = 80.dp + systemCutoutPadding.calculateStartPadding(LayoutDirection.Ltr),
                            top = systemBarsPadding.calculateTopPadding(),
                            end = 24.dp + systemCutoutPadding.calculateEndPadding(LayoutDirection.Ltr),
                            bottom = 0.dp
                        ) )
                        .fillMaxSize()
                        .clip( RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomEnd = 0.dp,
                            bottomStart = 0.dp
                        ) )
                else -> Modifier
                    .padding( PaddingValues(0.dp) )
                    .fillMaxSize()
                    .clip( RoundedCornerShape(0.dp) )
            }
        else -> Modifier
    }
}
