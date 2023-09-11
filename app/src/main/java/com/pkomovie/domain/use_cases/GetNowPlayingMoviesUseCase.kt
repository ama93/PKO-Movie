package com.pkomovie.domain.use_cases

import com.pkomovie.domain.model.Movie
import com.pkomovie.domain.repository.Repository
import com.pkomovie.utils.ErrorStatus
import com.pkomovie.utils.Resource
import timber.log.Timber
import javax.inject.Inject


class GetNowPlayingMoviesUseCase @Inject constructor(
    private val repository: Repository,
) {
    suspend operator fun invoke(): Resource<List<Movie>> {
        return kotlin.runCatching {
            val movies = repository.getNowPlayingMovies()
            Timber.d("fetchedMovies: $movies")
            Resource.Success(movies)
        }.getOrElse {
            Resource.Error(ErrorStatus(it.message))
        }
    }
}
