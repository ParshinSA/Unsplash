package com.example.unsplash.data.models.converters

import com.example.unsplash.data.models._common.NULL_STUB
import com.example.unsplash.data.models.dto.PhotosCollectionDto
import com.example.unsplash.presentation.fragment_collections.models.PhotosCollection

fun PhotosCollectionDto.toUiModel(): PhotosCollection? {
    val collectionID = id ?: return null
    val previewPhotos = previewPhotos?.mapNotNull { it.urlsDto?.small } ?: return null
    val title = title ?: NULL_STUB
    val totalPhotos = totalPhotos?.toString() ?: NULL_STUB
    val namePhotoCollector = photoCollector?.name ?: return null

    val commonInfo = "Photo collector: $namePhotoCollector\n" +
	  "Total photos: $totalPhotos"

    return PhotosCollection(
	  collectionId = collectionID,
	  previewPhotos = previewPhotos,
	  title = title, commonInfo = commonInfo
    )
}