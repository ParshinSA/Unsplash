package com.example.unsplash.data.room_db.entity_loding_photos

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LoadingPhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addLoading(loadingPhoto: LoadingPhotoEntity)

    @Delete
    fun removeLoading(loadingPhoto: LoadingPhotoEntity)

    @Query("SELECT * FROM ${LoadingPhotoEntity.TableData.NAME}")
    fun retrieveAllLoadings(): List<LoadingPhotoEntity>

    @Query("SELECT  * FROM ${LoadingPhotoEntity.TableData.NAME}")
    fun observeToChangeData(): Flow<List<LoadingPhotoEntity>>

}