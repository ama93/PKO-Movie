package com.pkomovie.data.remote.dto.now_playing

import com.pkomovie.data.remote.dto.MovieDto

data class NowPlayingMoviesDto(
    val page: Int,
    val results: List<MovieDto>,
    val total_pages: Int,
    val total_results: Int
)