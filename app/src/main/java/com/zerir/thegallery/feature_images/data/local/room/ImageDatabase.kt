package com.zerir.thegallery.feature_images.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zerir.thegallery.feature_images.domain.model.Image

@Database(
    version = 1,
    entities = [Image::class]
)
abstract class ImageDatabase : RoomDatabase() {

    abstract val imageDao: ImageDao

    companion object {
        const val DATABASE_NAME = "image_db"
    }

}