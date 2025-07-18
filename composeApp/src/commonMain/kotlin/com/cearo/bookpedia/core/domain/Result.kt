package com.cearo.bookpedia.core.domain

/**
 * Represents the outcome of an operation, which can be either a [Success] or an [Error].
 *
 * This sealed interface is used to encapsulate results from operations that might fail,
 * such as data parsing, network requests, or any computation where distinct success
 * and error paths are needed. It promotes explicit error handling over exceptions for
 * recoverable failures.
 *
 * @param D The type of the data in case of success (covariant).
 * @param E The type of the error object in case of failure (covariant). It is recommended
 *          that `E` implements or extends a common project-specific `Error` marker interface
 *          or sealed class for consistency.
 **/
sealed interface Result<out D, out E: Error> {
    data class Success<out D>(val data: D): Result<D, Nothing>
    data class Error<out E: com.cearo.bookpedia.core.domain.Error>(val error: E):
        Result<Nothing, E>
}

inline fun <T, E: Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when(this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

fun <T, E: Error> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map {  }
}

inline fun <T, E: Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Error -> this
        is Result.Success -> {
            action(data)
            this
        }
    }
}
inline fun <T, E: Error> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Error -> {
            action(error)
            this
        }
        is Result.Success -> this
    }
}

typealias EmptyResult<E> = Result<Unit, E>