package com.pkomovie.domain.repository

import com.pkomovie.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getNowPlayingMovies(): List<Movie>
    suspend fun queryMovies(query: String): List<Movie>

    suspend fun saveToFavourites(movieId: Int)

    suspend fun removeFromFavourites(movieId: Int)

    suspend fun observeFavouritesChanged(): Flow<Set<String>?>
}