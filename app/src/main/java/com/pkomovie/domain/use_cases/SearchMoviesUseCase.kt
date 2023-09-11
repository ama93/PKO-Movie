package com.pkomovie.domain.use_cases

import com.pkomovie.domain.model.Movie
import com.pkomovie.domain.repository.Repository
import com.pkomovie.utils.ErrorStatus
import com.pkomovie.utils.Resource
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val repository: Repository,
) {
    suspend operator fun invoke(query: String): Resource<List<Movie>> {
        return kotlin.runCatching {
            val searchResults = repository.queryMovies(query)
            Resource.Success(searchResults)
        }.getOrElse {
            Resource.Error(ErrorStatus(it.message))
        }
    }

}
