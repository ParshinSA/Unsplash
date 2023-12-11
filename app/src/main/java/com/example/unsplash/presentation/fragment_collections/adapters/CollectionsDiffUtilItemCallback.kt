package com.example.unsplash.presentation.fragment_collections.adapters

import com.example.unsplash.presentation.abstract_fragment_items_display.DisplayItemsDiffUtilItemCallback
import com.example.unsplash.presentation.fragment_collections.models.PhotosCollection

class CollectionsDiffUtilItemCallback : DisplayItemsDiffUtilItemCallback<PhotosCollection>() {

    override fun areContentsTheSame(oldItem: PhotosCollection, newItem: PhotosCollection) =
	  oldItem == newItem

}