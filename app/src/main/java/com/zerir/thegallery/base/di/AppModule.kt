package com.zerir.thegallery.base.di

import com.zerir.thegallery.base.network.RemoteDataSource
import com.zerir.thegallery.feature_images.data.remote.ImageApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideImageApi(remoteDataSource: RemoteDataSource): ImageApi {
        return remoteDataSource.buildApi(ImageApi::class.java)
    }
}