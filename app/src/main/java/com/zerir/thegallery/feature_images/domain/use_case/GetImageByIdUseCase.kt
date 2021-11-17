package com.zerir.thegallery.feature_images.domain.use_case

import com.zerir.thegallery.feature_images.domain.repository.ImageRepository
import javax.inject.Inject

class GetImageByIdUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {

    suspend operator fun invoke(id: String) = imageRepository.getImageById(id)
}