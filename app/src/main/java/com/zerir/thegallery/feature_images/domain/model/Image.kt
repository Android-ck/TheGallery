package com.zerir.thegallery.feature_images.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Image(
    @PrimaryKey val id: String = "",
    val user: String = "",
    val tags: String = "",
    val previewURL: String = "",
    val largeImageURL: String = "",
    val likes: Int = 0,
    val downloads: Int = 0,
    val comments: Int = 0,
)