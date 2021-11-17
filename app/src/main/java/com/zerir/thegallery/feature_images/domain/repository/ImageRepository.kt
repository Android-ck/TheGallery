package com.zerir.thegallery.feature_images.domain.repository

import com.zerir.thegallery.feature_images.data.remote.response.RetrieveResponse
import com.zerir.thegallery.feature_images.domain.model.Image
import kotlinx.coroutines.flow.Flow

interface ImageRepository {

    fun retrieveCachedImages(): Flow<List<Image>>

    suspend fun retrieveImages(query: String): RetrieveResponse

    suspend fun getImageById(id: String): Image?

    fun saveLastSearchTag(tag: String)

    fun loadLastSearchTag(): String

    suspend fun cachingImages(images: List<Image>)
}