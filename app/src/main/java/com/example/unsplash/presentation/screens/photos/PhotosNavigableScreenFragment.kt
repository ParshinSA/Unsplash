package com.example.unsplash.presentation.screens.photos

import android.view.View
import com.example.unsplash.R
import com.example.unsplash.presentation._common.interfaces.PhotoDetailsHandler
import com.example.unsplash.presentation.abstract_fragment_navigable_screen.NavigableScreenAbstractFragment

open class PhotosNavigableScreenFragment :
    NavigableScreenAbstractFragment(),
    PhotoDetailsHandler {

    override val routerTag = ROUTER_TAG

    override fun thisCustomActions() {
	  navigateTo(screens.PhotosFragment(), true)
    }

    override fun thisCustomSettings() {
	  // yet not settings
    }

    override fun openPhotoDetailsFrg(photoId: String, sharedView: View?) {
	  connectionStateProvider.isDeviceOnline() ?: return

	  navigateTo(
		targetFragmentScreen = screens.PhotoDetailsFragment(photoId = photoId),
		clearContainer = false,
		settingsTransactions = {
		    sharedView?.let {
			  val targetTransactionName = getString(R.string.transition_name_details)
			  addSharedElement(sharedView, targetTransactionName)
		    }
		}
	  )
    }

    companion object {
	  private const val ROUTER_TAG = "PhotosScreenFragment"

	  fun newInstance() = PhotosNavigableScreenFragment()
    }
}