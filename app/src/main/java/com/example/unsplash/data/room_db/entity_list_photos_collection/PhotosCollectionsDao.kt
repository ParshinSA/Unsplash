package com.example.unsplash.data.room_db.entity_list_photos_collection

import androidx.room.*

@Dao
interface PhotosCollectionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCollections(collections: List<PhotosCollectionsEntity>)

    @Query("SELECT * FROM ${PhotosCollectionsEntity.Table.NAME}")
    suspend fun requestAllPhotosCollections(): List<PhotosCollectionsEntity>

    @Query("SELECT COUNT(*) FROM ${PhotosCollectionsEntity.Table.NAME}")
    suspend fun receiveCountOfItemsInTable(): Int

    @Delete
    suspend fun deleteListPhotos(listPhoto: List<PhotosCollectionsEntity>)

}