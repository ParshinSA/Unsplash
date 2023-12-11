package com.example.unsplash.data.models.converters

import com.example.unsplash.data.room_db.entity_list_photos_collection.PhotosCollectionsEntity
import com.example.unsplash.presentation.fragment_collections.models.PhotosCollection

fun PhotosCollectionsEntity.toUiModel(): PhotosCollection {
    return PhotosCollection(
	  collectionId = collectionId,
	  title = title,
	  commonInfo = commonInfo,
	  previewPhotos = previewPhotos
    )
}