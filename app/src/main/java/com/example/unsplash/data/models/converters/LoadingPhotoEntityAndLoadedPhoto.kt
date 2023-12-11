package com.example.unsplash.data.models.converters

import com.example.unsplash.data.room_db.entity_loding_photos.LoadingPhotoEntity
import com.example.unsplash.presentation._common.models.LoadedPhoto

fun LoadedPhoto.toLoadingPhotoEntity(): LoadingPhotoEntity {
    return LoadingPhotoEntity(
	  loadId = loadId,
	  photoId = photoId,
	  urlToPreview = urlToPreview
    )
}