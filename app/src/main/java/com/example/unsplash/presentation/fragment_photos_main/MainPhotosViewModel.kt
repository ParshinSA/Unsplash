package com.example.unsplash.presentation.fragment_photos_main

import androidx.lifecycle.viewModelScope
import com.example.unsplash._common.extensions.launchIO
import com.example.unsplash.presentation._external_dependencies.PhotosRepository
import com.example.unsplash.presentation.abstract_fragment_photos.PhotosAbstractViewModel

open class MainPhotosViewModel(
    private val photosRepository: PhotosRepository,
) : PhotosAbstractViewModel(photosRepository) {

    fun requestSearchPhotos(query: String) {
	  viewModelScope.launchIO(
		action = {
		    val pager = photosRepository.requestSearchPhotosPager(query)
		    collectForItemsPager(pager)
		},
		onError = { it.printStackTrace() }
	  )
    }

    fun requestPhotosRemote() {
	  viewModelScope.launchIO(
		action = {
		    val pager = photosRepository.requestSimplePhotosPager()
		    collectForItemsPager(pager)
		},
		onError = { it.printStackTrace() }
	  )
    }

}