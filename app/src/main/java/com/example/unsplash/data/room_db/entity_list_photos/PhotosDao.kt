package com.example.unsplash.data.room_db.entity_list_photos

import androidx.room.*

@Dao
interface PhotosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveListPhotos(listPhoto: List<PhotoEntity>)

    @Query("SELECT * FROM ${PhotoEntity.Table.NAME}")
    suspend fun requestAllPhotos(): List<PhotoEntity>

    @Query("SELECT COUNT(*) FROM ${PhotoEntity.Table.NAME}")
    suspend fun receiveCountOfItemsInTable(): Int

    @Delete
    suspend fun deleteListPhotos(listPhoto: List<PhotoEntity>)

}