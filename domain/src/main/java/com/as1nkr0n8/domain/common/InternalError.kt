package com.as1nkr0n8.domain.common

data class InternalError(
    val code: Int = ErrorCodes.NO_ERROR,
    val message: String = "",
    val throwable: Throwable? = null,
    val extraData: Any? = null
) {
    fun hasError(): Boolean = code != 0
}