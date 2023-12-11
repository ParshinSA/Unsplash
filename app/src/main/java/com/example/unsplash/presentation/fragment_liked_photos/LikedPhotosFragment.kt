package com.example.unsplash.presentation.fragment_liked_photos

import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.unsplash.R
import com.example.unsplash._common.extensions.withArguments
import com.example.unsplash.presentation.abstract_fragment_photos.PhotosAbstractFragment
import com.example.unsplash.presentation.fragment_profile.ProfileFragment

class LikedPhotosFragment :
    PhotosAbstractFragment() {

    override val viewModel by viewModels<LikedPhotosViewModel> { viewModelsFactory }

    override val titleToolbar by lazy { getString(R.string.your_favorites) }

    override val refreshListener by lazy {
	  SwipeRefreshLayout.OnRefreshListener {
		requestLikedPhotos()
		updateInfoCounterLikes()
	  }
    }

    override fun thisCustomActions() {
	  retrieveArguments()
	  requestLikedPhotos()
    }

    private fun requestLikedPhotos() {
	  viewModel.requestLikedPhotos()
    }

    private fun updateInfoCounterLikes() {
	  val parent = requireParentFragment() as? ProfileFragment ?: return
	  parent.updateInfo()
    }

    private fun retrieveArguments() {
	  val userName = requireArguments().getString(KEY_USERS_USER_NAME) ?: return
	  viewModel.saveUsersUsername(userName)
    }

    companion object {
	  const val KEY_USERS_USER_NAME = "KEY_USERS_USER_NAME"

	  fun newInstance(userName: String) = LikedPhotosFragment().withArguments {
		putString(KEY_USERS_USER_NAME, userName)
	  }
    }

}