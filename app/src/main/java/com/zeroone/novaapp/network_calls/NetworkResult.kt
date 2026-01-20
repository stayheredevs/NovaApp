package com.zeroone.novaapp.network_calls

sealed class NetworkResult<T>(
    val resultData: T? = null,
    val message: String? = null) {

    class Success<T>(data: T) : NetworkResult<T>(data)

    class Error<T>(message: String, data: T? = null) : NetworkResult<T>(data, message)

    class Loading<T> : NetworkResult<T>()

}