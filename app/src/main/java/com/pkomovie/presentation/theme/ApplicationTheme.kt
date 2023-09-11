package com.pkomovie.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun ApplicationTheme(content: @Composable () -> Unit) {

    val colorScheme = if (isSystemInDarkTheme()) DarkColors else LightColors

    MaterialTheme(colorScheme = colorScheme) {
        content()
    }
}