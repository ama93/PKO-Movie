package com.pkomovie.presentation.details_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pkomovie.R
import com.pkomovie.domain.model.Movie
import com.pkomovie.presentation.components.FavouriteIcon
import com.pkomovie.presentation.components.MoviePosterImage
import com.pkomovie.presentation.details_screen.Constants.MAX_SCORE
import com.pkomovie.utils.round
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.spec.DestinationStyle

data class DetailsScreenNavArgs(val movie: Movie)

@Destination(style = DestinationStyle.BottomSheet::class, navArgsDelegate = DetailsScreenNavArgs::class)
@Composable
fun DetailsScreen(detailsViewModel: DetailsViewModel = hiltViewModel()) {
    val state = detailsViewModel.dataState

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        state.movie?.let {
            MoviePosterImage(posterPath = it.posterPath)
            Text(text = it.title, style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
            RatingAndReleaseYear(movie = it)
            OutlinedButton(onClick = { detailsViewModel.toggleAddToFavourite(it) }) {
                FavouriteIcon(isLiked = it.isFavourite)
                Spacer(modifier = Modifier.size(8.dp))
                val text = if (it.isFavourite) stringResource(id = R.string.delete_from_favourites)
                else stringResource(id = R.string.add_to_favourites)
                Text(text = text)
            }
            Text(text = it.overview)
        }
    }
}

@Composable
private fun RatingAndReleaseYear(movie: Movie) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
    ) {
        movie.apply {
            val roundedScore = movie.voteAverage.round(decimalPlaces = 1)
            Text(text = "$roundedScore/$MAX_SCORE")
            DotSpacer()
            Text(text = "${movie.releaseDate?.year}")
        }

    }
}

@Composable
fun DotSpacer() {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .size(5.dp)
            .background(color = MaterialTheme.colorScheme.primary)
    )
}


object Constants {
    const val MAX_SCORE = 10
}
