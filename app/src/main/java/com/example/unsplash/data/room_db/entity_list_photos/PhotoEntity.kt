package com.example.unsplash.data.room_db.entity_list_photos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.unsplash.data.room_db.entity_list_photos.PhotoEntity.Columns.COUNTER_LIKES
import com.example.unsplash.data.room_db.entity_list_photos.PhotoEntity.Columns.IMAGE_ID
import com.example.unsplash.data.room_db.entity_list_photos.PhotoEntity.Columns.LIKE_BY_USER
import com.example.unsplash.data.room_db.entity_list_photos.PhotoEntity.Columns.PHOTOGRAPHER_NAME
import com.example.unsplash.data.room_db.entity_list_photos.PhotoEntity.Columns.PHOTOGRAPHER_PHOTO_PROFILE
import com.example.unsplash.data.room_db.entity_list_photos.PhotoEntity.Columns.PHOTO_URL
import com.example.unsplash.data.room_db.entity_list_photos.PhotoEntity.Table.NAME

@Entity(tableName = NAME)
data class PhotoEntity(

    @PrimaryKey()
    @ColumnInfo(IMAGE_ID)
    val photoID: String,

    @ColumnInfo(COUNTER_LIKES)
    val counterLikes: String,

    @ColumnInfo(PHOTO_URL)
    val urlImagePhoto: String,

    @ColumnInfo(PHOTOGRAPHER_NAME)
    val photographerName: String,

    @ColumnInfo(PHOTOGRAPHER_PHOTO_PROFILE)
    val urlPhotoPhotographerProfile: String,

    @ColumnInfo(LIKE_BY_USER)
    val likedByUser: Boolean,

    ) {

    object Table {
	  const val NAME = "PhotoEntity"
	  const val MAX_ITEMS = 20
    }

    object Columns {
	  const val IMAGE_ID = "image_ID"
	  const val COUNTER_LIKES = "counter_likes"
	  const val PHOTO_URL = "photo_url"
	  const val PHOTOGRAPHER_NAME = "photographer_name"
	  const val PHOTOGRAPHER_PHOTO_PROFILE = "photographer_photo_profile"
	  const val LIKE_BY_USER = "like_by_user"
    }

}