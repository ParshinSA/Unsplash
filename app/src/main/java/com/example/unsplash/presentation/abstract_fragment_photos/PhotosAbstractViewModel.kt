package com.example.unsplash.presentation.abstract_fragment_photos

import androidx.lifecycle.viewModelScope
import androidx.paging.map
import com.example.unsplash._common.extensions.copyWithChangesLikeInfo
import com.example.unsplash._common.extensions.launchIO
import com.example.unsplash._common.extensions.launchMain
import com.example.unsplash.presentation._common.models.LikeInfo
import com.example.unsplash.presentation._external_dependencies.PhotosRepository
import com.example.unsplash.presentation.abstract_fragment_items_display.ItemsDisplayAbstractViewModel
import com.example.unsplash.presentation.fragment_photos_main.models.Photo

abstract class PhotosAbstractViewModel(
    private val photosRepository: PhotosRepository,
) : ItemsDisplayAbstractViewModel<Photo>() {

    init {
	  viewModelScope.launchIO(
		action = { photosRepository.listenToNewLikeInfo().collect(::updatePhotoInPagingData) },
		onError = { it.printStackTrace() }
	  )
    }

    fun changeLike(oldLikeInfo: LikeInfo) {
	  viewModelScope.launchIO(
		action = { photosRepository.changeLike(oldLikeInfo) },
		onError = { it.printStackTrace() })
    }

    private fun updatePhotoInPagingData(newLikeInfo: LikeInfo?) {
	  newLikeInfo ?: return
	  viewModelScope.launchMain(
		action = {
		    val photoId = newLikeInfo.photoId
		    val newPagingData = itemsMutStateFlow.value.map { photo: Photo ->
			  if (photo.photoID == photoId)
				photo.copyWithChangesLikeInfo(newLikeInfo)
			  else
				photo
		    }
		    itemsMutStateFlow.value = newPagingData
		},
		onError = { it.printStackTrace() }
	  )
    }

}