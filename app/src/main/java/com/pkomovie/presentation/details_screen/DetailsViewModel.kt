package com.pkomovie.presentation.details_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pkomovie.domain.model.Movie
import com.pkomovie.domain.use_cases.ToggleAddToFavouriteUseCase
import com.pkomovie.presentation.components.UiState
import com.pkomovie.presentation.destinations.DetailsScreenDestination
import com.pkomovie.utils.ErrorStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    val toggleAddToFavouriteUseCase: ToggleAddToFavouriteUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _dataState = MutableStateFlow(DetailsState())

    val dataState
        @Composable get() = _dataState.collectAsState().value

    init {
        val navArgs: DetailsScreenNavArgs = DetailsScreenDestination.argsFrom(savedStateHandle)
        _dataState.update {
            it.copy(movie = navArgs.movie, isLoading = false)
        }
    }

    fun toggleAddToFavourite(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            movie.run {
                toggleAddToFavouriteUseCase(id, isFavourite)?.let {
                    _dataState.update {
                        it.copy(movie = it.movie?.copy(isFavourite = isFavourite.not()))
                    }
                }
            }
        }
    }

}

data class DetailsState(
    val movie: Movie? = null,
    override val isLoading: Boolean = true,
    override val error: ErrorStatus? = null,
) : UiState
