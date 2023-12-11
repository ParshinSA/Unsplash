package com.example.unsplash.data.room_db.entity_list_photos_collection

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.unsplash.data.room_db.entity_list_photos_collection.PhotosCollectionsEntity.Columns.COLLECTION_ID
import com.example.unsplash.data.room_db.entity_list_photos_collection.PhotosCollectionsEntity.Columns.COMMON_INFO
import com.example.unsplash.data.room_db.entity_list_photos_collection.PhotosCollectionsEntity.Columns.LIST_PREVIEW_PHOTOS
import com.example.unsplash.data.room_db.entity_list_photos_collection.PhotosCollectionsEntity.Columns.TITLE
import com.example.unsplash.data.room_db.entity_list_photos_collection.PhotosCollectionsEntity.Table.NAME

@Entity(tableName = NAME)
@TypeConverters(PhotosCollectionTypeConverter::class)
data class PhotosCollectionsEntity(

    @PrimaryKey
    @ColumnInfo(COLLECTION_ID)
    val collectionId: String,

    @ColumnInfo(TITLE)
    val title: String,

    @ColumnInfo(COMMON_INFO)
    val commonInfo: String,

    @ColumnInfo(LIST_PREVIEW_PHOTOS)
    val previewPhotos: List<String>,

    ) {

    object Table {
	  const val NAME = "PhotosCollectionsEntity"
    }

    object Columns {
	  const val COLLECTION_ID = "COLLECTION_ID"
	  const val TITLE = "TITLE"
	  const val COMMON_INFO = "COMMON_INFO"
	  const val LIST_PREVIEW_PHOTOS = "LIST_PREVIEW_PHOTOS"
    }

}