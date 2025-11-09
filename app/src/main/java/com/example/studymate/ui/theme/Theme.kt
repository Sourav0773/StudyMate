package com.example.studymate.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun StudyMateTheme(content: @Composable () -> Unit) {
    val colorScheme = lightColorScheme(
        primary = PrimaryColor,
        secondary = SecondaryColor,
        background = PrimaryColor,
        surface = SurfaceColor,
        onPrimary = TextOnPrimary,
        onSecondary = TextOnPrimary,
        onBackground = TextOnPrimary,
        onSurface = TextOnSurface,
        error = ErrorColor
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}