package com.zerir.thegallery.feature_images.domain.repository

import com.zerir.thegallery.base.network.Resource
import com.zerir.thegallery.feature_images.data.remote.response.RetrieveResponse

interface ImageRepository {

    suspend fun retrieveImages(query: String): Resource<RetrieveResponse>

    suspend fun getImageById(query: String, id: String): Resource<RetrieveResponse>

}