package com.as1nkr0n8.domain.common

data class Error(
    val code: Int = 0,
    val message: String = "",
    val throwable: Throwable? = null,
    val extraData: Any? = null
) {
    fun hasError(): Boolean = code != 0
}