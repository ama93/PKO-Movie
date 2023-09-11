package com.pkomovie.data.remote.dto.search

import com.pkomovie.data.remote.dto.MovieDto

data class SearchMovieDto(
    val page: Int,
    val results: List<MovieDto>,
    val total_pages: Int,
    val total_results: Int
)