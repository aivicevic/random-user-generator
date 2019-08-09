package com.domain

enum class Status {
    LOADING, SUCCESS, ERROR
}

class Response<T>(val status: Status, val data: T?, val throwable: Throwable?) {

    companion object {

        fun <T> loading(): Response<T> = Response(Status.LOADING, null, null)

        fun <T> success(data: T): Response<T> = Response(Status.SUCCESS, data, null)

        fun <T> error(t: Throwable): Response<T> = Response(Status.ERROR, null, t)
    }
}
