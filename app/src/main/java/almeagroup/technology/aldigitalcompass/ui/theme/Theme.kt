package almeagroup.technology.aldigitalcompass.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = BrandAccent,
    secondary = BrandPrimary,
    tertiary = Warning,
    background = BrandOnSurface,
    surface = BrandPrimary,
    onPrimary = BrandOnSurface,
    onSurface = Color.White,
    onSurfaceVariant = BrandBorder,
    outline = BrandMuted,
)

private val LightColorScheme = lightColorScheme(
    primary = BrandPrimary,
    secondary = BrandAccent,
    tertiary = Warning,
    background = BrandBackground,
    surface = BrandSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = BrandOnSurface,
    onSurface = BrandOnSurface,
    onSurfaceVariant = BrandMuted,
    outline = BrandBorder,
)

@Composable
fun ServiceSkeletonTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
