package com.pkomovie.domain.use_cases

import com.pkomovie.domain.repository.Repository
import javax.inject.Inject

class ToggleAddToFavouriteUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(movieId: Int, isAlreadyLiked: Boolean) = kotlin.runCatching {
        if (isAlreadyLiked) {
            repository.removeFromFavourites(movieId)
        } else {
            repository.saveToFavourites(movieId)
        }
    }.getOrNull()
}