package com.pkomovie.utils

sealed interface Resource<out T> {
    class Success<T>(val data: T) : Resource<T>
    class Error<T>(val errorStatus: ErrorStatus) : Resource<T>
    class Loading<T> : Resource<T>
}

data class ErrorStatus(
    val message: String? = null,
    val requestStatus: Int? = null
)