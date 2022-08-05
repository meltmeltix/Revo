package com.alessiocameroni.revomusicplayer.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorPalette = darkColorScheme(
    primary = Purple80,
    onPrimary = Purple20,
    primaryContainer = Purple30,
    onPrimaryContainer = Purple90,
    inversePrimary = Purple40,

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

    surface = PurpleGrey30,
    onSurface = PurpleGrey80,
    inverseSurface = Grey90,
    inverseOnSurface = Grey10,

    surfaceVariant = PurpleGrey30,
    onSurfaceVariant = PurpleGrey80,

    outline = PurpleGrey80

)

private val LightColorPalette = lightColorScheme(
    primary = Purple80,
    secondary = Purple20
)

@Composable
fun RevoMusicPlayerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val useDynamicColors = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val colors = when {
        useDynamicColors && darkTheme -> dynamicDarkColorScheme(LocalContext.current)
        useDynamicColors && !darkTheme -> dynamicLightColorScheme(LocalContext.current)
        darkTheme -> DarkColorPalette
        else -> LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}