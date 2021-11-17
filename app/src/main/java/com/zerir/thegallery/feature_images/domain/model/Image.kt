package com.zerir.thegallery.feature_images.domain.model

data class Image(
    val id: String = "",
    val user: String = "",
    val tags: String = "",
    val previewURL: String = "",
    val largeImageURL: String = "",
    val likes: Int = 0,
    val downloads: Int = 0,
    val comments: Int = 0,
)