package com.example.unsplash.presentation.fragment_collections

import androidx.lifecycle.viewModelScope
import com.example.unsplash._common.extensions.launchIO
import com.example.unsplash.presentation._external_dependencies.CollectionsRepository
import com.example.unsplash.presentation.abstract_fragment_items_display.ItemsDisplayAbstractViewModel
import com.example.unsplash.presentation.fragment_collections.models.PhotosCollection

class CollectionsViewModel(
    private val repository: CollectionsRepository,
) : ItemsDisplayAbstractViewModel<PhotosCollection>() {

    fun requestSomeCollections() {
	  viewModelScope.launchIO(
		action = {
		    val pager = repository.requestPagerForSimpleCollections()
		    collectForItemsPager(pager)
		},
		onError = { it.printStackTrace() })
    }

    fun requestSearchCollections(query: String) {
	  viewModelScope.launchIO(
		action = {
		    val pager = repository.requestPagerForSearchCollections(query)
		    collectForItemsPager(pager)
		},
		onError = { it.printStackTrace() })
    }

}