package com.zerir.thegallery.feature_images.domain.use_case

import com.zerir.thegallery.base.network.Resource
import com.zerir.thegallery.feature_images.domain.repository.ImageRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetImageByIdUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {

    operator fun invoke(query: String, id: String) = flow {
        //loading
        emit(Resource.Loading())
        //server
        val searchResponse = imageRepository.getImageById(query = query, id = id)
        emit(searchResponse)
    }

}