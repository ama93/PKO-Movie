package com.pkomovie.data.remote

import com.pkomovie.data.remote.dto.now_playing.NowPlayingMoviesDto
import com.pkomovie.data.remote.dto.search.SearchMovieDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteApi {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): NowPlayingMoviesDto

    @GET("search/movie")
    suspend fun queryMovies(@Query("query") query: String): SearchMovieDto
}