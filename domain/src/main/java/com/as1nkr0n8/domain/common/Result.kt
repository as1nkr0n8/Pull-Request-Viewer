package com.as1nkr0n8.domain.common

sealed class Result<out S,out E> {
    data class Success<out S>(val data: S): Result<S, Nothing>()
    data class Error<out E>(val errorData: E): Result<Nothing, E>()
}
