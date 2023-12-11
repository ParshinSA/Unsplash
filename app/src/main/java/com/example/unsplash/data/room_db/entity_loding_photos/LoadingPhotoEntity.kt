package com.example.unsplash.data.room_db.entity_loding_photos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.unsplash.data.room_db.entity_loding_photos.LoadingPhotoEntity.ColumnsData.LOAD_ID
import com.example.unsplash.data.room_db.entity_loding_photos.LoadingPhotoEntity.ColumnsData.PHOTO_ID
import com.example.unsplash.data.room_db.entity_loding_photos.LoadingPhotoEntity.ColumnsData.URL_TO_PREVIEW

@Entity(tableName = LoadingPhotoEntity.TableData.NAME)
data class LoadingPhotoEntity(

    @PrimaryKey
    @ColumnInfo(LOAD_ID)
    val loadId: Long,

    @ColumnInfo(PHOTO_ID)
    val photoId: String,

    @ColumnInfo(URL_TO_PREVIEW)
    val urlToPreview: String,

    ) {

    object TableData {
	  const val NAME = "LoadingPhotoEntity"
    }

    object ColumnsData {

	  const val LOAD_ID = "load_ID"

	  const val PHOTO_ID = "photo_ID"

	  const val URL_TO_PREVIEW = "url_to_preview"
    }
}