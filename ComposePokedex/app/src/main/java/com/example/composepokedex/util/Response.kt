package com.example.composepokedex.util

sealed class Response<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Response<T>(data)
    class Failure<T>(message: String) : Response<T>(message = message)
    class Loading<T>(data: T? = null) : Response<T>(data)
}
