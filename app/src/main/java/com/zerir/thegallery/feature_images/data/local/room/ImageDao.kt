package com.zerir.thegallery.feature_images.data.local.room

import androidx.room.*
import com.zerir.thegallery.feature_images.domain.model.Image
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {

    @Query("SELECT * FROM image")
    fun getImages(): Flow<List<Image>>

    @Query("SELECT * FROM image WHERE id = :id")
    suspend fun getImageById(id: String): Image?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImages(vararg image: Image)

    @Query("DELETE FROM image")
    suspend fun deleteImages()

}