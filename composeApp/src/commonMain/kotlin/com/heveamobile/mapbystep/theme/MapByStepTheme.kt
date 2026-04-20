package com.heveamobile.mapbystep.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

val LightColorTheme = lightColorScheme(
    background = Background, // Activity background
    surfaceContainer = SurfaceContainer, // Primary containers
    surfaceContainerHigh = SurfaceContainerHigh, // Secondary containers (Toolbar, Nav bar, etc)
    outline = Outline, // Outline for SurfaceContainer
    onSurface = OnSurface, // Text and Icons on Surface
    primaryContainer = PrimaryContainer, // Primary elements like buttons
    onPrimaryContainer = OnPrimaryContainer, // Text and Icons on PrimaryContainer
    outlineVariant = OutlineVariant, // Outline for PrimaryContainer
    secondaryContainer = SecondaryContainer, // Secondary buttons like in Nav bar
    onSecondaryContainer = OnSecondaryContainer, // Text and icons on SecondaryContainer
)

@Composable
fun MapByStepTheme(
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalSpacing provides Spacing()) {
        MaterialTheme(
            colorScheme = LightColorTheme,
            typography = Typography,
            shapes = Shapes,
            content = content,
        )
    }
}