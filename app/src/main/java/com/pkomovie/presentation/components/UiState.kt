package com.pkomovie.presentation.components

import com.pkomovie.utils.ErrorStatus

interface UiState {
    val isLoading: Boolean
    val error: ErrorStatus?

    val isError
        get() = error != null
}