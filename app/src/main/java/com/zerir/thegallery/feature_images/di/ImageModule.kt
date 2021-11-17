package com.zerir.thegallery.feature_images.di

import com.zerir.thegallery.feature_images.data.repository.ImageRepositoryImpl
import com.zerir.thegallery.feature_images.domain.repository.ImageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ImageModule {

    @Binds
    abstract fun bindImageRepository(imageRepositoryImpl: ImageRepositoryImpl): ImageRepository

}