package com.pkomovie.presentation.home_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pkomovie.R
import com.pkomovie.presentation.components.FavouriteIcon
import com.pkomovie.presentation.components.MoviePosterImage
import com.pkomovie.presentation.components.ScreenContent
import com.pkomovie.presentation.destinations.DetailsScreenDestination
import com.pkomovie.presentation.details_screen.DetailsScreenNavArgs
import com.pkomovie.presentation.home_screen.Constants.COLUMN_COUNT
import com.pkomovie.presentation.home_screen.HomeScreenEvent.AddToFavouriteClick
import com.pkomovie.presentation.home_screen.HomeScreenEvent.SearchQueryChanged
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel(), navigator: DestinationsNavigator) {
    val state = homeViewModel.dataState
    ScreenContent(uiState = state) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            SearchBar(state.searchQuery) {
                homeViewModel.onEvent(SearchQueryChanged(it))
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(COLUMN_COUNT),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                if (state.movies.isEmpty()) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        TextNoResults()
                    }
                } else {
                    items(items = state.movies, key = { movie -> movie.id }) { movie ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navigator.navigate(DetailsScreenDestination(DetailsScreenNavArgs(movie)))
                                },
                            contentAlignment = Alignment.TopEnd
                        ) {
                            MoviePosterImage(posterPath = movie.posterPath)
                            FavouriteIcon(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .clickable {
                                        homeViewModel.onEvent(AddToFavouriteClick(movie))
                                    },
                                isLiked = movie.isFavourite,
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TextNoResults() {
    Text(
        text = stringResource(id = R.string.search_no_results),
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleMedium
    )
}

object Constants {
    const val COLUMN_COUNT = 3
}
