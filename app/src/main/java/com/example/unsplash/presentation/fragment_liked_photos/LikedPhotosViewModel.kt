package com.example.unsplash.presentation.fragment_liked_photos

import androidx.lifecycle.viewModelScope
import com.example.unsplash._common.extensions.launchIO
import com.example.unsplash.presentation._external_dependencies.PhotosRepository
import com.example.unsplash.presentation.abstract_fragment_photos.PhotosAbstractViewModel

class LikedPhotosViewModel(
    private val repository: PhotosRepository,
) : PhotosAbstractViewModel(repository) {

    var usersUsername: String? = null

    fun requestLikedPhotos() {
	  viewModelScope.launchIO(
		action = {
		    val pager = repository.requestLikedPhotosPager(usersUsername)
		    collectForItemsPager(pager)
		},
		onError = { it.printStackTrace() }
	  )
    }

    fun saveUsersUsername(userName: String) {
	  usersUsername = userName
    }

}