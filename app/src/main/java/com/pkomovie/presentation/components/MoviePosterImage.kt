package com.pkomovie.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.Coil
import coil.compose.AsyncImage

@Composable
fun MoviePosterImage(modifier: Modifier = Modifier, posterPath: String) {
    AsyncImage(
        model = POSTER_URL.format(posterPath),
        contentDescription = "Thumbnail",
        modifier = modifier,
        contentScale = ContentScale.FillWidth,
        imageLoader = Coil.imageLoader(LocalContext.current.applicationContext)
    )
}

const val POSTER_URL = "https://image.tmdb.org/t/p/w500/%s"