package com.example.unsplash.data.room_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.unsplash.data.room_db.entity_list_photos.PhotoEntity
import com.example.unsplash.data.room_db.entity_list_photos.PhotosDao
import com.example.unsplash.data.room_db.entity_list_photos_collection.PhotosCollectionsEntity
import com.example.unsplash.data.room_db.entity_list_photos_collection.PhotosCollectionsDao
import com.example.unsplash.data.room_db.entity_loding_photos.LoadingPhotoDao
import com.example.unsplash.data.room_db.entity_loding_photos.LoadingPhotoEntity

@Database(
    entities = [
	  PhotoEntity::class,
	  LoadingPhotoEntity::class,
	  PhotosCollectionsEntity::class,
    ],
    version = AppDatabaseRoom.VERSION_DB
)
abstract class AppDatabaseRoom : RoomDatabase() {

    abstract fun getPhotosDao(): PhotosDao

    abstract fun getPhotosCollectionDao(): PhotosCollectionsDao

    abstract fun getLoadingPhotoDao(): LoadingPhotoDao

    fun clearAllDatabaseFiles() {
	  clearAllTables()
    }

    companion object {
	  const val VERSION_DB = 1
	  const val NAME_DB = "AppDatabaseRoom"
    }
}