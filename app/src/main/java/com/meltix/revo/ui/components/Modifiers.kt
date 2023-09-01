package com.meltix.revo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.meltix.revo.data.classes.album.HeaderLayout

@Composable
fun Modifier.contentModifier(
    windowClass: WindowSizeClass,
    padding: PaddingValues
): Modifier = composed {
    when (windowClass.widthSizeClass) {
        WindowWidthSizeClass.Medium, WindowWidthSizeClass.Expanded ->
            when (windowClass.heightSizeClass) {
                WindowHeightSizeClass.Medium, WindowHeightSizeClass.Expanded ->
                    Modifier
                        .padding(PaddingValues(top = 64.dp, bottom = 0.dp))
                        .fillMaxSize()
                        .clip( RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp ) )
                        .background(MaterialTheme.colorScheme.surface)
                else ->
                    Modifier
                        .padding(PaddingValues(top = padding.calculateTopPadding(), bottom = 0.dp))
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)
            }
        else ->
            Modifier
                .padding(PaddingValues(top = padding.calculateTopPadding(), bottom = 0.dp))
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
    }
}

@Composable
fun Modifier.albumDetailsContentModifier(
    windowClass: WindowSizeClass,
    padding: PaddingValues,
    headerLayout: HeaderLayout
): Modifier = composed {
    when (windowClass.widthSizeClass) {
        WindowWidthSizeClass.Medium, WindowWidthSizeClass.Expanded ->
            when (windowClass.heightSizeClass) {
                WindowHeightSizeClass.Medium, WindowHeightSizeClass.Expanded ->
                    Modifier
                        .padding(PaddingValues(top = 64.dp, bottom = 0.dp))
                        .fillMaxSize()
                        .clip( RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp) )
                        .background(MaterialTheme.colorScheme.surface)
                else ->
                    when(headerLayout) {
                        HeaderLayout.FRUIT_MUSIC -> Modifier
                            .padding(PaddingValues(top = 0.dp, bottom = 0.dp))
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surface)
                        else -> Modifier
                            .padding(PaddingValues(top = padding.calculateTopPadding(), bottom = 0.dp))
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surface)
                    }
            }
        else ->
            when(headerLayout) {
                HeaderLayout.FRUIT_MUSIC -> Modifier
                    .padding(PaddingValues(top = 0.dp, bottom = 0.dp))
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                else -> Modifier
                    .padding(PaddingValues(top = padding.calculateTopPadding(), bottom = 0.dp))
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
            }
    }
}