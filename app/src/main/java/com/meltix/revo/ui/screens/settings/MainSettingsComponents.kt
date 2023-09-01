package com.meltix.revo.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.meltix.revo.ui.navigation.SettingsScreens

@Composable
fun MainSettingsLayout(
    windowClass: WindowSizeClass,
    leftPaneContent: @Composable (NavHostController?) -> Unit,
    rightPaneContent: @Composable (String, NavHostController) -> Unit
) {
    when(windowClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            val compactNavController = rememberNavController()
            CompactLayout {
                rightPaneContent(SettingsScreens.SettingsList.route, compactNavController)
            }
        }
        else -> {
            val expandedNavController = rememberNavController()
            ExpandedLayout(
                leftPaneContent = { leftPaneContent(expandedNavController) },
                rightPaneContent = {
                    rightPaneContent(SettingsScreens.Library.route, expandedNavController)
                }
            )
        }
    }
}

@Composable
private fun CompactLayout(content: @Composable () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) { content() }
}

@Composable
private fun ExpandedLayout(
    leftPaneContent: @Composable () -> Unit,
    rightPaneContent: @Composable () -> Unit,
) {
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    val systemCutoutPadding = WindowInsets.displayCutout.asPaddingValues()
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.inverseOnSurface
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = systemCutoutPadding.calculateStartPadding(LayoutDirection.Ltr),
                    top = systemBarsPadding.calculateTopPadding(),
                    end = systemCutoutPadding.calculateEndPadding(LayoutDirection.Ltr)
                ),
        ) {
            Column(
                modifier = Modifier
                    .weight(0.8f)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
            ) { leftPaneContent() }
            
            Column(
                modifier = Modifier
                    .padding(end = 24.dp)
                    .clip( RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp) )
                    .weight(1f)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.surface)
            ) { rightPaneContent() }
        }
    }
}
