package com.zerir.thegallery.feature_images.data.remote

import com.zerir.thegallery.BuildConfig
import com.zerir.thegallery.feature_images.data.remote.response.RetrieveResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApi {

    @GET("?")
    suspend fun retrieveImages(
        @Query("key") apiKey: String = BuildConfig.AppApiKey,
        @Query("q") query: String,
        @Query("id") id: String = "",
    ): RetrieveResponse

}