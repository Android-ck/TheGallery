package com.zerir.thegallery.feature_images.domain.use_case

import com.zerir.thegallery.feature_images.domain.repository.ImageRepository
import javax.inject.Inject

class GetLastSearchTagUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {

    operator fun invoke() = imageRepository.loadLastSearchTag()

}