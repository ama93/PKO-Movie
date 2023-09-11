package com.pkomovie.presentation.home_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pkomovie.domain.model.Movie
import com.pkomovie.domain.repository.Repository
import com.pkomovie.domain.use_cases.GetNowPlayingMoviesUseCase
import com.pkomovie.domain.use_cases.SearchMoviesUseCase
import com.pkomovie.domain.use_cases.ToggleAddToFavouriteUseCase
import com.pkomovie.presentation.components.UiState
import com.pkomovie.presentation.home_screen.HomeScreenEvent.AddToFavouriteClick
import com.pkomovie.presentation.home_screen.HomeScreenEvent.SearchQueryChanged
import com.pkomovie.utils.ErrorStatus
import com.pkomovie.utils.Resource.Error
import com.pkomovie.utils.Resource.Loading
import com.pkomovie.utils.Resource.Success
import com.pkomovie.utils.removeWhitespaces
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val getNowPlayingMovies: GetNowPlayingMoviesUseCase,
    val searchMoviesUseCase: SearchMoviesUseCase,
    val addToFavouriteUseCase: ToggleAddToFavouriteUseCase,
    val repository: Repository,
) : ViewModel() {

    private val _dataState = MutableStateFlow(HomeState())

    // Exposed to homeScreen
    val dataState
        @Composable get() = _dataState.collectAsState().value

    private var initialMovieList = emptyList<Movie>()

    private var queryJob: Job? = null


    init {
        val getMoviesJob = viewModelScope.launch(Dispatchers.IO) {
            when (val moviesResource = getNowPlayingMovies()) {
                is Success -> {
                    _dataState.update {
                        it.copy(movies = moviesResource.data, isLoading = false)
                    }
                    // shown when search results cleared
                    initialMovieList = moviesResource.data
                }

                is Error -> _dataState.update {
                    it.copy(error = moviesResource.errorStatus, isLoading = false)
                }

                is Loading -> {}
            }

        }
        viewModelScope.launch(Dispatchers.IO) {
            getMoviesJob.join()
            observeFavouritesChanged()
        }
    }

    private suspend fun observeFavouritesChanged() {
        repository.observeFavouritesChanged().collectLatest { favourites ->
            Timber.d("favoritesChanged, current: $favourites")
            _dataState.update {
                val moviesWithIsFavourite = it.movies.toMutableList().map { movie ->
                    movie.copy(isFavourite = favourites?.contains(movie.id.toString()) == true)
                }
                // toggleList needed to trigger recomposition properly
                it.copy(movies = moviesWithIsFavourite, toggleList = it.toggleList.not())
            }
        }
    }

    fun onEvent(uiEvent: HomeScreenEvent) {
        when (uiEvent) {
            is SearchQueryChanged -> {
                val query = uiEvent.query
                _dataState.update { it.copy(searchQuery = query) }
                if (query.removeWhitespaces().isNotEmpty()) {
                    submitSearchQuery()
                } else {
                    _dataState.update { it.copy(movies = initialMovieList) }
                }
            }

            is AddToFavouriteClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    uiEvent.movie.run {
                        addToFavouriteUseCase(id, isFavourite)
                    }
                }
            }
        }
    }

    private fun submitSearchQuery() {
        queryJob?.cancel()
        queryJob = viewModelScope.launch(Dispatchers.IO) {

            when (val resultsResource = searchMoviesUseCase(_dataState.value.searchQuery)) {
                is Success -> {
                    _dataState.update {
                        it.copy(movies = resultsResource.data, isLoading = false)
                    }
                    // shown when search results cleared
                    initialMovieList = resultsResource.data
                }

                is Error -> _dataState.update {
                    it.copy(error = resultsResource.errorStatus, isLoading = false)
                }

                is Loading -> {}
            }
        }
    }

}

data class HomeState(
    val movies: List<Movie> = emptyList(),
    val searchQuery: String = "",
    val toggleList: Boolean = false,
    override val isLoading: Boolean = true,
    override val error: ErrorStatus? = null,
) : UiState

sealed interface HomeScreenEvent {

    data class SearchQueryChanged(val query: String) : HomeScreenEvent

    data class AddToFavouriteClick(val movie: Movie) : HomeScreenEvent

}
