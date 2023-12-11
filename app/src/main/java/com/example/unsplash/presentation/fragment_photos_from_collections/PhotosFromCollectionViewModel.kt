package com.example.unsplash.presentation.fragment_photos_from_collections

import androidx.lifecycle.viewModelScope
import com.example.unsplash._common.extensions.launchIO
import com.example.unsplash.presentation._external_dependencies.PhotosRepository
import com.example.unsplash.presentation.abstract_fragment_photos.PhotosAbstractViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PhotosFromCollectionViewModel(
    private val repository: PhotosRepository,
) : PhotosAbstractViewModel(repository) {

    private var collectionId: String? = null

    private val collectionCommonInfoMutStateFlow = MutableStateFlow<String?>("")
    private val collectionTitleMutStateFlow = MutableStateFlow<String?>("")

    val collectionCommonInfoSateFlow get() = collectionCommonInfoMutStateFlow.asStateFlow()
    val collectionTitleStateFlow get() = collectionTitleMutStateFlow.asStateFlow()

    fun saveCollectionCommonInfo(data: String?) {
	  collectionCommonInfoMutStateFlow.value = data
    }

    fun saveCollectionTitle(title: String?) {
	  collectionTitleMutStateFlow.value = title
    }

    fun saveCollectionId(id: String?) {
	  collectionId = id
    }

    fun requestPhotosFromCollection() {
	  viewModelScope.launchIO(
		action = {
		    val pager = repository.requestPhotosFromCollection(collectionId)
		    collectForItemsPager(pager)
		},
		onError = { it.printStackTrace() }
	  )
    }

}
