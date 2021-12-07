package com.zerir.thegallery.feature_images.di

import com.zerir.thegallery.feature_images.data.local.preference.TagPreferenceImpl
import com.zerir.thegallery.feature_images.data.repository.ImageRepositoryImpl
import com.zerir.thegallery.feature_images.data.local.preference.TagPreference
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

    @Binds
    abstract fun bindTagPreference(tagPreferenceImpl: TagPreferenceImpl): TagPreference

}