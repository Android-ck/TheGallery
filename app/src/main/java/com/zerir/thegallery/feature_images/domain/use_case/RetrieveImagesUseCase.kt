package com.zerir.thegallery.feature_images.domain.use_case

import com.zerir.thegallery.base.network.Resource
import com.zerir.thegallery.feature_images.domain.repository.ImageRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RetrieveImagesUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {

    operator fun invoke(query: String) = flow {
        //loading
        emit(Resource.Loading())
        //server
        val searchResponse = imageRepository.retrieveImages(query = query)
        emit(searchResponse)
    }

}