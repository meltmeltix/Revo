package com.meltix.revo.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun surfaceColorOnWindowSize(windowClass: WindowSizeClass): Color {
    return when(windowClass.widthSizeClass) {
        WindowWidthSizeClass.Medium, WindowWidthSizeClass.Expanded ->
            MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
        else ->
            MaterialTheme.colorScheme.surface
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun scrollBehaviorOnWindowSize(windowClass: WindowSizeClass): TopAppBarScrollBehavior {
    return when(windowClass.heightSizeClass) {
        WindowHeightSizeClass.Medium, WindowHeightSizeClass.Expanded ->
            TopAppBarDefaults.pinnedScrollBehavior()
        else ->
            TopAppBarDefaults.enterAlwaysScrollBehavior()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topAppBarInsetsOnWindowsSize(windowClass: WindowSizeClass): WindowInsets {
    return when(windowClass.widthSizeClass) {
        WindowWidthSizeClass.Medium, WindowWidthSizeClass.Expanded -> WindowInsets(0.dp)
        else -> TopAppBarDefaults.windowInsets
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topAppBarColorOnWindowSize(windowClass: WindowSizeClass): TopAppBarColors {
    return when(windowClass.widthSizeClass) {
        WindowWidthSizeClass.Medium, WindowWidthSizeClass.Expanded ->
            when(windowClass.heightSizeClass) {
                WindowHeightSizeClass.Compact ->
                    TopAppBarDefaults.topAppBarColors(
                        scrolledContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
                    )
                else ->
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                        scrolledContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
                    )
            }
        else -> TopAppBarDefaults.topAppBarColors()
    }
}

@Composable
fun listContentPaddingOnWindowSize(windowClass: WindowSizeClass): PaddingValues {
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()

    return when(windowClass.widthSizeClass) {
        WindowWidthSizeClass.Compact ->
            when(windowClass.heightSizeClass) {
                WindowHeightSizeClass.Medium ->
                    PaddingValues(bottom = systemBarsPadding.calculateBottomPadding() + 80.dp)
                else ->
                    PaddingValues(bottom = systemBarsPadding.calculateBottomPadding())
            }
        else -> PaddingValues(bottom = systemBarsPadding.calculateBottomPadding())
    }
}