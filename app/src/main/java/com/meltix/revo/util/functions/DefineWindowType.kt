package com.meltix.revo.util.functions

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.meltix.revo.data.classes.WindowType

/**
 * Class that defines with an enum class the type of screen depending
 * on the WindowHeight and Width size classes.
 *
 * - WindowWidthSizeClass.Compact:
 *     - COMPACT_WINDOW: When the app is used in split screen on a Phone
 *     - COMPACT_PORTRAIT: When the device is in portrait mode a Phone
 * - WindowWidthSizeClass.Medium:
 *     - MEDIUM_PORTRAIT: Might be used on a square, medium size Foldable
 *     - EXPANDED_PORTRAIT: When the device is in portrait mode and is a Foldable
 *     - COMPACT_LANDSCAPE: When the device is in landscape mode and is a medium size Phone
 * - WindowWidthSizeClass.Expanded:
 *     - COMPACT_LANDSCAPE: When the device is in landscape mode and is a Phone
 *     - MEDIUM_LANDSCAPE: When the device is in landscape mode and is a Foldable
 *     - EXPANDED_LANDSCAPE: When the device is in landscape mode and is a Tablet
 */
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