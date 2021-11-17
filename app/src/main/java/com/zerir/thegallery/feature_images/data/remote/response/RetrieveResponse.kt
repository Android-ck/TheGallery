package com.zerir.thegallery.feature_images.data.remote.response

import com.zerir.thegallery.feature_images.domain.model.Image

data class RetrieveResponse(
    val total: Int = 0,
    val totalHits: Int = 0,
    val hits: List<Image> = listOf(),
)
