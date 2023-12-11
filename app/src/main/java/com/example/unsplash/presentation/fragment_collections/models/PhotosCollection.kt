package com.example.unsplash.presentation.fragment_collections.models

import com.example.unsplash.presentation.abstract_fragment_items_display.ItemDisplay

data class PhotosCollection(
    val collectionId: String,
    val title: String,
    val commonInfo: String,
    val previewPhotos: List<String>,
    override val displayItemId: String = collectionId
) : ItemDisplay
