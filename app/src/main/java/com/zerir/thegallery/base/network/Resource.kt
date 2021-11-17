package com.zerir.thegallery.base.network

import okhttp3.ResponseBody

sealed class Resource<out T>(
    val data: T? = null,
    val errorCode: Int? = null,
    val errorBody: ResponseBody? = null,
    val isNetworkError: Boolean? = null,
) {
    class Success<out T>(data: T) : Resource<T>(data = data)
    class Failure<T>(
        errorCode: Int? = null,
        errorBody: ResponseBody? = null,
        isNetworkError: Boolean,
    ) : Resource<T>(errorCode = errorCode, errorBody = errorBody, isNetworkError = isNetworkError)
    class Loading<T> : Resource<T>()
}
