package com.pkomovie.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ScreenContent(
    uiState: UiState,
    content: @Composable () -> Unit = {},
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            uiState.isError -> uiState.error?.let {
                ErrorView()
            }
            uiState.isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            else -> content()
        }
    }
}