package com.as1nkr0n8.domain.common

sealed class Result<out S> {
    data class Success<out S>(val data: S): Result<S>()
    data class Error(val errorData: InternalError): Result<Nothing>()
}
