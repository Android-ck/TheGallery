package com.zerir.thegallery.base.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

interface AsyncCall {

    suspend fun <T> invokeAsyncCall(
        ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
        apiCall: suspend () -> T,
    ): Resource<T> {
        return withContext(ioDispatcher) {
            try {
                Resource.Success(data = apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is NoConnectivityException -> {
                        Resource.Failure(isNetworkError = true)
                    }
                    is HttpException -> {
                        Resource.Failure(
                            errorCode = throwable.code(),
                            errorBody = throwable.response()?.errorBody(),
                            isNetworkError = false)

                    }
                    else -> {
                        Resource.Failure(isNetworkError = false)
                    }
                }
            }
        }
    }

}