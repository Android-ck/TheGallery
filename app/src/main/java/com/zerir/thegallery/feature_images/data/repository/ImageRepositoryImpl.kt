package com.zerir.thegallery.feature_images.data.repository

import com.zerir.thegallery.base.network.AsyncCall
import com.zerir.thegallery.base.network.Resource
import com.zerir.thegallery.feature_images.data.remote.ImageApi
import com.zerir.thegallery.feature_images.data.remote.response.RetrieveResponse
import com.zerir.thegallery.feature_images.domain.repository.ImageRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val imageApi: ImageApi,
) : ImageRepository, AsyncCall {

    override suspend fun retrieveImages(query: String): Resource<RetrieveResponse> {
        return invokeAsyncCall {
            imageApi.retrieveImages(query = query)
        }
    }

    override suspend fun getImageById(query: String, id: String): Resource<RetrieveResponse> {
        return invokeAsyncCall {
            imageApi.retrieveImages(query = query, id = id)
        }
    }

}