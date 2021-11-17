package com.zerir.thegallery.base.di

import android.app.Application
import androidx.room.Room
import com.zerir.thegallery.base.network.RemoteDataSource
import com.zerir.thegallery.feature_images.data.local.room.ImageDatabase
import com.zerir.thegallery.feature_images.data.remote.ImageApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideImageApi(remoteDataSource: RemoteDataSource): ImageApi {
        return remoteDataSource.buildApi(ImageApi::class.java)
    }

    @Provides
    @Singleton
    fun provideImageDatabase(app: Application): ImageDatabase {
        return Room.databaseBuilder(
            app,
            ImageDatabase::class.java,
            ImageDatabase.DATABASE_NAME,
        ).build()
    }
}