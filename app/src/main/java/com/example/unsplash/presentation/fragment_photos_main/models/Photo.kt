package com.example.unsplash.presentation.fragment_photos_main.models

import com.example.unsplash.presentation.abstract_fragment_items_display.ItemDisplay

data class Photo(
    val photoID: String,
    val likes: String,
    val photoResource: String,
    val photographerName: String,
    val photoResourcePhotographerProfile: String,
    val likedByUser: Boolean,
    override val displayItemId: String = photoID
) : ItemDisplay