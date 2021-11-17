package com.zerir.thegallery.base.network

import kotlinx.coroutines.flow.*

interface NetworkBoundResource : AsyncCall {

    fun <ResultType, RequestType> invokeNetworkBoundResource(
        query: () -> Flow<ResultType>,
        fetch: suspend () -> RequestType,
        caching: suspend (RequestType) -> Unit,
        shouldFetch: (ResultType) -> Boolean = { true },
    ) = flow {
        //get query once from flow
        val data = query().first()

        //check for fetching
        val flow = if (shouldFetch(data)) {
            //loading
            emit(Resource.Loading(data))
            //retrieve data from server
            val fetchResult = invokeAsyncCall { fetch() }
            //if retrieving succeed
            if (fetchResult is Resource.Success) {
                //cache data
                fetchResult.data?.let { caching(it) }
                //load query with new data
                query().map { Resource.Success(it) }
            } else {
                //load offline query with error
                query().map {
                    Resource.Failure(
                        it,
                        fetchResult.throwable,
                    )
                }
            }
        } else {
            //load offline query
            query().map { Resource.Success(it) }
        }

        emitAll(flow)
    }
}