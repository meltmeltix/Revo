package com.meltix.revo.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColorScheme(
    primary = Purple80,
    onPrimary = Purple20,
    primaryContainer = Purple30,
    onPrimaryContainer = Purple90,

    secondary = DarkPurple80,
    onSecondary = DarkPurple20,
    secondaryContainer = DarkPurple30,
    onSecondaryContainer = DarkPurple90,

    tertiary = AquaGreen80,
    onTertiary = AquaGreen20,
    tertiaryContainer = AquaGreen30,
    onTertiaryContainer = AquaGreen90,

    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,

    background = Grey10,
    onBackground = Grey90,
    surface = Grey10,
    onSurface = Grey90,

    surfaceVariant = VarGrey30,
    onSurfaceVariant = VarGrey80,
    outline = VarGrey60

)

private val LightColorPalette = lightColorScheme(
    primary = Purple40,
    onPrimary = Color.White,
    primaryContainer = Purple90,
    onPrimaryContainer = Purple10,

    secondary = DarkPurple40,
    onSecondary = Color.White,
    secondaryContainer = DarkPurple90,
    onSecondaryContainer = DarkPurple10,

    tertiary = AquaGreen40,
    onTertiary = Color.White,
    tertiaryContainer = AquaGreen90,
    onTertiaryContainer = AquaGreen10,

    error = Red40,
    onError = Color.White,
    errorContainer = Red90,
    onErrorContainer = Red10,

    background = Grey99,
    onBackground = Grey10,
    surface = Grey99,
    onSurface = Grey10,

    outline = VarGrey50,
    surfaceVariant = VarGrey90,
    onSurfaceVariant = VarGrey30,

)

@Composable
fun RevoMusicPlayerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val useDynamicColors = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val systemUiController = rememberSystemUiController()
    val colors = when {
        useDynamicColors && darkTheme -> dynamicDarkColorScheme(LocalContext.current)
        useDynamicColors && !darkTheme -> dynamicLightColorScheme(LocalContext.current)
        darkTheme -> DarkColorPalette
        else -> LightColorPalette
    }

    when {
        !darkTheme -> systemUiController
            .setSystemBarsColor(color = Color.Transparent, darkIcons = true)
        else -> systemUiController
            .setSystemBarsColor(color = Color.Transparent, darkIcons = false)
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}