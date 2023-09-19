package com.meltix.revo.util.functions

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.meltix.revo.data.classes.WindowType

@Composable
fun defineWindowType(windowSizeClass: WindowSizeClass): WindowType {
    return when(windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            when(windowSizeClass.heightSizeClass) {
                WindowHeightSizeClass.Compact -> WindowType.COMPACT_WINDOW
                else -> WindowType.COMPACT_PORTRAIT
            }
        }
        WindowWidthSizeClass.Medium -> {
            when(windowSizeClass.heightSizeClass) {
                WindowHeightSizeClass.Medium -> WindowType.MEDIUM_PORTRAIT
                WindowHeightSizeClass.Expanded -> WindowType.EXPANDED_PORTRAIT
                else -> WindowType.COMPACT_LANDSCAPE
            }
        }
        WindowWidthSizeClass.Expanded -> {
            when(windowSizeClass.heightSizeClass) {
                WindowHeightSizeClass.Compact -> WindowType.COMPACT_LANDSCAPE
                WindowHeightSizeClass.Medium -> WindowType.MEDIUM_LANDSCAPE
                else -> WindowType.EXPANDED_LANDSCAPE
            }
        }
        else -> WindowType.COMPACT_PORTRAIT
    }
}