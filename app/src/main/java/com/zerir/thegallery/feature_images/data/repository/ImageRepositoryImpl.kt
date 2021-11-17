package com.zerir.thegallery.feature_images.data.repository

import androidx.room.withTransaction
import com.zerir.thegallery.feature_images.data.local.room.ImageDatabase
import com.zerir.thegallery.feature_images.data.remote.ImageApi
import com.zerir.thegallery.feature_images.data.remote.response.RetrieveResponse
import com.zerir.thegallery.feature_images.domain.model.Image
import com.zerir.thegallery.feature_images.domain.preference.TagPreference
import com.zerir.thegallery.feature_images.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val imageApi: ImageApi,
    private val tagPreference: TagPreference,
    private val imageDatabase: ImageDatabase,
) : ImageRepository {

    private val imageDao = imageDatabase.imageDao

    override fun retrieveCachedImages(): Flow<List<Image>> {
        return imageDao.getImages()
    }

    override suspend fun retrieveImages(query: String): RetrieveResponse {
        return imageApi.retrieveImages(query = query)
    }

    override suspend fun getImageById(id: String): Image? {
        return imageDao.getImageById(id)
    }

    override fun saveLastSearchTag(tag: String) {
        tagPreference.saveLastSearchTag(tag)
    }

    override fun loadLastSearchTag(): String {
        return tagPreference.loadLastSearchTag()
    }

    override suspend fun cachingImages(images: List<Image>) {
        imageDatabase.withTransaction {
            imageDao.deleteImages()
            imageDao.insertImages(*images.toTypedArray())
        }
    }

}