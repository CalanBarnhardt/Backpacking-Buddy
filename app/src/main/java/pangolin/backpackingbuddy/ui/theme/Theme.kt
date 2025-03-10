package pangolin.backpackingbuddy.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = MediumForestGreen, // Main green
    secondary = LightForestGreen, // Secondary green
    tertiary = LightGray, // Light gray for accents
    background = VeryLightGray, // Very light background
    surface = VeryLightGreen, // Light surfaces
    onPrimary = VeryLightGray, // Light text on green
    onSecondary = DarkGray, // Dark text on light green
    onTertiary = DarkGray, // Dark text on light gray
    onBackground = DarkGray, // Dark text on light background
    onSurface = DarkGray, // Dark text on light surfaces
    // Add button color specific roles here
    primaryContainer = DarkForestGreen, // Color of button background
    onPrimaryContainer = VeryLightGray // Color of text on button
)

private val DarkColorScheme = darkColorScheme(
    primary = MediumForestGreen, // Main green
    secondary = LightForestGreen, // Secondary green
    tertiary = DarkGray, // Dark gray for accents
    background = DarkGray, // Dark background
    surface = DarkForestGreen, // Darker surfaces
    onPrimary = VeryLightGray, // Light text on green
    onSecondary = DarkGray, // Dark text on light green
    onTertiary = VeryLightGray, // Light text on dark gray
    onBackground = VeryLightGray, // Light text on dark background
    onSurface = VeryLightGray, // Light text on dark surfaces
    // Add button color specific roles here
    primaryContainer = DarkForestGreen, // Color of button background
    onPrimaryContainer = VeryLightGray // Color of text on button
)

@Composable
fun BackpackingBuddyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}