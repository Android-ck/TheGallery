package com.zerir.thegallery.base.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface AsyncCall {

    suspend fun <T> invokeAsyncCall(
        ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
        apiCall: suspend () -> T,
    ): Resource<T> {
        return withContext(ioDispatcher) {
            try {
                Resource.Success(data = apiCall.invoke())
            } catch (throwable: Throwable) {
                Resource.Failure(
                    throwable = throwable,
                )
            }
        }
    }

}