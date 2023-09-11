package com.pkomovie.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: Int,
    val isAdult: Boolean, val posterPath: String, val title: String, val overview: String,
    val voteAverage: Double,
    val releaseDate: LocalDate?,
    val isFavourite: Boolean = false,
)
