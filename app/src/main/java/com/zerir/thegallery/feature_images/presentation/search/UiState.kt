package com.zerir.thegallery.feature_images.presentation.search

import com.zerir.thegallery.feature_images.domain.model.Image

data class UiState(
    val query: String,
    val images: List<Image>?,
    val loading: Boolean,
    var throwable: Throwable?,
)
