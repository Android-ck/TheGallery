package com.zerir.thegallery.feature_images.domain.use_case

import com.zerir.thegallery.base.network.NetworkBoundResource
import com.zerir.thegallery.feature_images.domain.repository.ImageRepository
import javax.inject.Inject

class RetrieveImagesUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) : NetworkBoundResource {

    operator fun invoke(query: String) = invokeNetworkBoundResource(
        query = {
            imageRepository.retrieveCachedImages()
        },
        fetch = {
            imageRepository.retrieveImages(query)
        },
        caching = { retrieveResponse ->
            imageRepository.saveLastSearchTag(query)
            val images = retrieveResponse.hits
            imageRepository.cachingImages(images)
        }
    )

}