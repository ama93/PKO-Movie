package com.pkomovie.data.mapper

import com.pkomovie.data.remote.dto.MovieDto
import com.pkomovie.domain.model.Movie
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate
import timber.log.Timber


fun MovieDto.toMovie() = Movie(
    id = id,
    posterPath = posterPath,
    title = originalTitle,
    overview = overview,
    voteAverage = voteAverage,
    releaseDate = releaseDate.toLocalDate(),
    isAdult = adult
)

fun String.toLocalDate() = kotlin.runCatching {
    LocalDate.parse(this)
}.onFailure {
    Timber.d("Date has not been parsed: $it")
}.getOrNull()
