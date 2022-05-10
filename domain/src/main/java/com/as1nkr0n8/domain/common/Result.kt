package com.as1nkr0n8.domain.common

sealed class Result<Success, Error> {
    data class Success<Success>(val data: Success): Result<Success, Nothing>()
    data class Error<Error>(val data: Error): Result<Nothing, Error>()
}
