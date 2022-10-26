package ru.okei.med.feature.theme

import android.app.Activity
import android.view.WindowManager
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat

@Composable
fun MedTheme(content: @Composable () -> Unit) {
    val darkColorPalette = darkColors(
        primary = BlueLight,
        secondary = BlueLight,
        background = Black95,
        onBackground = White95,
        error = RedLight
    )
    MaterialTheme(
        colors = darkColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}