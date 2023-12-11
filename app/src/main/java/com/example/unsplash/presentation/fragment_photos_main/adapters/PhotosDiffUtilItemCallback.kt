package com.example.unsplash.presentation.fragment_photos_main.adapters

import com.example.unsplash.presentation.abstract_fragment_items_display.DisplayItemsDiffUtilItemCallback
import com.example.unsplash.presentation.fragment_photos_main.models.Photo

class PhotosDiffUtilItemCallback : DisplayItemsDiffUtilItemCallback<Photo>() {
    override fun areContentsTheSame(oldItem: Photo, newItem: Photo) = newItem == oldItem
}