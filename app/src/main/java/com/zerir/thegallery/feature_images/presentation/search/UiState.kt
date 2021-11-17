package com.zerir.thegallery.feature_images.presentation.search

import com.zerir.thegallery.base.network.Resource
import com.zerir.thegallery.feature_images.data.remote.response.RetrieveResponse

data class UiState(
    val query: String,
    val resource: Resource<RetrieveResponse>,
)
